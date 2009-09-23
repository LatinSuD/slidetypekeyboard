/*
 * Copyright (C) 2008-2009 Google Inc.
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
import java.util.List;

import android.view.KeyEvent;
import android.view.MotionEvent;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;

public class LatinKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;
    static int direction;
    
    static boolean sShiftState;
    static boolean sAltState;
    
    static long downTime=0;
        
    @Override
    public boolean setShifted(boolean newState) {
    	sShiftState=newState;
    	if (newState)
    		sAltState=false;
    	
    	super.setShifted(!(sShiftState || sAltState));
    	
    	return super.setShifted(sShiftState || sAltState);
    //	invalidate();
//    	return sShiftState;
    }

    public void setAlt(boolean newState) {
    	sAltState=newState;
    	
    	if (newState)
    		sShiftState=false;
    	
    	super.setShifted(!(sShiftState || sAltState));
    	super.setShifted(sShiftState || sAltState);
    }
    
    public void setNormal() {
    	if (sAltState) {
    		setShifted(true);
    		setShifted(false);
    	} else if (sShiftState) {
    		setShifted(false);
    	}
    }
    
    public void rotateAltShift() {
    	if (sAltState) {
    		setShifted(true);
    	} else if (sShiftState) {
    		setShifted(false);
    	} else {
    		setAlt(true);
    	}
    }
    
//    static int keysAtOnce;

    public LatinKeyboardView(Context context, AttributeSet attrs) {
      super(context, attrs);
      //setProximityCorrectionEnabled(false);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //setProximityCorrectionEnabled(false);
    }
        

	// Esto esta bien, pero solo para unas pocas teclas concretas
    @Override
    protected boolean onLongPress(Key key) {    	
        if (key.codes[0] == '\n') {
        	direction=-1;
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
        	return super.onLongPress(key);
        }        
    }

    
/*
    // parece que las swipe no van muy finas
    protected void swipeUp() {
    }
    */
    
    public float downX;
    public float downY;
    public static float minSlide=10;
    private int lastDirection=-2;
    
    
    public boolean onTouchEvent(MotionEvent me) {
    	int act = me.getAction();
    	if (act==android.view.MotionEvent.ACTION_DOWN) {
    		//keysAtOnce=0;
    		downTime=me.getEventTime();
    		lastDirection=direction=0;
    		downX=me.getX();
    		downY=me.getY();
    	} else if (act==android.view.MotionEvent.ACTION_UP || act==android.view.MotionEvent.ACTION_MOVE) {
    		float dy=me.getY()-downY;
    		float dx=me.getX()-downX;
    		if (Math.abs(dx)>minSlide || Math.abs(dy)>minSlide) {
    			if (dy > dx) {
    				if (dy > -dx) {
    					direction=4;	
    				} else {
    					direction=1;
    				}
    			} else {
    				if (dy > -dx) {
    					direction=3;
    				} else {
    					direction=2;
    				}
    			}
    		} else {
    			direction=0;
    		}

    		if (act==android.view.MotionEvent.ACTION_MOVE) {
				/* TODO De momento lo dejamos asi */
    			// allow redraw only on slidable keys
    			//  except shift, which would have side effects
				if (lastDirection!=direction && (
						(
								SlideTypeKeyboard.pressedCode>='0' &&
								SlideTypeKeyboard.pressedCode<='9'
						) || SlideTypeKeyboard.pressedCode=='*'
					 )
					) {
					lastDirection=direction;
					downTime=me.getEventTime();

    				// hack to force update of iconPreview
    				me.setLocation(0, 0);
    				super.onTouchEvent(me);
    				me.setLocation(downX, downY);    				    				
    			} else {
    				// cancel to prevent highliting when rolling over other keys
    				return true;
    			}
    		} else if (act==android.view.MotionEvent.ACTION_UP) {
    			// simulate long press
				if (me.getEventTime() - downTime > 800) {
					Key theKey=null;
					for (Key key: getKeyboard().getKeys()) {
						if (key.codes[0]==SlideTypeKeyboard.pressedCode)
							theKey=key;
					}
					
					if (theKey != null) {
						// hack to make close preview window
						me.setAction(android.view.MotionEvent.ACTION_MOVE);
						me.setLocation(0, 0);
						super.onTouchEvent(me);
						this.onLongPress(theKey);
					}
					
					//return false;
				}
    		}
    	}
    	
		// aqui es donde luego llama a todo lo demas
		return super.onTouchEvent(me);
}   

   /*
    @Override
    public void onDraw(Canvas canvas) {
    	canvas.drawColor(Color.BLACK);
    	super.onDraw(canvas);
    }
    
    	Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    	mPaint.setColor(Color.WHITE);
    	Keyboard kbd=getKeyboard();
    	//Keyboard.Row rr=kbd.
    	List<Keyboard.Key> keys = kbd.getKeys();
    	ListIterator<Keyboard.Key> it=keys.listIterator();
    	
    	super.onDraw(canvas);
    	
    	char[] letter= new char[1];
    	Rect bounds=new Rect();
    	
    	while (it.hasNext() 
    //	&& maxi++<5
    ) {
    			LatinKeyboard.LatinKey key=(LatinKeyboard.LatinKey)it.next();
    		//	paint.Style.
    			//canvas.drawRect(r, paint)
    			//key.
    			
    	    	//android.os.Debug.waitForDebugger();    			
    			//mPaint.getTextBounds(txt, 0, txt.length(), bounds);
    			mPaint.setTextSize(key.height/3);
    			
    				if (key.fancyLabel != null) {
    					int keyX=key.x+key.width/2;
    					int keyY=key.y+key.height/2;
    					int tW0,tH0,tMX,tMY;
    					
	    				letter[0]=key.fancyLabel.charAt(0);
	    				mPaint.getTextBounds(letter, 0, 1, bounds);
	    				tW0=bounds.right-bounds.left;
	    				tMX=(bounds.right-bounds.left)/2;
	    				tH0=bounds.top-bounds.bottom;
	    				tMY=(bounds.top-bounds.bottom)/2;
	    				
	    				mPaint.setColor(Color.RED);
	    				bounds.offset(keyX-tMX, keyY-tMY);
	    				canvas.drawRect(bounds, mPaint);
	    				mPaint.setStyle(Style.STROKE);
	    				canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
	    				mPaint.setColor(Color.WHITE);
	    				canvas.drawText(key.fancyLabel, 0, 1, (float)keyX-tMX, (float)keyY-tMY, mPaint);    	        			
    					
    					
    				}
    	}
    	
    }
    */	
  
}