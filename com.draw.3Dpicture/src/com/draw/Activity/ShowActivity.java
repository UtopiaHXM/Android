package com.draw.Activity;

import com.draw.R;
import com.draw.Tools.drawRender;
import com.draw.Tools.infoInit;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShowActivity extends Activity {

	private float[] Five_points;
	private int[] Five_colors;
	private byte[] Five_faces;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
		//五面体顶点集合
		Five_points = new float[]{
				//前五个点
			0.10f,0,0,   //a1
			0.309f,0.95f,0,  //b1
			-0.809f,0.588f,0,  //c1
			-0.809f,-0.588f,0,  //d1
			0.309f,-0.951f,0,   //e1
			    //后五点
			
			0.10f,0,0.25f,   //a2
			0.309f,0.95f,0.25f,  //b2
			-0.809f,0.588f,0.25f,  //c2
			-0.809f,-0.588f,0.25f,  //d2
			0.309f,-0.951f,0.25f   //e2
		};
		//五面体顶点颜色集
		Five_colors = new int[] {
				65535, 0, 0, 0, 
				65535, 65535, 0, 0, 
				65535,0, 65535, 0, 
				65535, 0, 0, 0, 
				65535, 65535, 0, 0,
				65535, 0, 65535,0,
				65535, 0, 0, 0, 
			    65535, 65535, 0, 0, 
				65535, 0, 65535, 0,
				65535,0, 0, 0
	   };
	
	//五面体面集合集
	Five_faces = new byte[] { 
			0, 1, 2,
			0, 2, 3,
			0, 3, 4,
			5, 6,7,
			5, 7, 8, 
			5, 8, 9, 
			0, 1, 5, 
			1, 5, 6, 
			1, 2, 6,
			2, 6, 7, 
			2, 3, 7,
			3, 7, 8,
			3, 4, 9,
			3, 8, 9,
			0, 4, 9,
			0, 5, 9
		};

	//创建GLSuperfaceView 显示OpenGl绘制的图形
	GLSurfaceView glView = new GLSurfaceView(this);
	
	/*infoInit info = new infoInit(0, 0, 0, 0, 0, 0, Five_points, Five_colors, Five_faces);
	//创建绘制器
	drawRender myRender = new drawRender(info);*/
	drawRender myRender = new drawRender();
	glView.setRenderer(myRender);
	setContentView(glView);
	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
		return true;
	}

}
