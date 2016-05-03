
package com.example.cleverlampcontrol;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;

public class MyView extends View {
	private Paint mPaint;
	private Context mContext;
	List<Float> length_list = new ArrayList<Float>();//矩形的横向长度
	float length_begin=0,length_end=0;
	ArrayList<Integer> color1 = new ArrayList<Integer>();//存储radiogroup中的颜色值
	int color2,select_number;//现在使用的颜色
	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attr) {
		super(context, attr);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);		
		for(int i=0;i<length_list.size();i++){
			if(length_end<551){
		if(i==0){
			length_begin=0;
			length_end = length_list.get(i); 
			color2=color1.get(i);
			mPaint.setColor(Color.RED);
			if(color2==1){
				mPaint.setColor(Color.RED);
				}
				if(color2==2){
				mPaint.setColor(Color.GREEN);	
				}
				if(color2==3){
				mPaint.setColor(Color.BLUE);	
				}
			if(select_number==i){
				canvas.drawRect(new Rect(10+(int)length_begin, 17, (int)length_end+10, 39), mPaint);
			}
			else{
			canvas.drawRect(new Rect(10+(int)length_begin, 20, (int)length_end+10, 35), mPaint);
			}
		}else{
			length_begin=length_list.get(i-1)+length_begin;
			length_end=length_list.get(i)+length_end;
			color2=color1.get(i);
			if(color2==1){
				mPaint.setColor(Color.RED);
				}
				if(color2==2){
				mPaint.setColor(Color.GREEN);	
				}
				if(color2==3){
				mPaint.setColor(Color.BLUE);	
				}
				if(select_number==i){
					canvas.drawRect(new Rect(10+(int)length_begin, 17, (int)length_end+10, 39), mPaint);
				}
				else{
				canvas.drawRect(new Rect(10+(int)length_begin, 20, (int)length_end+10, 35), mPaint);
				}
		  }		
		}else{
			length_begin=0;
			length_end=0;
		}			
	}
}
	public void setLong(ArrayList list){
		length_list = list;
	}
	public void setColor(int i,int color){
		color1.add(i,color);	
	}
	public void deleteColor(int color_xiang){
		color1.remove(color_xiang);
	}
	public void selectnumber(int select_number){
		this.select_number = select_number;
	}
}
