package com.example.zhang.hitsystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class Tri extends View {

    private Paint mPaint;
    Context mContext;
    float A=ScoreAnalyze.Anum;
    float B=ScoreAnalyze.Bnum;
    float C=ScoreAnalyze.Cnum;
    float D=ScoreAnalyze.Dnum;
    float E=ScoreAnalyze.Enum;

    public Tri(Context context) {
        super(context);
    }

    public Tri(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Tri(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Paint p = new Paint();
        Paint l1 = new Paint();
        Paint l2 = new Paint();
        Path path1 = new Path();
        Path path2 = new Path();
        Paint p1 = new Paint();

        p.setAntiAlias(true);
        p1.setAntiAlias(true);
        l1.setAntiAlias(true);
        l2.setAntiAlias(true);



        p.setColor(Color.GRAY);
        l1.setColor(Color.BLACK);
        l1.setStrokeWidth(3);
        l2.setColor(Color.BLACK);
        l2.setStrokeWidth(4);
        p1.setColor(Color.BLACK);
        p1.setTextSize(30);

        canvas.drawLine(200, 700, 200, 300, l2);
        path1.moveTo(180, 300);
        path1.lineTo(220, 300);
        path1.lineTo(200, 260);
        path1.close();
        canvas.drawPath(path1, p1);



        canvas.drawLine(200, 701, 650, 701, l2);
        path2.moveTo(650, 680);
        path2.lineTo(650, 720);
        path2.lineTo(690, 700);
        path2.close();
        canvas.drawPath(path2, p1);

        canvas.drawRect(525, 700-4*A, 594, 700, p);
        canvas.drawLine(525, 700, 525, 700-4*A, l1);
        canvas.drawLine(525, 700-4*A, 595, 700-4*A, l1);
        canvas.drawLine(595, 700, 595, 700-4*A, l1);

        canvas.drawRect(445, 700-4*B, 514, 700, p);
        canvas.drawLine(445, 700, 445, 700-4*B, l1);
        canvas.drawLine(445, 700-4*B, 515, 700-4*B, l1);
        canvas.drawLine(515, 700, 515, 700-4*B, l1);

        canvas.drawRect(365, 700-4*C, 434, 700, p);
        canvas.drawLine(365, 700, 365, 700-4*C, l1);
        canvas.drawLine(365, 700-4*C, 435, 700-4*C, l1);
        canvas.drawLine(435, 700, 435, 700-4*C, l1);

        canvas.drawRect(285, 700-4*D, 354, 700, p);
        canvas.drawLine(285, 700, 285, 700-4*D, l1);
        canvas.drawLine(285, 700-4*D, 355, 700-4*D, l1);
        canvas.drawLine(355, 700, 355, 700-4*D, l1);

        canvas.drawRect(205, 700-4*E, 274, 700, p);
        canvas.drawLine(205, 700, 205, 700-4*E, l1);
        canvas.drawLine(205, 700-4*E, 275, 700-4*E, l1);
        canvas.drawLine(275, 700, 275, 700-4*E, l1);

        canvas.drawText("人数", 120, 280, p1);
        canvas.drawText("成绩", 620, 760, p1);

        canvas.drawText("0", 190, 740, p1);
        canvas.drawText("60", 270, 740, p1);
        canvas.drawText("70", 350, 740, p1);
        canvas.drawText("80", 430, 740, p1);
        canvas.drawText("90", 510, 740, p1);
        canvas.drawText("100", 590, 740, p1);

        canvas.drawText("0", 140, 710, p1);
        canvas.drawText("20", 140, 630, p1);
        canvas.drawText("40", 140, 550, p1);
        canvas.drawText("60", 140, 470, p1);
        canvas.drawText("80", 140, 390, p1);
        canvas.drawText("100", 140, 310, p1);

        canvas.drawText(String.valueOf((int)E), 227, 680-4*E, p1);
        canvas.drawText(String.valueOf((int)D), 307, 680-4*D, p1);
        canvas.drawText(String.valueOf((int)C), 387, 680-4*C, p1);
        canvas.drawText(String.valueOf((int)B), 467, 680-4*B, p1);
        canvas.drawText(String.valueOf((int)A), 547, 680-4*A, p1);

    }


    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return true;
    }

}

