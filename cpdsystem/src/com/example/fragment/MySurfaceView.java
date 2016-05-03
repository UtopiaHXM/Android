package com.example.fragment;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;
	int P_w, P_h;
	List<int[]> D_data, D_data1;
	// int[] color_now;
	int[] test_data, test_data1, test_data2;
	int[] test_data3, test_data4, test_data5;
	static List<CPD_Data> CP_Grp;
	static boolean white_R = true;
	// int CP_Max;
	
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// 初始化SurfaceHolder对象
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		// TODO Auto-generated method stub
		// 按钮中触发内容开始
		CP_Grp = null;
		new Thread(new MyInitThread()).start();
		new Thread(new MyDrawThread()).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public static void setCP_Grp(List<CPD_Data> CPDData_Grp) {
		CP_Grp = CPDData_Grp;
	}
 

	class MyDrawThread implements Runnable {
		
		@Override
		public void run() {
			Log.d("www", "wwwwwwwwww");
			int[] color_now;

			while (led.isRunBoolean) {
				canvas = holder.lockCanvas(null);// 获取画布
				Paint mPaint = new Paint();
				if (white_R) {
					// Log.d("eee","CP_Grp:"+CP_Grp.size());
					mPaint.setARGB(255, 255, 255, 255);
					canvas.drawCircle(lawnFragment.screenWidth * 11 / 40,
							lawnFragment.screenHeight / 2 - 80, 80.0f, mPaint);
				} else {
					Log.d("aaa", "CP_Grp != null");
					Log.d("aaa", "CP_Grp+size:" + CP_Grp.size() + "123"
							+ CP_Grp.get(led.index_CPD).name);
					// 组颜色变化
					color_now = new int[3];

					color_now = CP_Grp.get(led.index_CPD).r_C;
					Log.d("aaa", "颜色生成：" + color_now[0] + "," + color_now[1]
							+ "," + color_now[2]);
					mPaint.setARGB(255, color_now[0], color_now[1],
							color_now[2]);
					// 组图形生成
					canvas.drawCircle(CP_Grp.get(led.index_CPD).r_X,
							CP_Grp.get(led.index_CPD).r_Y,
							CP_Grp.get(led.index_CPD).r_R, mPaint);
					mPaint.setColor(color.white);
					canvas.drawText(CP_Grp.get(led.index_CPD).name, 100, 100,
							mPaint);
				}
				holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
				holder.lockCanvas(new Rect(0, 0, 0, 0));
				holder.unlockCanvasAndPost(canvas);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	class MyInitThread implements Runnable {

		@Override
		public void run() {
			while (led.isRunBoolean) {
				if (CP_Grp != null) {
					for (int i = 0; i < CP_Grp.size(); i++) {
						CP_Grp.get(i).setColor();
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// TODO Auto-generated method stub

		return false;
	}
}
