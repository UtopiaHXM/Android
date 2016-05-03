package com.example.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.cpdsystem.R;




import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressLint("NewApi")
public class AndTextFragmentMainActivity extends Activity implements OnNavigationListener  {
	Button firstColorButton;
	Button secondColorButton;
	Button addButton;
	Button createFileButton;
	Button deleteButton;
	Button sendButton;
	Button turnleft_button,turnright_button;
	Button clearButton;
	SeekBar redBar;
	SeekBar greenBar;
	SeekBar blueBar;
	RadioButton staticStatusRadioButton;
	RadioButton flashingStatusRadioButton;
	RadioButton gradientStatusRadioButton;
	RadioButton firstColorRadioButton;
	RadioButton secondColorRadioButton;
	RadioGroup statusRadioGroup;
	RadioGroup colorRadioGroup;
	EditText timEditText;
	EditText numberEditText;
	TextView numberTextView;
	TextView timeHintTextView;
	TextView numberHintTextView;
	MyView view;
	TextView select_textview;//选择显示第几项的textview
	
	int redProgressFist,redProgressSecond;
	int greenProgressFist,greenProgressSecond;
	int blueProgressFist,blueProgressSecond;
	Boolean forgroundColorBoolean;
	Boolean isRight = true;
	byte[] commandBytes = new byte[10];
	int status=2;
	int color=2;
	int red = 1,green=2,blue=3;
    ArrayList<Float> length_list = new ArrayList<Float>(); ////////
	ArrayList<Float> length_list1 = new ArrayList<Float>();
	float lengthAll = 0;
	Float length_begin,length_end;
	int select_xiang=0;//选择的第几项/////////
	

	ArrayList<byte[]> commamdList = new ArrayList<byte[]>();
	ArrayList<byte[]> sendList = new ArrayList<byte[]>();
	int[] simuInt = new int[9];
	byte[] aaaBytes={12,23,23,21};
	static List<int[]> rect_list ;

	Fragment fragment;
	FragmentTransaction ft;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_and_text_main);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(AndTextFragmentMainActivity.this,
				android.R.layout.simple_list_item_1,
				new String[]{"草坪灯","场景灯"}), 
				this);
		
		
		timeHintTextView = (TextView)findViewById(R.id.text_timeHint);
		numberHintTextView = (TextView)findViewById(R.id.text_numberHint);
		numberTextView = (TextView)findViewById(R.id.textView_number);
		select_textview=(TextView)findViewById(R.id.textview_dijixiang);
		timEditText = (EditText)findViewById(R.id.editText_time);
		numberEditText = (EditText)findViewById(R.id.editText_number);
		statusRadioGroup = (RadioGroup)findViewById(R.id.radioGroup_state);
		statusRadioGroup.setOnCheckedChangeListener(statusListener);
		colorRadioGroup = (RadioGroup)findViewById(R.id.radioGroup_color);
		colorRadioGroup.setOnCheckedChangeListener(colorRadioGroupChangeListener);
		//x
		view = (MyView)findViewById(R.id.myView1);
		//x
		
		addButton = (Button)findViewById(R.id.button_add);
		addButton.setOnClickListener(addListener);
		createFileButton = (Button)findViewById(R.id.button_creat);
		//createFileButton.setOnClickListener(createFileListener);
		deleteButton=(Button)findViewById(R.id.button_delete);
		deleteButton.setOnClickListener(deleteListener);
		clearButton=(Button)findViewById(R.id.button_removeall);
		clearButton.setOnClickListener(clearlistener);
		turnleft_button=(Button)findViewById(R.id.button_zuozhi);
		turnleft_button.setOnClickListener(turnleftlistener);
		turnright_button=(Button)findViewById(R.id.button_youzhi);
		turnright_button.setOnClickListener(turnrightlistener);
		
		firstColorRadioButton = (RadioButton)findViewById(R.id.radioButton_first);
		firstColorButton = (Button)findViewById(R.id.button_colorf);
		secondColorRadioButton = (RadioButton)findViewById(R.id.radioButton_second);
		secondColorButton = (Button)findViewById(R.id.button_colors);
		
		redBar = (SeekBar)findViewById(R.id.seekBar_red);
		redBar.setOnSeekBarChangeListener(colorBarChangeListener);
		greenBar = (SeekBar)findViewById(R.id.seekBar_green);
		greenBar.setOnSeekBarChangeListener(colorBarChangeListener);
		blueBar = (SeekBar)findViewById(R.id.seekBar_blue);
		blueBar.setOnSeekBarChangeListener(colorBarChangeListener);
		
		((Button)findViewById(R.id.button_allsimu)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for(int i=0;i<led.ledCommandSimuList.size();i++)
				{
					Log.d("ccc",i+".."+led.ledCommandSimuList.get(i)[0]+
							".."+led.ledCommandSimuList.get(i)[1]+
							".."+led.ledCommandSimuList.get(i)[2]+
							".."+led.ledCommandSimuList.get(i)[3]+
							".."+led.ledCommandSimuList.get(i)[4]+
							".."+led.ledCommandSimuList.get(i)[5]+
							".."+led.ledCommandSimuList.get(i)[6]+
							".."+led.ledCommandSimuList.get(i)[7]+
							".."+led.ledCommandSimuList.get(i)[8]);
				}
			}
		});
		

		
		//数据初始化
		redProgressFist=redProgressSecond=255;
		greenProgressFist=greenProgressSecond=255;
		blueProgressFist=blueProgressSecond=255;
		statusRadioGroup.check(R.id.radio_flashing);
		status = 2;
		colorRadioGroup.check(R.id.radioButton_first);
		forgroundColorBoolean = true;
		firstColorButton.setBackgroundColor(Color.rgb(redProgressFist, greenProgressFist, blueProgressFist));
		secondColorButton.setBackgroundColor(Color.rgb(redProgressSecond, greenProgressSecond, blueProgressSecond));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		
		switch (itemPosition) {
		case 0:
			fragment = new lawnFragment();
			ft = getFragmentManager().beginTransaction();		
			ft.replace(R.id.fragment, fragment);		
			ft.commit();		
			break;

		
			
		case 1:
			fragment = new sceneFragment();
			ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.fragment, fragment);
			ft.commit();
			break;
			
		default:
			break;
		}
		 
		return false;
	}

	
private OnSeekBarChangeListener colorBarChangeListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			switch (seekBar.getId()) {
			case R.id.seekBar_red:
				
				if(forgroundColorBoolean){
					redProgressFist = seekBar.getProgress();
					firstColorButton.setBackgroundColor(Color.rgb(redProgressFist, greenProgressFist, blueProgressFist));
					
				}
				else {
					redProgressSecond = seekBar.getProgress();
					secondColorButton.setBackgroundColor(Color.rgb(redProgressSecond, greenProgressSecond, blueProgressSecond));
					
				}
				break;

			case R.id.seekBar_green:
				
				if(forgroundColorBoolean){
					greenProgressFist = seekBar.getProgress();
					firstColorButton.setBackgroundColor(Color.rgb(redProgressFist, greenProgressFist, blueProgressFist));
					
				}
				else {
					greenProgressSecond = seekBar.getProgress();
					secondColorButton.setBackgroundColor(Color.rgb(redProgressSecond, greenProgressSecond, blueProgressSecond));
					
				}
				break;
				
			case R.id.seekBar_blue:
				
				if(forgroundColorBoolean){
					blueProgressFist = seekBar.getProgress();
					firstColorButton.setBackgroundColor(Color.rgb(redProgressFist, greenProgressFist, blueProgressFist));
					
				}
				else {
					blueProgressSecond = seekBar.getProgress();
					secondColorButton.setBackgroundColor(Color.rgb(redProgressSecond, greenProgressSecond, blueProgressSecond));
					
				}
				break;
				
			default:
				break;
			}
			
			
		}
	};
	
	private RadioGroup.OnCheckedChangeListener colorRadioGroupChangeListener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radioButton_first:
				forgroundColorBoolean=true;
				firstColorButton.setBackgroundColor(Color.rgb(redProgressFist, greenProgressFist, blueProgressFist));
				redBar.setProgress(redProgressFist);
				blueBar.setProgress(blueProgressFist);
				greenBar.setProgress(greenProgressFist);
				break;

			case R.id.radioButton_second:
				forgroundColorBoolean=false;
				secondColorButton.setBackgroundColor(Color.rgb(redProgressSecond, greenProgressSecond, blueProgressSecond));
				redBar.setProgress(redProgressSecond);
				blueBar.setProgress(blueProgressSecond);
				greenBar.setProgress(greenProgressSecond);
				break;
				
			default:
				break;
			}
			
		}
		
		
	};
	private RadioGroup.OnCheckedChangeListener statusListener =new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			//静态
			case R.id.radio_static:
				color = red;
				//x
				status = 1;
				simuInt[0]=1;
				numberEditText.setVisibility(0x00000004);
				numberTextView.setVisibility(0x00000004);
				numberHintTextView.setVisibility(0x00000004);;
				secondColorButton.setVisibility(0x00000004);
				secondColorRadioButton.setVisibility(0x00000004);
				break;
			//闪烁	
			case R.id.radio_flashing:
				
				color=green;
				//x
				status = 2;
				simuInt[0]=2;
				numberEditText.setVisibility(0x00000000);
				numberTextView.setVisibility(0x00000000);
				numberHintTextView.setVisibility(0x00000000);
				secondColorButton.setVisibility(0x00000000);
				secondColorRadioButton.setVisibility(0x00000000);
				break;
			//渐变	
			case R.id.radio_gradient:
				color=blue;
				//x
				status = 3;
				simuInt[0]=3;
				numberEditText.setVisibility(0x00000004);
				numberTextView.setVisibility(0x00000004);
				numberHintTextView.setVisibility(0x00000004);
				secondColorButton.setVisibility(0x00000000);
				secondColorRadioButton.setVisibility(0x00000000);
				break;
				
			default:
				break;
			}
			//恢复选择前景色
			colorRadioGroup.check(R.id.radioButton_first);
		}
	};
	
	
	private OnClickListener addListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int l;
			int time=0;
			int number = 0;
			float time_float=0;
			float number_float=0;
			simuInt = new int[9];
			
			simuInt[0]= status;
			Log.d("ccc","status:"+status);
			simuInt[1]= redProgressFist;
			simuInt[2]= greenProgressFist;
			simuInt[3]= blueProgressFist;
			simuInt[4]= redProgressSecond;
			simuInt[5]= greenProgressSecond;
			simuInt[6]= blueProgressSecond;
			simuInt[7]=0;
			simuInt[8]=0;
			commandBytes=new byte[10];
			commandBytes[0] = (byte)redProgressFist ;
			commandBytes[1] = (byte)greenProgressFist ;
			commandBytes[2] = (byte)blueProgressFist ;
			commandBytes[3] = (byte)redProgressSecond ;
			commandBytes[4] = (byte)greenProgressSecond ;
			commandBytes[5] = (byte)blueProgressSecond ;
			
			
			String timeString = timEditText.getText().toString();
			
			if(timeString.equals(""))
			{
				
			}
			else {
				 time = Integer.parseInt(timeString);
				 simuInt[7] = time;
				 time_float = Float.parseFloat(timeString);
			}
			
			byte[] timebyte =intToByteArray1(time); 
			switch(status){
			case 1:
				//静态
				testLimit(time, 1, 8640000,1);
				for(int i=0;i<3;i++)
				{
					commandBytes[i+3]=commandBytes[i];
				}
				
				l=timebyte.length;
				for(int i=3;i>=0;i--)
				{
					if(i<l)
					{
						commandBytes[9-i]=timebyte[l-1-i];
					}
					else {
						commandBytes[9-i]=(byte)0x00;
					}
				}
				commandBytes[6]=(byte) (commandBytes[6]&(byte)0x7f);
				break;
			case 2:
				//闪烁
				testLimit(time, 1, 255,1);
				String numberString = numberEditText.getText().toString();
				if(numberString.equals(""))
				{
					
				}
				else {
					number = Integer.parseInt(numberString);
					simuInt[8] = number;
					number_float=Float.parseFloat(numberString);
					time_float=number_float*time_float;
				}
				
				byte []numberbyte=intToByteArray1(number);
				Log.d("num","number:"+1);
				testLimit(number, 1, 8000000,2);
				Log.d("num","number:"+2);
				 l=numberbyte.length;
				for(int i=2;i>=0;i--)
				{
					if(i<l)
					{
						commandBytes[8-i]=numberbyte[l-1-i];
					}
					else {
						commandBytes[8-i]=(byte)0x00;
					}
				}
				Log.d("aa","time..:"+time);
				commandBytes[9]=(byte) (timebyte[3]&0xff);
				commandBytes[6]=(byte) (commandBytes[6]|(byte)0x80);
				break;
			case 3:
				//渐变
				testLimit(time, 0,100000,1);
				l=timebyte.length;
				for(int i=3;i>=0;i--)
				{
					if(i<l)
					{
						commandBytes[9-i]=timebyte[l-1-i];
					}
					else {
						commandBytes[9-i]=(byte)0x00;
					}
				}
				commandBytes[6]=(byte) (commandBytes[6]&(byte)0x7f);
				break;
				
			default:
				break;
			}
			if(isRight){
				int []addsimu = new int[9];
				System.arraycopy(simuInt, 0, addsimu, 0, simuInt.length);
				byte []addcommand = new byte[10];
				System.arraycopy(commandBytes, 0, addcommand, 0, commandBytes.length);
								
				led.ledCommandFileList.add(select_xiang,addcommand);
				led.ledCommandSimuList.add(select_xiang,addsimu);
				
				//x
				length_list.add(select_xiang,time_float); 
				view.setColor(select_xiang,color);
				select_xiang++;
				select_textview.setText("第"+select_xiang+"项");
				
				findAllnumber();
				view.selectnumber(select_xiang-1);
				view.setLong(length_list1);
				view.invalidate();
				//x  
				Log.d("selectxiang","+.."+select_xiang+"");	
				//恢复选择前景色
				colorRadioGroup.check(R.id.radioButton_first);
				 
				Toast.makeText(AndTextFragmentMainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
			}
			
			
		}
	};
	private OnClickListener deleteListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(select_xiang>0){
				
				//删除所选中的指令
				led.ledCommandSimuList.remove(select_xiang-1);
				led.ledCommandFileList.remove(select_xiang-1);
				//
				length_list.remove(select_xiang-1);
				view.deleteColor(select_xiang-1);
				findAllnumber();
				view.setLong(length_list1);
				view.invalidate();
				select_xiang--;
				if(length_list.size()<select_xiang){
					
					select_textview.setText("第"+select_xiang+"项");
					view.selectnumber(select_xiang-1);
				}
				Log.d("selectxiang","-.."+select_xiang+"");	
			}	
		}
	};
	private OnClickListener clearlistener = new OnClickListener() {
		
		public void onClick(View v) {
			select_xiang = 0;
			length_list.clear();
			led.ledCommandFileList.clear();
			led.ledCommandSimuList.clear();
			findAllnumber();
			view.setLong(length_list1);
			view.invalidate();
			select_textview.setText("第"+0+"项");
		}
	}; {
		
	}
   private OnClickListener turnleftlistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(select_xiang>1)
			{
				select_xiang--;
				view.selectnumber(select_xiang-1);
				findAllnumber();
				view.setLong(length_list1);
				view.invalidate();
				select_textview.setText("第"+select_xiang+"项");
			}
			Log.d("selectxiang","<.."+select_xiang+"");	
		}
	};
	private OnClickListener turnrightlistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(select_xiang<length_list.size()){
				select_xiang++;
				view.selectnumber(select_xiang-1);
				findAllnumber();
				view.setLong(length_list1);
				view.invalidate();
				select_textview.setText("第"+select_xiang+"项");
			}
			Log.d("selectxiang",">.."+select_xiang+"");	
			
		}
	};
	
	

	private void testLimit(int real,int min,int max,int tag){
		if(real<=max&&real>=min)
		{
			isRight = true;
		}
		else {
			Log.d("toast","1");
			isRight = false;
			
			if(tag==1)
			{
				Toast.makeText(AndTextFragmentMainActivity.this, "时间输入值范围"+min+"~"+max+"的整数！", Toast.LENGTH_SHORT).show();
				timEditText.setText("");
			}
			else{
				Toast.makeText(AndTextFragmentMainActivity.this, "次数输入值范围"+min+"~"+max+"的整数！", Toast.LENGTH_SHORT).show();
				numberEditText.setText("");
			}
			Log.d("toast","2");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {//返回键结束应用程序
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			System.exit(0);

			return true;

		} else {

			return super.onKeyDown(keyCode, event);

		}

	}
	static  void init_rect(List<int[]> initr)
	{
		rect_list = initr ;
		for(int i =0 ; i<rect_list.size();i++)
		{
		
		}
	}
	public static byte[] intToByteArray1(int i) {   
		  byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
		}
	public void findAllnumber(){
        lengthAll=0;
        length_list1.clear();
		for(int i=0;i<length_list.size();i++){
			lengthAll = lengthAll+length_list.get(i);
		}
		if(lengthAll!=0){
		for(int i=0;i<length_list.size();i++){
			length_list1.add((length_list.get(i)/lengthAll)*550);
		}
	}		
}
}
