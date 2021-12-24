package com.grt.testdrivendevelopment.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.grt.testdrivendevelopment.R;
import com.grt.testdrivendevelopment.data.MoleSpriter;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private ArrayList<MoleSpriter> spriters=new ArrayList<>();

    public GameView(Context context) {
        super(context);
        initview();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initview();
    }

    private void initview() {
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
    }
    private DrawThread drawThread;
    private float touchx,touchy;
    private  boolean istouched=false;

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {


        drawThread = new DrawThread();
        drawThread.start();
        GameView.this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.ACTION_DOWN==motionEvent.getAction())
                {
                    touchx=motionEvent.getRawX();
                    touchy=motionEvent.getRawY();
                    istouched=true;
                }
                return false;
            }
        });
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

        drawThread.mystop();
    }

    private class DrawThread extends Thread {
        public boolean isstoped=false;


        public DrawThread() {
            if (spriters.size()==0){
                Bitmap molepic = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.mole);
                spriters.add(new MoleSpriter(1,1,GameView.this.getWidth(),GameView.this.getHeight(),molepic));
            }
        }

        public void mystop(){
            isstoped=true;

        }

        @Override
        public void run() {
            super.run();
            Canvas canvas = null;
            int shotcount=0;
            Paint paint=new Paint();
            paint.setColor(Color.GREEN);
            paint.setTextSize(100);


            while (!isstoped){
                try {
                    canvas=surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.GRAY);
                    for (int i=0;i<spriters.size();i++){
                        spriters.get(i).move();
                        spriters.get(i).draw(canvas);
                    }
                    Thread.sleep(1000);
                    if(GameView.this.istouched) {
                        for (int i = 0; i < spriters.size(); i++) {
                            if (spriters.get(i).isshoted(GameView.this.touchx, GameView.this.touchy - 250)) {
                                shotcount++;
                            }
                            GameView.this.istouched = false;
                        }
                    }
                    canvas.drawText("shot"+shotcount,100,100,paint);
                    postInvalidate();

                }catch (Exception e){
                }finally {
                    if (null!=canvas){
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

            }
        }
    }
}
