package com.example.shubhambarudwale.cubesolver;

import org.opencv.android.JavaCameraView;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by shubhambarudwale on 20/09/17.
 */

public class CustomOpenCVJavaCameraView extends JavaCameraView{
    int imgcols;
    int imgrows;
    int height;
    int width;
    public CustomOpenCVJavaCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
        init();

    }

    private Paint linePaint;
    protected void init() {
        Resources r = this.getResources();
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setAlpha(200);
        linePaint.setStrokeWidth(1);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(r.getColor(R.color.colorPrimary));
        linePaint.setShadowLayer(2, 1, 1, r.getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linePaint.setStrokeWidth(5);
        height=canvas.getHeight();
        width=canvas.getWidth();
        int rectleft=(height-imgrows)/2;
        int recttop=(width-imgrows)/2;
        canvas.drawLine(recttop,rectleft,recttop,rectleft+imgrows,linePaint);
        canvas.drawLine(recttop,rectleft,recttop+imgrows,rectleft,linePaint);
        canvas.drawLine(recttop,rectleft+imgrows,recttop+imgrows,rectleft+imgrows,linePaint);
        canvas.drawLine(recttop+imgrows,rectleft,recttop+imgrows,rectleft+imgrows,linePaint);
        canvas.drawLine(recttop+imgrows/3,rectleft,recttop+imgrows/3,rectleft+imgrows,linePaint);
        canvas.drawLine(recttop+imgrows*2/3,rectleft,recttop+imgrows*2/3,rectleft+imgrows,linePaint);
        canvas.drawLine(recttop,rectleft+imgrows/3,recttop+imgrows,rectleft+imgrows/3,linePaint);
        canvas.drawLine(recttop,rectleft+imgrows*2/3,recttop+imgrows,rectleft+imgrows*2/3,linePaint);


    }
}
