package cn.yinyushijing.scanpen;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private final String TAG = "CameraView";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private boolean isPreviewOn;

    private int imageWidth = 1920;
    private int imageHeight = 1080;

    private int frameRate = 30;
    private OcrHandler ocrHandler;

    public CameraView(Context context) {
        super(context);
        init();
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    @RequiresPermission(Manifest.permission.CAMERA)
    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.OPAQUE);

        ocrHandler = new OcrHandler();
        ocrHandler.init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        openCamera();
        if (mCamera != null)
            initCameraParams();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            release();
        } catch (Exception e) {
        }
    }

    private boolean isScanning = false;


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
       if(data ==null) return;

        try {
            Camera.Size size = camera.getParameters().getPreviewSize();

            //frame to bitmap
            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, baos);
            Bitmap bmp = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());

            //这里返回的照片默认横向的，先将图片旋转90度
            bmp = rotateToDegrees(bmp, 90);
//            Log.d("sizesize", String.format("bmpsize:;;   %d ;%d  ", bmp.getWidth(), bmp.getHeight()));
            bmp = bitmapCrop(bmp, bmp.getWidth() / 4, bmp.getHeight() / 2 -  bmp.getHeight() /10, bmp.getWidth() / 2, bmp.getHeight() / 2 +  bmp.getHeight() /10);

            if (bmp == null)
                return;

            new OcrAsyncTask().execute(bmp);
        } catch (Exception ex) {
            Log.e(TAG, "failed", ex);
            isScanning = false;
        }
    }


    private Bitmap bitmapCrop(Bitmap bitmap, int left, int top, int width, int height) {
        if (null == bitmap || width <= 0 || height < 0) {
            return null;
        }
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        if (widthOrg >= width && heightOrg >= height) {
            try {
                bitmap = Bitmap.createBitmap(bitmap, left, top, width, height);
            } catch (Exception e) {
                Log.d(TAG,"createBitmap",e);
                return null;
            }
        }
        return bitmap;
    }

    private Bitmap rotateToDegrees(Bitmap tmpBitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degrees);
        return Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight(), matrix,
                true);
    }

    public void initCameraParams() {
        stopPreview();
        Camera.Parameters camParams = mCamera.getParameters();
        List<Camera.Size> supportedPreviewSizes = camParams.getSupportedPreviewSizes();

        imageWidth = supportedPreviewSizes.get(0).width;
        imageHeight = supportedPreviewSizes.get(0).height;
        Log.d("SizeSize",String.format("prew:%d,%d",imageWidth,imageHeight));
/*
        for (int i = 0; i < supportedPreviewSizes.size(); i++) {
            Log.d(TAG,String.format("%d,%d,%d",i,supportedPreviewSizes.get(i).width,supportedPreviewSizes.get(i).height));

            if ((supportedPreviewSizes.get(i).width >= imageWidth && supportedPreviewSizes.get(i).height >= imageHeight) || i == supportedPreviewSizes.size() - 4) {
                imageWidth = supportedPreviewSizes.get(i).width;
                imageHeight = supportedPreviewSizes.get(i).height;
                Log.v(TAG, "Changed to supported resolution: " + imageWidth + "x" + imageHeight);
                break;
            }
        }
*/
        camParams.setPreviewSize(imageWidth, imageHeight);
//        Log.v(TAG, "supportedPreviewSizes.size:" + supportedPreviewSizes.size() + "Setting imageWidth: " + imageWidth + " imageHeight: " + imageHeight + " frameRate: " + frameRate);
        camParams.setJpegQuality(100);
        camParams.setPreviewFrameRate(frameRate);
//        Log.v(TAG, "Preview Framerate: " + camParams.getPreviewFrameRate());

        mCamera.setParameters(camParams);
        //取到的图像默认是横向的，这里旋转90度，保持和预览画面相同
        mCamera.setDisplayOrientation(90);

        startPreview();
    }

    public void startPreview() {
        try {
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            mCamera.autoFocus(autoFocusCB);
        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
        }
    }

    public void stopPreview() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
        }
    }


    public void openCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int cameraId = 0; cameraId < Camera.getNumberOfCameras(); cameraId++) {
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    mCamera = Camera.open(cameraId);
                } catch (Exception e) {
                    if (mCamera != null) {
                        mCamera.release();
                        mCamera = null;
                    }
                }
                break;
            }
        }
    }

    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            postDelayed(doAutoFocus, 1000);
        }
    };
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (mCamera != null) {
                try {
                    mCamera.autoFocus(autoFocusCB);
                } catch (Exception e) {
                }
            }
        }
    };


    public void release() {
        if (isPreviewOn && mCamera != null) {
            isPreviewOn = false;
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        ocrHandler.release();
    }


    private class OcrAsyncTask extends AsyncTask<Bitmap, Integer, Integer> {

        @Override
        protected Integer doInBackground(Bitmap... params) {
            ocrHandler.scanDocuments(params[0], new OcrHandler.OcrCallback() {
                @Override
                public void handleResult(String result) {
                    Log.d(TAG, "result：  " + result);
                    if (!TextUtils.isEmpty(result)) {
                        isScanning = true;
                    } else
                        isScanning = false;
                }
            });
            return 0;
        }
    }
}
