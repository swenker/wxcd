package cn.yinyushijing.scanpen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;

public class DrawImageView extends AppCompatImageView {
    private Paint paint = new Paint();
    private int screenWidth=960;
    private int screenHeight=1280;

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

//        screenWidth =wm.getDefaultDisplay().getWidth();
//        screenHeight =wm.getDefaultDisplay().getHeight();
    }


    private void initPaint(){
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5.5f);
        paint.setAlpha(100);
    }

    ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("sizesize",String.format("screensize:%d,%d",screenWidth,screenHeight));
        canvas.drawRect(new Rect(screenWidth/4, screenHeight/2-screenHeight/20, 3*screenWidth/4, screenHeight/2+screenHeight/20), paint);
    }


}
