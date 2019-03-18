package com.example.zhang.hitsystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Cir extends View {

    private Paint mPaint;
    Context mContext;
    float A=ScoreAnalyze.Anum;
    float B=ScoreAnalyze.Bnum;
    float C=ScoreAnalyze.Cnum;
    float D=ScoreAnalyze.Dnum;
    float E=ScoreAnalyze.Enum;

    public Cir(Context context) {
        super(context);
    }

    public Cir(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Cir(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        Paint p1 = new Paint();
        Paint p2 = new Paint();
        Paint p3 = new Paint();
        Paint p4 = new Paint();
        Paint p5 = new Paint();
        Paint p = new Paint();
        Paint pzi = new Paint();
        Paint l1 = new Paint();
        Paint pb = new Paint();

        p1.setAntiAlias(true);
        p2.setAntiAlias(true);
        p3.setAntiAlias(true);
        p4.setAntiAlias(true);
        p5.setAntiAlias(true);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        pzi.setTextSize(30);
        l1.setColor(Color.BLACK);
        l1.setStrokeWidth(5);
        pb.setColor(Color.parseColor("#EECFA1"));
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5);
        /**p1.setColor(Color.RED);
        p2.setColor(Color.YELLOW);
        p3.setColor(Color.BLUE);
        p4.setColor(Color.GREEN);
        p5.setColor(Color.BLACK);*/

        p1.setColor(Color.parseColor("#5677FC"));
        p2.setColor(Color.parseColor("#26B6F6"));
        p3.setColor(Color.parseColor("#9CCC65"));
        p4.setColor(Color.parseColor("#FFCA28"));
        p5.setColor(Color.parseColor("#E84E40"));

        /**p1.setColor(Color.BLACK);
        p2.setColor(Color.BLACK);
        p3.setColor(Color.BLACK);
        p4.setColor(Color.BLACK);
        p5.setColor(Color.BLACK);
        p1.setAlpha(255);
        p2.setAlpha(200);
        p3.setAlpha(150);
        p4.setAlpha(100);
        p5.setAlpha(50);
*/
        RectF rectF = new RectF(200, 300, 600, 700);
        canvas.drawArc(rectF, 0, (A/(A+B+C+D+E))*360, true, p1);
        canvas.drawArc(rectF, (A/(A+B+C+D+E))*360,(B/(A+B+C+D+E))*360, true, p2);
        canvas.drawArc(rectF, ((A+B)/(A+B+C+D+E))*360, (C/(A+B+C+D+E))*360, true, p3);
        canvas.drawArc(rectF, ((A+B+C)/(A+B+C+D+E))*360, (D/(A+B+C+D+E))*360, true, p4);
        canvas.drawArc(rectF, ((A+B+C+D)/(A+B+C+D+E))*360, (E/(A+B+C+D+E))*360, true, p5);
        canvas.drawArc(rectF, 0, (A/(A+B+C+D+E))*360, false, p);
        canvas.drawArc(rectF, (A/(A+B+C+D+E))*360,(B/(A+B+C+D+E))*360, true, p);
        canvas.drawArc(rectF, ((A+B)/(A+B+C+D+E))*360, (C/(A+B+C+D+E))*360, true, p);
        canvas.drawArc(rectF, ((A+B+C)/(A+B+C+D+E))*360, (D/(A+B+C+D+E))*360, true, p);
        canvas.drawArc(rectF, ((A+B+C+D)/(A+B+C+D+E))*360, (E/(A+B+C+D+E))*360, true, p);

        //canvas.drawLine(380, 760, 740, 980, l1);

        canvas.drawLine(380, 760, 380, 980, l1);
        canvas.drawLine(720, 760, 720, 980, l1);
        canvas.drawLine(380, 760, 720, 760, l1);
        canvas.drawLine(380, 980, 720, 980, l1);
        canvas.drawRect(381, 760, 720, 980, pb);
        canvas.drawText("蓝色为90-100分的学生", 400, 800, pzi);
        canvas.drawText("蓝色为80-90分的学生", 400, 840, pzi);
        canvas.drawText("蓝色为70-80分的学生", 400, 880, pzi);
        canvas.drawText("蓝色为60-70分的学生", 400, 920, pzi);
        canvas.drawText("蓝色为60分的学生", 400, 960, pzi);


        canvas.drawLine(30, 760, 30, 980, l1);
        canvas.drawLine(300, 760, 300, 980, l1);
        canvas.drawLine(30, 760, 300, 760, l1);
        canvas.drawLine(30, 980, 300, 980, l1);
        canvas.drawRect(31, 760, 300, 980, pb);
        canvas.drawText("90-100约占"+(int)((A/(A+B+C+D+E))*100)+"%", 50, 800, pzi);
        canvas.drawText("80-90约占"+(int)((B/(A+B+C+D+E))*100)+"%", 50, 840, pzi);
        canvas.drawText("70-80约占"+(int)((C/(A+B+C+D+E))*100)+"%", 50, 880, pzi);
        canvas.drawText("60-70约占"+(int)((D/(A+B+C+D+E))*100)+"%", 50, 920, pzi);
        canvas.drawText("60以下约占"+(int)((E/(A+B+C+D+E))*100)+"%", 50, 960, pzi);

    }


    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return true;
    }

}