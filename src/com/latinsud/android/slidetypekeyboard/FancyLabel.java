package com.latinsud.android.slidetypekeyboard;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;


class fancyLabelDraw extends Drawable {
	int width;
	int height; 
	Rect bounds;
	LatinKeyboard.LatinKey key;
	static float horizontalPadding=(float)0.95;
	static float verticalPadding=(float)0.85;
	
	// auxiliary variables
	static char[] letter = new char[1];
	static Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	static float tW0,tMX,tH0,tMY, tM0;
	
	public  fancyLabelDraw(LatinKeyboard.LatinKey aKey) {
		key=aKey;
		bounds=new Rect();
	}
		
	
	@Override
	public void draw(Canvas canvas) {
		if (SlideTypeKeyboard.keyLayout==1) {
			drawNumLeft(canvas);
		} else if (SlideTypeKeyboard.keyLayout==2) {
			drawNumCenter(canvas);
		} else {
			drawNumBehind(canvas);
		}
	}
	
	private char getLetter(int idx) {
		char res=key.fancyLabel.charAt(idx);
		if (LatinKeyboardView.sShiftState)
			res=Character.toUpperCase(res);
		return res;
	}
		
	private void drawNumCenter(Canvas canvas) { 
        // TODO Auto-generated method stub
        //canvas.drawColor(Color.MAGENTA);
//      Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        //mPaint.setColor(Color.CYAN);
//      mPaint
//      canvas.drawRect(bounds, mPaint);
//      mPaint.setColor(Color.WHITE);
//      android.os.Debug.waitForDebugger();


        if (key.fancyLabel != null) {
                float keyX=key.width*horizontalPadding/2;
                float keyY=key.height*verticalPadding/2;

                // Set size and correct it
                float textSize=key.height*verticalPadding/3;
                mPaint.setTextSize(textSize);
                Paint.FontMetrics pfm = mPaint.getFontMetrics();
                textSize = textSize * textSize/(pfm.descent-pfm.ascent);
                mPaint.setTextSize( textSize );
                pfm = mPaint.getFontMetrics();

                tH0 = (pfm.ascent-pfm.descent);
                tMY = (pfm.ascent+pfm.descent)/(float)2.0;


                // center
                letter[0]=getLetter(0);
                mPaint.getTextBounds(letter, 0, 1, bounds);
                tW0=(bounds.right-bounds.left);
                tMX=(bounds.right+bounds.left)/(float)2.0;
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint.setColor(Color.YELLOW);
                canvas.drawText(letter, 0, 1, keyX-tMX, keyY-tMY, mPaint);

                // left & right
                letter[0]=getLetter(1);
                mPaint.getTextBounds(letter, 0, 1, bounds);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint.setColor(Color.WHITE);
                canvas.drawText(letter, 0, 1, keyX-bounds.right -tW0/2-3, keyY-tMY, mPaint);

                letter[0]=getLetter(3);
                mPaint.getTextBounds(letter, 0, 1, bounds);
                canvas.drawText(letter, 0, 1, keyX-bounds.left +tW0/2+3, keyY-tMY, mPaint);
        
                // up & down
                letter[0]=getLetter(2);
                mPaint.getTextBounds(letter, 0, 1, bounds);
                tMX=(bounds.right+bounds.left)/(float)2.0;
                canvas.drawText(letter, 0, 1, keyX-tMX, keyY-tMY+tH0-1, mPaint);

                letter[0]=getLetter(4);
                mPaint.getTextBounds(letter, 0, 1, bounds);
                tMX=(bounds.right+bounds.left)/(float)2.0;
                canvas.drawText(letter, 0, 1, keyX-tMX, keyY-tMY-tH0+1, mPaint);
        }

	}
	
	
	private void drawNumLeft(Canvas canvas) {
		// TODO Auto-generated method stub		
		
		if (key.fancyLabel != null) {
			float keyX=key.width*horizontalPadding*(float)0.7;
			float keyY=key.height*verticalPadding/2;
	
			// Set size and correct it
			float textSize=key.height*verticalPadding/2;
			mPaint.setTextSize(textSize);
			Paint.FontMetrics pfm = mPaint.getFontMetrics();
			textSize = textSize * textSize/(pfm.descent-pfm.ascent);
			mPaint.setTextSize( textSize );	
			pfm = mPaint.getFontMetrics(); 
			
			tH0 = (pfm.ascent-pfm.descent);
			

			// main symbol
			letter[0]=getLetter(0);
			mPaint.setTextSize( textSize * (float)1.4);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMX=(bounds.right+bounds.left)/(float)2.0;
			tMY = (pfm.ascent+pfm.descent)/(float)2.0;
			mPaint.setTypeface(Typeface.DEFAULT_BOLD);
			mPaint.setColor(Color.YELLOW);
			canvas.drawText(letter, 0, 1, key.width*horizontalPadding*(float)0.2-tMX, keyY-tMY, mPaint);
			
			mPaint.setTypeface(Typeface.DEFAULT);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextSize( textSize );

			// up & down
			letter[0]=getLetter(2);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMX=(bounds.right+bounds.left)/(float)2.0;
			tW0=(bounds.right-bounds.left);
			canvas.drawText(letter, 0, 1, keyX-tMX, keyY-tMY+tH0/2-1, mPaint);

			letter[0]=getLetter(4); 
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMX=(bounds.right+bounds.left)/(float)2.0;
			if (bounds.right-bounds.left > tW0)
				tW0=(bounds.right-bounds.left);
			canvas.drawText(letter, 0, 1, keyX-tMX, keyY-tMY-tH0/2+1, mPaint);

			// left & right
			letter[0]=getLetter(1);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMY = (pfm.ascent+pfm.descent)/(float)2.0;
			canvas.drawText(letter, 0, 1, keyX-bounds.right -tW0/2-2, keyY-tMY, mPaint);
			
			letter[0]=getLetter(3);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			canvas.drawText(letter, 0, 1, keyX-bounds.left +tW0/2+2, keyY-tMY, mPaint);
		}	
	}
	
	private void drawNumBehind(Canvas canvas) {
		// TODO Auto-generated method stub		
		
		if (key.fancyLabel != null) {
			float keyX=key.width*horizontalPadding/2;
			float keyY=key.height*verticalPadding/2;
	
			// Set size and correct it
			float textSize=key.height*verticalPadding/2;
			mPaint.setTextSize(textSize);
			Paint.FontMetrics pfm = mPaint.getFontMetrics();
			textSize = textSize * textSize/(pfm.descent-pfm.ascent);
			mPaint.setTextSize( textSize );	
			pfm = mPaint.getFontMetrics(); 
			
			tH0 = (pfm.ascent-pfm.descent);
			
float tMY0, tMX0;
			
			// main symbol
			letter[0]=getLetter(0);
			mPaint.setTextSize( textSize * (float)2);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMX0=(bounds.right+bounds.left)/(float)2.0;
			tMY = (pfm.ascent+pfm.descent)/(float)2.0;
			tMY0 = (bounds.top+bounds.bottom)/(float)2.0;
			mPaint.setTypeface(Typeface.DEFAULT_BOLD);
			mPaint.setColor(0xFF706040);
			canvas.drawText(letter, 0, 1, keyX-tMX0, keyY-tMY0, mPaint);
			
			//mPaint.setTypeface(Typeface.DEFAULT);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextSize( textSize );

			// up & down
			letter[0]=getLetter(2);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMX=(bounds.right+bounds.left)/(float)2.0;
			tW0=(bounds.right-bounds.left);
			canvas.drawText(letter, 0, 1, keyX-tMX, keyY-tMY+tH0/2-1, mPaint);

			letter[0]=getLetter(4); 
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMX=(bounds.right+bounds.left)/(float)2.0;
			if (bounds.right-bounds.left > tW0)
				tW0=(bounds.right-bounds.left);
			canvas.drawText(letter, 0, 1, keyX-tMX, keyY-tMY-tH0/2+1, mPaint);

			// left & right
			letter[0]=getLetter(1);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			tMY = (pfm.ascent+pfm.descent)/(float)2.0;
			canvas.drawText(letter, 0, 1, keyX-bounds.right -tW0/2-4, keyY-tMY, mPaint);
			
			letter[0]=getLetter(3);
			mPaint.getTextBounds(letter, 0, 1, bounds);
			canvas.drawText(letter, 0, 1, keyX-bounds.left +tW0/2+4, keyY-tMY, mPaint);

			/*
			// Draw semi-transparent background again
			letter[0]=getLetter(0);
			mPaint.setTextSize( textSize * (float)2);
			mPaint.setTypeface(Typeface.DEFAULT_BOLD);
			mPaint.setColor(0x20706040);
			canvas.drawText(letter, 0, 1, keyX-tMX0, keyY-tMY0, mPaint);
			 */
		}	
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void setBounds(Rect r) {
		int kk=3+2;
		kk=kk+3;
	}
    
	@Override
	public int getIntrinsicWidth() {
		return (int)(key.width*horizontalPadding);
		//return 10;//width;
	}
	
	// cada vez que se dibuja
	@Override
	public int getIntrinsicHeight() {
		return (int)(key.height*verticalPadding);
	}
	
	@Override
	public int getMinimumWidth() {
		int kk=3+2;
		return kk;
	}

	/*
	@Override
	public void   	  setBounds(int left, int top, int right, int bottom) {
		int kk=3+2;
		kk=kk+3;
		//return kk;
	}
	*/	
	
	@Override
	public void onBoundsChange(Rect newBounds) {
		bounds.set(newBounds);
	}


}

