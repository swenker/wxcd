package cn.yinyushijing.scanpen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private CameraView mCameraView;
    private ImageView mImageView;

    private static final int PERMISSION_REQUEST_ID_CAMERA = 100010;
    private static final int PERMISSION_REQUEST_ID_WRITE_EXTERNAL_STORAGE = 100020;
    private static final int PERMISSION_REQUEST_ID_MOUNT_UNMOUNT_FILESYSTEMS = 100030;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermissionGranted(getApplicationContext(), Manifest.permission.CAMERA)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_ID_CAMERA);
        }
        if (!checkPermissionGranted(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_ID_WRITE_EXTERNAL_STORAGE);
        }
        if (!checkPermissionGranted(getApplicationContext(), Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)) {
            requestPermissions(new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, PERMISSION_REQUEST_ID_MOUNT_UNMOUNT_FILESYSTEMS);
        }

/*
        mCameraView = (CameraView) findViewById(R.id.main_camera);
        mImageView = (ImageView) findViewById(R.id.main_image);
        mCameraView.setTag(mImageView);
*/

    }


    public static boolean checkPermissionGranted(Context context, String permissionName) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permissionName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_ID_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
