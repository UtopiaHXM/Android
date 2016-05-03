package com.example.cpdsystem;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.DB.DBAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class zigbeeAcitvity extends Activity{
	DBAdapter dbAdapter;
	Spinner folderSpinner;
	ListView fileListView;
	ListView ledListView;
	TextView fileSelectTextView;
	TextView fileTextView;
	TextView ledSelectTextView;
	TextView ledTextView;
	Button sendButton;
	Button resetButton;
	Button resetAllButton ;
	
	String folderPath;
	String firstPath,secondPath;
	String ledNameString;
	String tableString;
	File[] folderAimFiler ;
	FileDeal  fd;
	ArrayList<String> folderArrayList = new ArrayList<String>();
	ArrayList<String> txtList = new ArrayList<String>();
	ArrayList<String> ledList = new ArrayList<String>();
	ArrayAdapter<String> fileAdapter;
	ArrayAdapter<String> ledAdapter;
	Boolean isFileBoolean = false;
	Boolean isAddressBoolean = false;
	
	Bundle b_one;
    Message m_one;
	Handler h_one = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String result = msg.getData().getString("result");
			if(result.equals("OK")){
				sendButton.setClickable(true);
				Toast.makeText(zigbeeAcitvity.this, "发送完毕", Toast.LENGTH_SHORT).show();
			}
			else if (result.equals("FALSE")) {
				sendButton.setClickable(true);
				Toast.makeText(zigbeeAcitvity.this, "发送失败", Toast.LENGTH_SHORT).show();
			}
			else if (result.equals("SEND")) {
				Toast.makeText(zigbeeAcitvity.this, "准备发送", Toast.LENGTH_SHORT).show();
			}
			else if (result.equals("SENDING")) {
				Toast.makeText(zigbeeAcitvity.this, "正在发送", Toast.LENGTH_SHORT).show();
			}else if (result.equals("RESET OK")) {
				Toast.makeText(zigbeeAcitvity.this, "重置成功", Toast.LENGTH_SHORT).show();
			}else if (result.equals("RESET FALSE")) {
				Toast.makeText(zigbeeAcitvity.this, "重置失败", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.zigbeesend);
	        dbAdapter = new DBAdapter(this);
	        folderSpinner = (Spinner)findViewById(R.id.spinner_zigbee);
	        fileListView = (ListView)findViewById(R.id.list_zigbeeleft);
	        ledListView = (ListView)findViewById(R.id.list_zigbeeright);
	        fileSelectTextView = (TextView)findViewById(R.id.textview_zigbeeselectleft);
	        fileTextView = (TextView)findViewById(R.id.textview_zigbeeleft);
	        ledSelectTextView = (TextView)findViewById(R.id.textview_zigbeeselectright);
	        ledTextView = (TextView)findViewById(R.id.textview_zigbeeright);
	        sendButton = (Button)findViewById(R.id.button_zigbeesend);
	        
	        fd= new FileDeal();
	        folderSpinner.getScrollY();
			folderPath=new Environment().getExternalStorageDirectory()
					.toString() + "/" + "command";
			File folderFile = new File(folderPath);
			folderAimFiler = folderFile.listFiles();
			for(int i=0;i<folderAimFiler.length;i++)
			{
				folderArrayList.add(folderAimFiler[i].getName());
			}
			SpinnerAdapter folderAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item1,folderArrayList);
			folderSpinner.setAdapter(folderAdapter);
			folderSpinner.setOnItemSelectedListener(folderListener);
			
			fileAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, txtList);
			fileListView.setAdapter(fileAdapter);
			fileListView.setOnItemClickListener(fileListener);
			
			ledAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,ledList);
			ledListView.setAdapter(ledAdapter);
			ledListView.setOnItemClickListener(ledListener);
			
			sendButton.setOnClickListener(sendListener);
			resetButton = (Button)findViewById(R.id.button_reset_zigbee);
			resetButton.setOnClickListener(resetListener);
			resetAllButton = (Button)findViewById(R.id.buttonresetAll_zigbee);
			resetAllButton.setOnClickListener(resetAllListener);
	 }
	 private OnItemSelectedListener folderListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				txtList.clear();
				String folderAddress=((TextView)arg1).getText().toString();
				String txtFilePath = folderPath +"/" +folderAddress;
				Log.d("file","aa"+txtFilePath );
				firstPath = txtFilePath;
				File txtFile = new File(txtFilePath);
				ArrayList<String> thirdList = fd.getFileNameListTxt(txtFile, ".txt");
				for(int i=0;i<thirdList.size();i++){
					txtList.add(thirdList.get(i).toString());
				};
	            //txtList = ceshiArrayList; 
				//Log.d("file","file.."+txtList.get(0).toString());
				fileAdapter.notifyDataSetChanged();	
				ledList.clear();
				if(folderAddress.endsWith("草坪灯"))
				{
					tableString = "lawn";
					thirdList =dbAdapter.quanryName("lawn");
					for(int i=0;i<thirdList.size();i++){
						ledList.add(thirdList.get(i).toString());
					};
					ledAdapter.notifyDataSetChanged();
				}else if (folderAddress.endsWith("场景灯")){
					tableString = "scene" ;
					thirdList =dbAdapter.quanryName("scene");
					for(int i=0;i<thirdList.size();i++){
						ledList.add(thirdList.get(i).toString());
					};
					ledAdapter.notifyDataSetChanged();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		private OnItemClickListener fileListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String txtChoosed=((TextView)arg1).getText().toString();
				fileTextView.setText(txtChoosed);
				fileSelectTextView.setText("已选择");
				isFileBoolean = true;
				secondPath = firstPath + "/"+txtChoosed;
			}
		};
		private OnItemClickListener ledListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ledNameString=((TextView)arg1).getText().toString();
				ledTextView.setText(ledNameString);
				ledSelectTextView.setText("已选择");
				isAddressBoolean = true;
				//secondPath = firstPath + "/"+txtChoosed;
			}
		};
		private OnClickListener sendListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isFileBoolean){
					Toast.makeText(zigbeeAcitvity.this,"请选择文件", Toast.LENGTH_SHORT).show();
				}else if (!isAddressBoolean) {
					Toast.makeText(zigbeeAcitvity.this, "请选择灯", Toast.LENGTH_SHORT).show();
				}else {
					sendButton.setClickable(false);
					 b_one = new Bundle();
			            b_one.putString("result", "SEND");
			            m_one = new Message();
			            m_one.setData(b_one);
			            h_one.sendMessage(m_one);
					sendData sd = new sendData(secondPath,ledNameString,tableString, dbAdapter,h_one);
					new Thread(sd).start();
				}
				
			}
		};
		private OnClickListener resetListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isAddressBoolean){
					Toast.makeText(zigbeeAcitvity.this, "请选择灯", Toast.LENGTH_SHORT).show();
				}
				else {
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String[] infos =dbAdapter.quaryOne(tableString,ledNameString);
							String gateway=infos[0];
							String node = infos[1];
							String value = "        <"+node+" value=\""+"ffff05"+"\"/>\r\n";
							String ID = "      <device id=\"" + gateway + "\"/>\r\n";
						        HttpURLConnection conn = null;

						        try {
						        	Log.d("aaa","00");
						            // Create url to the Device Cloud server for a given web service request
						            URL url = new URL("http://login.etherios.com:80/ws/sci");
						            conn = (HttpURLConnection) url.openConnection(); 
						            //conn.setDoOutput(true);
						            //conn.setDoInput(true);
						            conn.setRequestMethod("POST"); 
						            
						            // Build authentication string
						            String userpassword = "wfy" + ":" + "!QA2ws#ED";

						            // can change this to use a different base64 encoder
						            String encodedAuthorization = Base64.encodeBytes(userpassword.getBytes()).trim();

						            // set request headers
						            conn.setRequestProperty("Authorization", "Basic "
						                    + encodedAuthorization);
						            conn.setRequestProperty("Content-Type", "text/xml");
						            
						            // Send data to server
						            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
						            out.write("<sci_request version=\"1.0\">\r\n");
						            out.write("  <send_message cache=\"false\">\r\n");
						            out.write("    <targets>\r\n");
						            out.write(ID);
						            out.write("    </targets>\r\n");
						            out.write("    <rci_request version=\"1.1\">\r\n");
						            out.write("      <do_command target=\"serial_device\">\r\n");
						            out.write(value);
						            out.write("      </do_command>\r\n");
						            out.write("    </rci_request>\r\n");
						            out.write("  </send_message>\r\n");
						            out.write("</sci_request>\r\n");
						            out.close();
						            // Get input stream from response and convert to String
						            InputStream is = conn.getInputStream();

						            Scanner isScanner = new Scanner(is);
						            StringBuffer buf = new StringBuffer();
						            while (isScanner.hasNextLine()) {
						                buf.append(isScanner.nextLine() + "\n");
						            }
						            String responseContent = buf.toString();

						            // add line returns between tags to make it a bit more readable
						            responseContent = responseContent.replaceAll("><", ">\n<");

						            // Output response to standard out
						            System.out.println(responseContent);
						            Log.d("aaa","11");
						            b_one = new Bundle();
						            b_one.putString("result", "RESET OK");
						            m_one = new Message();
						            m_one.setData(b_one);
						            h_one.sendMessage(m_one);
						        } catch (Exception e) {
						            // Print any exceptions that occur
						            e.printStackTrace();
						            b_one = new Bundle();
						            b_one.putString("result", "RESET FALSE");
						            m_one = new Message();
						            m_one.setData(b_one);
						            h_one.sendMessage(m_one);
						        }
						}
					}).start();
				}
				
				
			}
		};
		private OnClickListener resetAllListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpURLConnection conn = null;

				        try {
				            // Create url to the Device Cloud server for a given web service request
				            URL url = new URL("http://login.etherios.com:80/ws/sci");
				            conn = (HttpURLConnection) url.openConnection(); 
				           // conn.setDoOutput(true);
				           // conn.setDoInput(true);
				            conn.setRequestMethod("POST"); 
				            
				            // Build authentication string
				            String userpassword = "wfy" + ":" + "!QA2ws#ED";

				            // can change this to use a different base64 encoder
				            String encodedAuthorization = Base64.encodeBytes(userpassword.getBytes()).trim();

				            // set request headers
				            conn.setRequestProperty("Authorization", "Basic "
				                    + encodedAuthorization);
				            conn.setRequestProperty("Content-Type", "text/xml");
				            
				            // Send data to server
				            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				            out.write("<sci_request version=\"1.0\">\r\n");
				            out.write("  <send_message cache=\"false\">\r\n");
				            out.write("    <targets>\r\n");
				            out.write("      <device id=\"00000000-00000000-00409DFF-FF55A0F1\"/>\r\n");
				            out.write("    </targets>\r\n");
				            out.write("    <rci_request version=\"1.1\">\r\n");
				            out.write("      <do_command target=\"serial_device\">\r\n");
				            out.write("        <broadcast value=\"ffff05\"/>\r\n");
				            out.write("      </do_command>\r\n");
				            out.write("    </rci_request>\r\n");
				            out.write("  </send_message>\r\n");
				            out.write("</sci_request>\r\n");
				            out.close();
				            // Get input stream from response and convert to String
				            InputStream is = conn.getInputStream();

				            Scanner isScanner = new Scanner(is);
				            StringBuffer buf = new StringBuffer();
				            while (isScanner.hasNextLine()) {
				                buf.append(isScanner.nextLine() + "\n");
				            }
				            String responseContent = buf.toString();

				            // add line returns between tags to make it a bit more readable
				            responseContent = responseContent.replaceAll("><", ">\n<");

				            // Output response to standard out
				            System.out.println(responseContent);
				            b_one = new Bundle();
				            b_one.putString("result", "RESET OK");
				            m_one = new Message();
				            m_one.setData(b_one);
				            h_one.sendMessage(m_one);
				        } catch (Exception e) {
				            // Print any exceptions that occur
				            e.printStackTrace();
				            e.printStackTrace();
				            b_one = new Bundle();
				            b_one.putString("result", "RESET FALSE");
				            m_one = new Message();
				            m_one.setData(b_one);
				            h_one.sendMessage(m_one);
				        } finally {
				            if (conn != null)
				                conn.disconnect();
				        }
					}
				}).start();
			}
		};
}
