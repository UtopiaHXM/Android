package com.example.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.Tools.ActionSingle;
import com.example.cpdsystem.R;



import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class sceneFragment extends Fragment{

	Button simuOneButton;
	Button simuAllButton;
	Button createFileButton;
	Spinner ledNameSpinner;
	SimpleAdapter ledAdapter; 
	List<HashMap<String, String>> ledList = new ArrayList<HashMap<String,String>>();
	HashMap<String, String> ledHashMap;
	List<int[]>  simuOneList = new ArrayList<int[]>();
	List<List<int[]>> simuAllLists = new ArrayList<List<int[]>>();
	byte[] commandBytes = new byte[10];
	List<byte[]> commandOneList = new ArrayList<byte[]>();
	List<List<byte[]>> commandLists = new ArrayList<List<byte[]>>();
	int index = 0;
	static byte[] initial = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
	View diaLayout;
	View spinnerLayout;
	LayoutInflater diaInflater;
	
	int setOri = 0;  //��һ��spinnerѡ���ֵ
	String currentType = "������";   //Ĭ�Ϸ���״̬
	Boolean itemSelect = false;    //Ĭ��spinner���ɱ�ѡ��
	int currentChoosen = 0;     //spinner�б�ѡ��Ҫ�����ѡ��
	StringBuffer hasChoosen = new StringBuffer();   //���ڴ��spinner��ѡ���choosenֵ
	
	Thread simuThread ;
	ActionSingle actionSingle ;
	
	ExecutorService executor;;
	Bundle b_one;
	Message m_one;
	Boolean isSimuBoolean = false;
	
	
	Handler h_one = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String result = msg.getData().getString("result");
			if(result.equals("END")){
				simuOneButton.setText("����");
				isSimuBoolean = false;
			}
		}
		
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View sceneView = inflater.inflate(R.layout.scene, container,false);
		diaInflater = inflater;
		
		spinnerLayout = inflater.inflate(R.layout.spinner, null);
		ledNameSpinner = (Spinner)sceneView.findViewById(R.id.spinner_name_scene);
		ledNameSpinner.setAdapter(ledAdapter);
		ledNameSpinner.setOnItemSelectedListener(ledSelectedListener);
		return sceneView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		simuOneButton =(Button) getActivity().findViewById(R.id.button_simu);
		simuOneButton.setOnClickListener(simuOneListener);
		simuAllButton = (Button)getActivity().findViewById(R.id.button_gsimu);
		simuAllButton .setOnClickListener(simuAllListener);
		createFileButton = (Button)getActivity().findViewById(R.id.button_creat);
		createFileButton.setOnClickListener(createFileListener);
		for(int i=0;i<6;i++)
		{
			commandOneList.add(initial);
			commandLists.add(commandOneList);
			int [] ini ={0};
			simuOneList.add(ini);
			simuAllLists.add(simuOneList);
		}
		
		ledHashMap = new HashMap<String, String>();
		ledHashMap.put("ledName", "����");
		ledList.add(ledHashMap);
		ledHashMap = new HashMap<String, String>();
		ledHashMap.put("ledName", "δ��");
		ledList.add(ledHashMap);
		ledHashMap = new HashMap<String, String>();
		ledHashMap.put("ledName", "���");
		ledList.add(ledHashMap);
		ledHashMap = new HashMap<String, String>();
		ledHashMap.put("ledName", "�ϼ�");
		ledList.add(ledHashMap);
		ledHashMap = new HashMap<String, String>();
		ledHashMap.put("ledName", "�繷");
		ledList.add(ledHashMap);
		ledHashMap = new HashMap<String, String>();
		ledHashMap.put("ledName", "����");
		ledList.add(ledHashMap);
		
		ledAdapter = new SimpleAdapter(getActivity(), ledList, R.layout.spinner, new String[]{"ledName","ledStatus"}, new int[]{R.id.text_name_spinner,R.id.text_status_spinner});
		
	}
	//������
		private OnClickListener simuOneListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isSimuBoolean){
					isSimuBoolean = true;
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
					
					List<int[]> addSimuList = new ArrayList<int[]>();
					List<byte[]> addCommand = new ArrayList<byte[]>();
					addCommand.addAll(led.ledCommandFileList);
					addSimuList.addAll(led.ledCommandSimuList);
					simuAllLists.set(index,addSimuList);
					commandLists.set(index, addCommand);
					simuOneButton.setText("�жϷ���");
					actionSingle = new ActionSingle(led.ledCommandSimuList,index,true,h_one);
					simuThread = new Thread(actionSingle);
					simuThread.start();
					ledHashMap = new HashMap<String, String>();
					
					ledHashMap.put("ledName", ledList.get(index).get("ledName"));
					ledHashMap.put("ledStatus", "�ѷ���");
					ledList.set(index, ledHashMap);
					//ledList.add(index, ledHashMap);
					ledAdapter.notifyDataSetChanged();
				}
				else {
					actionSingle.setIsRun(false);
				}
			}
		};
		//�����
		private OnClickListener simuAllListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				executor=Executors.newFixedThreadPool(6);
				for(int i =0 ;i<simuAllLists.size();i++)
				{
					if(simuAllLists.get(i).get(0)[0]==0)
					{
						actionSingle = new ActionSingle(simuAllLists.get(i), i, false, h_one);
						executor.execute(actionSingle);
					}
					else {
						actionSingle = new ActionSingle(simuAllLists.get(i), i, true, h_one);
						executor.execute(actionSingle);
					}
				}
			}
		};
		//�����ļ�
		private OnClickListener createFileListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				diaLayout = diaInflater.inflate(R.layout.dialogcreate, null);
				new AlertDialog.Builder(getActivity()).setTitle("�����ļ�����").setView(diaLayout)
				.setPositiveButton("����",positiveClickListener)
				.setNegativeButton("ȡ��", null).show();
			}
		};
		private OnItemSelectedListener ledSelectedListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				index = arg2;
			//	if(((TextView)arg1.findViewById(R.id.text_status_spinner)).getText().toString().equals("�ѷ���")){
			//		led.ledCommandFileList = commandLists.get(arg2);
			//		led.ledCommandSimuList = simuAllLists.get(arg2);
			//	}
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		private DialogInterface.OnClickListener positiveClickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText fileNameEditText = (EditText)diaLayout.findViewById(R.id.edit_fileName_dialogcreate);
				String fileNamewString = fileNameEditText.getText().toString()+".txt";
				String sd_cardpath = new Environment().getExternalStorageDirectory()
						.toString() + "/" + "command"+"/������";
				
				commandBytes=new byte[10];
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
					
					try {
						for(int i=0;i<commandLists.size();i++)
						{
							if(commandLists.get(i).get(0).equals(initial)){
								byte[] start ={(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)i};
								outputStream.write(start);
								byte[] end ={(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
								outputStream.write(end);
							}
							else {
								byte[] start ={(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)i};
								outputStream.write(start);
								for(int j=0;j<commandLists.get(i).size();j++)
								{
									outputStream.write(commandLists.get(i).get(j));
								}
								byte[] end ={(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
								outputStream.write(end);
							}	
						}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Toast.makeText(getActivity(), "�����ļ����ɳɹ�", Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				Log.d("aa","sucess");
			}
		};
		
		
		
		
	
		

}
