package com.example.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.example.DB.DBAdapter;
import com.example.cpdsystem.R;




import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class lawnFragment extends Fragment{
	

	Button aaButton;
	Button simuOneButton;
	Button simuAllButton;
	Button createFileButton;
	View diaLayout;
	LayoutInflater diaInflater;
	byte[] commandBytes = new byte[10];
	
	Button cPDaddButton,cPDdelButton,cPDbeforeButton,cPDlastButton;
	EditText CPD_name,CPD_nodeURL,CPD_gatewayURL;
	TextView CPD_TVname;
	static ArrayList<CPD_Data>  CP_Grp;
	//static int led.index_CPD = 0;
	int id=0;
	//static boolean white_R = true;
	static int screenWidth;
	static int screenHeight;
	LinearLayout l_one;
	List<int[]> init_col;
	boolean col_switch = true;
	DBAdapter  dbAdapter;
	List<String> ledNameList = new ArrayList<String>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View lawnView  = inflater.inflate(R.layout.lawn, container,false);
		diaInflater = inflater;
		CPD_TVname = (TextView)lawnView.findViewById(R.id.textView_CPD);
		CPD_TVname.setText("子鼠");
		cPDaddButton = (Button)lawnView.findViewById(R.id.button_CPDAdd);
		cPDaddButton.setOnClickListener(addCPDButtonListener);
		cPDdelButton = (Button)lawnView.findViewById(R.id.button_CPDDel);
		cPDdelButton.setOnClickListener(delCPDButtonListener);
        cPDbeforeButton = (Button)lawnView.findViewById(R.id.button_Bef);
        cPDbeforeButton.setOnClickListener(beforeCPDButtonListener);
        cPDlastButton = (Button)lawnView.findViewById(R.id.button_Next);
        cPDlastButton.setOnClickListener(lastCPDButtonListener);
		Log.d("aa","OnCreateView" );
		return lawnView ;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("aa",".."+getActivity());
		led.isRunBoolean = true;
		dbAdapter = new DBAdapter(getActivity());
		simuOneButton =(Button) getActivity().findViewById(R.id.button_simu);
		simuAllButton = (Button)getActivity().findViewById(R.id.button_allsimu);
		createFileButton = (Button)getActivity().findViewById(R.id.button_creat);
		createFileButton.setOnClickListener(createFileListener);
		
		screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
		screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：800p）
		CP_Grp = new ArrayList<CPD_Data>();
		Log.d("aa",".s."+simuOneButton.getText().toString());
		init_col = new ArrayList<int[]>();		
		int[] init_col_one = {1,255,255,255,0,0,0,10,0};
		init_col.add(init_col_one);
		ledNameList = dbAdapter.quanryName("lawn");		
		for(int i =0;i<ledNameList.size();i++)			
		{
			
			float x = screenWidth * 11 / 40;
			float y = screenHeight / 2 - 80;
			float r = 80.0f;
			int state = 0;//0,未仿真、1，已仿真、2，已生成 
			                //0 控件在布局中 , 1 控件在OnTouch_move中 ,2控件
							// 在SurfaceView中固定
			CPD_Data cpd_da = new CPD_Data(x,y,r,state,ledNameList.get(i),"URL1","URL2",i);
			CP_Grp.add(cpd_da);			
		}
		MySurfaceView.setCP_Grp(CP_Grp);		
		Log.d("fff","f:"+CP_Grp.size()+",M:"+MySurfaceView.CP_Grp.size());
		simuOneButton.setOnClickListener(simuOneListener);
	}
	//单仿真
	private OnClickListener simuOneListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (CP_Grp!=null) 
			{
				Log.d("aaa", "kaishifangzhen");
				if (col_switch) {		
					MySurfaceView.white_R = false;							
					CP_Grp.get(led.index_CPD).init();
					CP_Grp.get(led.index_CPD).set_FileG(led.ledCommandFileList);
					CP_Grp.get(led.index_CPD).set_ColorG(led.ledCommandSimuList);
					MySurfaceView.setCP_Grp(CP_Grp);
					col_switch = !col_switch;
					simuOneButton.setText("终止仿真");
					Log.d("eee", "true,index:"+led.index_CPD);				
				} 
				else {
					    MySurfaceView.white_R = true;
						col_switch = !col_switch;
						Log.d("aaa", "false");
						simuOneButton.setText("单仿真");				
				}
			}
		}
	};
	//组仿真
	private OnClickListener simuAllListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	//生成文件
	private OnClickListener createFileListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			diaLayout = diaInflater.inflate(R.layout.dialogcreate, null);
			//LayoutInflater inflater = getLayoutInflater();
			//View diaLayout = inflater.inflate(R.layout.dialog, null);
			new AlertDialog.Builder(getActivity()).setTitle("输入文件名称").setView(diaLayout)
			.setPositiveButton("创建",positiveClickListener)
			.setNegativeButton("取消", null).show();
			
		
		}
	};
	private DialogInterface.OnClickListener positiveClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			EditText fileNameEditText = (EditText)diaLayout.findViewById(R.id.edit_fileName_dialogcreate);
			String fileNamewString = fileNameEditText.getText().toString()+".txt";
			String sd_cardpath = new Environment().getExternalStorageDirectory()
					.toString() + "/" + "command"+"/草坪灯";
			for(int i=0;i<10;i++)
			{
				if(i<6)
				{
					commandBytes[i]= (byte) 0x00;
				}
				else {
					commandBytes[i]= (byte) 0xff;
				}
				
			}
			
			
			File commandfFile=new File(sd_cardpath,fileNamewString);
			if (!commandfFile.isFile()) {
				try {
					commandfFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Log.d("aa",".."+commandfFile.getName() );
			try {
				FileOutputStream outputStream = new FileOutputStream(commandfFile);
				for(int i=0;i<led.ledCommandFileList.size();i++)
				{
					try {
						outputStream.write(led.ledCommandFileList.get(i));
						byte[] aa = led.ledCommandFileList.get(i);
						String aaaString = new String(aa);
						Log.d("test",i+":"+aaaString );
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					outputStream.write(commandBytes);
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				commandBytes=new byte[10];
				Toast.makeText(getActivity(), "配置文件生成成功", Toast.LENGTH_SHORT).show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Log.d("aa","sucess");
		}
	};
	private OnClickListener addCPDButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			class_add();
		}
		
	};
	
	private void class_add() {
		Log.d("aaa", "surfaceCreated开始");
		Log.d("aaa", "GRP_size=" + CP_Grp.size());
		l_one =  (LinearLayout) diaInflater.inflate(R.layout.dialogadd, null);
		CPD_name = (EditText)l_one.findViewById(R.id.editText_CPDName);
		CPD_nodeURL = (EditText)l_one.findViewById(R.id.editText_NodeURL);
		CPD_gatewayURL = (EditText)l_one.findViewById(R.id.editText_Gateway_URL);
		new AlertDialog.Builder(getActivity())
				.setIcon(R.drawable.ic_launcher)
				.setTitle("创建")
				.setView(l_one)
				.setPositiveButton("添加", new DialogInterface.OnClickListener() {		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						id = CP_Grp.size();					
						led.index_CPD = CP_Grp.size();	
						float x = screenWidth * 11 / 40;
						float y = screenHeight / 2 - 80;
						float r = 80.0f;
						int state = 0;//0,未仿真、1，已仿真、2，已生成 
						                //0 控件在布局中 , 1 控件在OnTouch_move中 ,2控件
										// 在SurfaceView中固定
						String Name = CPD_name.getText().toString().trim();
						Log.d("ccc","name:"+Name);
						String Node_URL = ""+CPD_nodeURL.getText().toString().trim();
						String Gateway_URL =""+CPD_gatewayURL.getText().toString().trim();
						CPD_Data my_cpddata = new CPD_Data(x, y, r, state,
								Name, Node_URL, Gateway_URL, id);
						CP_Grp.add(my_cpddata);
						CP_Grp.get(id).set_ColorG(init_col);
						CPD_TVname.setText(""+CP_Grp.get(id).name);
						MySurfaceView.setCP_Grp(CP_Grp);
						
						dialog.dismiss();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create().show();
	}
	private OnClickListener delCPDButtonListener = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 删除CP_Grp及数据库中的某一组数据
			// if CP_Grp != null, CP_Grp.remove(index);
			if( CP_Grp != null&&led.index_CPD>(CP_Grp.size()-1))
			{
				CP_Grp.remove(led.index_CPD);
				if(led.index_CPD>0)
				{
					led.index_CPD = led.index_CPD-1;
				}
			}
			Log.d("ddd", "size:"+CP_Grp.size()+",index:"+led.index_CPD);
		}
		
	};

	private OnClickListener beforeCPDButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 查询数据库当前草坪灯对象的前一个对象可用属性的值
			// 如果为true则调用方法画双同心圆
			if (CP_Grp.size() > 1 && led.index_CPD > 0) {
				led.index_CPD = led.index_CPD - 1;
				CPD_TVname.setText(CP_Grp.get(led.index_CPD).name);
				/*if (CP_Grp.get(led.index_CPD).get_ColorG() != null
						&& CP_Grp.get(led.index_CPD).get_FileG() != null) {
					led.ledCommandSimuList.clear();
					led.ledCommandFileList.clear();
				}*/
			}
			Log.d("ddd", "size:" + CP_Grp.size() + ",index:" + led.index_CPD);

		}
	};
	private OnClickListener lastCPDButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 查询数据库当前草坪灯对象的后一个对象可用属性的值
			// 如果为true则调用方法画双同心圆
			if(led.index_CPD<CP_Grp.size()-1)
			{
				led.index_CPD = led.index_CPD+1;
				CPD_TVname.setText(CP_Grp.get(led.index_CPD).name);
				/*if (CP_Grp.get(led.index_CPD).get_ColorG() != null
						&& CP_Grp.get(led.index_CPD).get_FileG() != null) {
					led.ledCommandSimuList.clear();
					led.ledCommandSimuList.addAll(CP_Grp.get(led.index_CPD)
							.get_ColorG()); //lawnFragment.CP_Grp.get(led.index_CPD).get_ColorG()
					led.ledCommandFileList.clear();
					led.ledCommandFileList.addAll(CP_Grp.get(led.index_CPD)
							.get_FileG());
					AndTextFragmentMainActivity.init_rect(CP_Grp.get(led.index_CPD)
							.get_ColorG());
				}*/
			}
			Log.d("ddd", "size:"+CP_Grp.size()+",index:"+led.index_CPD);
		}	
	};
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		led.isRunBoolean =false ;
	}

}
