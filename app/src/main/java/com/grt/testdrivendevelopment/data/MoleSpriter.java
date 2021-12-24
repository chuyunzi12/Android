package com.grt.testdrivendevelopment.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public class MoleSpriter {
    private float x,y;
    private Bitmap molepic;
    private int moleWidth , moleHeight;
    private float maxWidth,maxHight;

    public MoleSpriter(float x, float y, float maxWidth, float maxHight,Bitmap molepic) {
        this.x = x;
        this.y = y;
        this.molepic=molepic;
        moleHeight=molepic.getHeight();
        moleWidth=molepic.getWidth();
        this.maxHight=maxHight-moleHeight;
        this.maxWidth=maxWidth-moleWidth;
    }

    public void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        canvas.drawBitmap(molepic,x,y,paint);
    }

    public void move(){
        this.x= (float) (Math.random()*maxWidth);
        this.y= (float) (Math.random()*maxHight);
    }

    public boolean isshoted(float touchx, float touchy) {
        if(((touchx-x)<=moleWidth)&&((touchy-y)<=moleHeight)){
            return true;
        }
        else
            return false;
    }
}

