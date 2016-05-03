package com.example.fragment;

import android.opengl.GLSurfaceView;

import com.Tools.ActionSingle;
import com.Tools.MyRenderer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class MyGlSurfaceView extends GLSurfaceView {

	int[] Five_Colors = new int[] {
	           65535, 0, 0, 0, 
	           65535, 0, 0, 0, 
	           65535, 0, 0, 0, 
	           65535, 0, 0, 0, 
	           65535, 0, 0, 0,
	           65535, 0, 0, 0,
	           65535, 0, 0, 0,
	           65535, 0, 0, 0,
	           65535, 0, 0, 0, 
	           65535, 0, 0, 0
	};
	
	public MyGlSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
			MyRenderer myRender = new MyRenderer();
		// Œ™GLSurfaceView…Ë÷√ªÊ÷∆∆˜
			
			this.setRenderer(myRender);
            
			ActionSingle.setMyRenderer(myRender);
			Log.d("ccc","sdfsafdas");
			
	}

}