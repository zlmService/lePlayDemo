package com.zlm.demo.leshidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by zhaoliming on 2016/8/9.
 */
public class MyProgressBarAllVideo extends ProgressBar {

    // 画笔和文字
    String text;
    Paint mPaint;

    public MyProgressBarAllVideo(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initText();
    }

    public MyProgressBarAllVideo(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initText();
    }

    public MyProgressBarAllVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initText();
    }

    // 設置進度。
    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        setText(progress);
        super.setProgress(progress);
    }

    // 重写onDraw
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        // 设置字体大小。
        this.mPaint.setTextSize(20);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    private void initText() {
        this.mPaint = new Paint();
        // 设置字体的颜色。
        this.mPaint.setColor(Color.WHITE);

    }

    // 设置文字内容
    private void setText(int progress) {
        // int i = (progress * 100) / this.getMax();

        this.text = String.valueOf(progress) + "/" + this.getMax();

        // /把进度放进来，让进度值和百分比同步。。

    }

    private void setText() {
        setText(this.getProgress());
    }
}
