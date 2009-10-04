/*
 * Copyright (C) 2009 Alejandro Grijalba
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


package com.latinsud.android.slidetypekeyboard;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* Class that displays a popup preview of each key
 *  It is shown when pressing a key, and hides when releasing the key.
 */

class TextDrawable extends Drawable {
	int width;
	int height; 
	Rect bounds;
	LatinKeyboard.LatinKey key;
	static float horizontalPadding=(float)0.95;
	static float verticalPadding=(float)0.85;
	static int kkCount=0;
	Canvas theCanvas;
	
	// auxiliary variables
	static char[] letter = new char[1];
	static Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	static float tW0,tMX,tH0,tMY;
	
	static TextDrawable theInstance;
	
	public  TextDrawable(LatinKeyboard.LatinKey aKey) {
		key=aKey;
		bounds=new Rect();
	}
	
	@Override
	public void draw(Canvas canvas) {
        //float keyX=key.width*horizontalPadding/2;
        float keyY=key.height*verticalPadding/2;

        float textSize=key.height*(float)0.7;
        mPaint.setTextSize(textSize);


        letter[0]=(char)SlideTypeKeyboard.getCharFromKey(SlideTypeKeyboard.pressedCode, LatinKeyboardView.direction);
        if (LatinKeyboardView.sShiftState)
        	letter[0]=Character.toUpperCase(letter[0]);
        	
        
        mPaint.getTextBounds(letter, 0, 1, bounds);
        tMX=(bounds.right+bounds.left)/(float)2.0;
        Paint.FontMetrics pfm = mPaint.getFontMetrics();
        tMY = (pfm.ascent+pfm.descent)/(float)2.0;

        
		canvas.drawText(String.valueOf(letter[0]), -tMX, -keyY-tMY, mPaint);
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
	
	// called each time it is drawn
	@Override
	public int getIntrinsicHeight() {
		return (int)(key.height*verticalPadding);
	}
	
	@Override
	public int getMinimumWidth() {
		int kk=3+2;
		return kk;
	}
	
	@Override
	public void onBoundsChange(Rect newBounds) {
		bounds.set(newBounds);
	}


}

