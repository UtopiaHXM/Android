package com.example.Add_listview_style;



import com.example.meimei.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 圆角ListView
 */
public class listview_style extends ListView {
	public listview_style(Context context) {
		super(context);
	}

	public listview_style(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public listview_style(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int itemnum = pointToPosition(x, y);
			if (itemnum == AdapterView.INVALID_POSITION)
				break;
			else {
				if (itemnum == 0) {
					if (itemnum == (getAdapter().getCount() - 1)) {
						//只有一项
						setSelector(R.drawable.listview_center_style);
					} else {
						//第一项
						setSelector(R.drawable.listview_top_style);
					}
				} else if (itemnum == (getAdapter().getCount() - 1))
					//最后一项
					setSelector(R.drawable.listview_bottom_style);
				else {
					//中间项
					setSelector(R.drawable.listview_center_style);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
}