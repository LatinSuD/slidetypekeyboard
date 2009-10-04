/*
 * Copyright (C) 2008-2009 Google Inc.
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
import java.util.List;

import android.view.KeyEvent;
import android.view.MotionEvent;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;

/* This class is the View of the keyboard.
 * Currently extends KeyboardView class.
 * It's main duty is to receive touch events.
 */
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
    

    public LatinKeyboardView(Context context, AttributeSet attrs) {
      super(context, attrs);
      //setProximityCorrectionEnabled(false);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //setProximityCorrectionEnabled(false);
    }
        

	// 
    @Override
    protected boolean onLongPress(Key key) {    	
        if (key.codes[0] == '\n') {
        	direction=-1;
        	// force launch options activity
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
        	return super.onLongPress(key);
        }        
    }

    
    /*
    // swipe functions seem not to work here
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
    	
		// after we return here the service will get notified, etc
		return super.onTouchEvent(me);
    }   

  
}
