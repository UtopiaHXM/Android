package com.example.Activities;

import com.example.meimei.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class View_flow_activity extends AbstractActivity {

	static boolean isRun = false;
	ViewFlipper viewFlipper = null;
	LinearLayout view_3;
	LinearLayout view_4;
	LinearLayout view_2;
	float startX;
	static int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_flow_main);

		view_2 = (LinearLayout) findViewById(R.id.view_2);
		view_3 = (LinearLayout) findViewById(R.id.view_3);
		view_4 = (LinearLayout) findViewById(R.id.view_4);
		init();
	}

	private void init() {
		viewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			break;
		case MotionEvent.ACTION_UP:

			if (event.getX() > startX) {
				// 向右滑动
				if (count != 0) {
					viewFlipper.setInAnimation(this, R.anim.in_rightleft);
					viewFlipper.setOutAnimation(this, R.anim.out_rightleft);
					viewFlipper.showPrevious();
					count--;
				}

			} else

			if (event.getX() < startX) {
				// 向左滑动
				if (count == 2) {
					Log.d("tag", "count view = "+count);
					if(isRun){
						View_flow_activity.this.setResult(0, getIntent());
						View_flow_activity.this.finish();
						
					}else{
						
						Log.d("tag", "tag");
						Intent intent = new Intent();
						intent.setClass(View_flow_activity.this,
								chooseConnWay.class);
						View_flow_activity.this.startActivity(intent);
					}
					
				} else {
					viewFlipper.setInAnimation(this, R.anim.in_leftright);
					viewFlipper.setOutAnimation(this, R.anim.out_leftright);
					viewFlipper.showNext();
					count = count + 1;
				}
			}
			break;
		}

		return super.onTouchEvent(event);
	}

}