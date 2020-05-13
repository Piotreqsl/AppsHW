package com.onionv2.apps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class ZegarView extends View {

    private int height, width = 0;
    private int padding = 50;
    private int fontSize = 40;
    private int handTruncation, hourHandTruncation = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private boolean isSystem = true;
    private boolean isModeChosen = false;
    private int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12};
    private Rect rect = new Rect();
    private Canvas _canvas;
    private Calendar c;
    private Calendar stoper = Calendar.getInstance();
    private boolean isStoper = false;


    public ZegarView(Context context) {
        super(context);
    }

    public ZegarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZegarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void startZegara() {
        height = getHeight();
        width = getWidth();
        int min = Math.min(height, width);
        radius = min / 2 - padding; // do koła
        handTruncation = min / 20;
        hourHandTruncation = min / 7;
        paint = new Paint();
        isInit = true;



    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            startZegara();
            stoper.set(Calendar.HOUR_OF_DAY, 0);
            stoper.set(Calendar.MINUTE, 0);
            stoper.set(Calendar.SECOND, 0);
            stoper.set(Calendar.MILLISECOND, 0);
        }
        _canvas = canvas;

        if(isStoper){
            stoper.add(Calendar.SECOND, 1);
            Log.d("adding", stoper.toString());
        }


        Paint p = new Paint();
        p.setShader(new LinearGradient(0, 0, 0, getHeight(), getResources().getColor(R.color.gradient),  getResources().getColor(R.color.gradient1), Shader.TileMode.MIRROR));
        canvas.drawPaint(p);
        rysujKolo(canvas);
        rysujSrodek(canvas);
        rysujCyfry(canvas);
        rysujWskazowki(canvas, c);


        //to słuzy do narysowania wszystkiego na nowo zeby sie wskazowki obracały


        postInvalidateDelayed(10);



       // invalidate();
    }

    private void rysujWskazowke(Canvas canvas, double loc, boolean isHour, boolean isColorDif) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int handRadius = isHour ? radius - handTruncation - hourHandTruncation : radius - handTruncation;




        if(!isColorDif) {
            canvas.drawLine(width / 2, height / 2,
                    (float) (width / 2 + Math.cos(angle) * handRadius),
                    (float) (height / 2 + Math.sin(angle) * handRadius),
                    paint);
        }else{


            canvas.drawLine(width / 2, height / 2,
                    (float) (width / 2 + Math.cos(angle) * handRadius),
                    (float) (height / 2 + Math.sin(angle) * handRadius),
                    paint);

        }
    }

    private void rysujWskazowki(Canvas canvas, Calendar custom) {
        if(isStoper){ // stoper chodzi


            float hour = stoper.get(Calendar.HOUR_OF_DAY);
            hour = hour > 12 ? hour - 12 : hour;
            rysujWskazowke(canvas, (hour + stoper.get(Calendar.MINUTE) / 60) * 5f, true, false);
            rysujWskazowke(canvas, stoper.get(Calendar.MINUTE), false, false);
            rysujWskazowke(canvas, stoper.get(Calendar.SECOND), false, false);

            return;
        }

        if(!isStoper && !isModeChosen){ // stoper zatrzymany


            float hour = stoper.get(Calendar.HOUR_OF_DAY);
            hour = hour > 12 ? hour - 12 : hour;
            rysujWskazowke(canvas, (hour + stoper.get(Calendar.MINUTE) / 60) * 5f, true, false);
            rysujWskazowke(canvas, stoper.get(Calendar.MINUTE), false, false);
            rysujWskazowke(canvas, stoper.get(Calendar.SECOND), false, false);

            return;
        }


        if(isModeChosen && isSystem){

            MediaPlayer mPlayer = MediaPlayer.create(getContext(), R.raw.tick);
            mPlayer.start();

            Calendar c = Calendar.getInstance();
            float hour = c.get(Calendar.HOUR_OF_DAY);
            hour = hour > 12 ? hour - 12 : hour;
            rysujWskazowke(canvas, (hour + c.get(Calendar.MINUTE) / 60) * 5f, true, false);
            rysujWskazowke(canvas, c.get(Calendar.MINUTE), false, false);
            rysujWskazowke(canvas, c.get(Calendar.SECOND), false, false);
            //rysujWskazowke(canvas, c.get(Calendar.MILLISECOND), false, true);

        }

        if(isModeChosen && !isSystem){




            float hour = custom.get(Calendar.HOUR_OF_DAY);
            hour = hour > 12 ? hour - 12 : hour;
            rysujWskazowke(canvas, (hour + custom.get(Calendar.MINUTE) / 60) * 5f, true, false);
            rysujWskazowke(canvas, custom.get(Calendar.MINUTE), false, false);
        }



    }

    private void rysujCyfry(Canvas canvas) {
        paint.setTextSize(fontSize);

        for (int number : numbers) {
            String tmp = String.valueOf(number);
            paint.getTextBounds(tmp, 0, tmp.length(), rect);
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
            canvas.drawText(tmp, x, y, paint);
        }
    }

    private void rysujSrodek(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 12, paint);
    }

    private void rysujKolo(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius + padding - 10, paint);
    }

    public void setSystemTime(){
        isModeChosen = true;
        isSystem = true;
        isStoper = false;
        rysujWskazowki(_canvas, c);
    }

    public void setCustomTime(Calendar c){
        isModeChosen = true;
        isSystem = false;
        isStoper = false;
        this.c = c;
        rysujWskazowki(_canvas, c);


    }

    public void startStoper(){
        isModeChosen = false;
        isSystem = false;
        isStoper = !this.isStoper;

        rysujWskazowki(_canvas, Calendar.getInstance());

    }

    public void zerujStoper(){
        stoper.set(Calendar.HOUR_OF_DAY, 0);
        stoper.set(Calendar.MINUTE, 0);
        stoper.set(Calendar.SECOND, 0);
        stoper.set(Calendar.MILLISECOND, 0);

        rysujWskazowki(_canvas, Calendar.getInstance());
    }


}