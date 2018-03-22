package cn.yinyushijing.scanpen;

import android.graphics.Bitmap;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class OcrHandler {
    //path to tessdata
    static final String TESSBASE_PATH = Environment.getExternalStorageDirectory() + File.separator + "ocr" + File.separator + "304" + File.separator;
    static final String ENGLISH_LANGUAGE = "eng";
    static final String CHINESE_LANGUAGE = "chi_sim";
    private TessBaseAPI baseApi;

    OcrHandler() {
    }

    void init() {
        baseApi = new TessBaseAPI();
        baseApi.init(TESSBASE_PATH, ENGLISH_LANGUAGE);
    }

    public void release() {
        baseApi.clear();
        baseApi.end();
    }

    void scanDocuments(final Bitmap bitmap, final OcrCallback ocrCallback) {

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.init(TESSBASE_PATH, ENGLISH_LANGUAGE);

        try {
            baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
            baseApi.setImage(bitmap);

            String result = baseApi.getUTF8Text();
            if (ocrCallback != null) {
                ocrCallback.handleResult(result);
            }

            baseApi.clear();
            baseApi.end();

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    interface OcrCallback {
        void handleResult(String result);
    }
}
