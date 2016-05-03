package com.example.cpdsystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class GraMainActivity extends Activity {

	GridView gridview;
	ArrayList<HashMap<String, Object>> lstImageItem;
	HashMap<String, Object> map_0;
	HashMap<String, Object> map;
	SimpleAdapter saImageItems;
	static int index = 0;
	// boolean[] Ld_state = {false,false,false,false,false,false};

	Handler h_oneHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
			case 0:flush(0);break;
			case 1:flush(1);break;
			case 2:flush(2);break;
			case 3:flush(3);break;
			case 4:flush(4);break;
			case 5:flush(5);break;
		}
		}
	};
    
	public void flush(int i)
	{	
			map = new HashMap<String, Object>();
			if (sta.str_stateLd[i].equals("close")) {
				map.put("ItemImage", R.drawable.close);// ���ͼ����Դ��ID
				map.put("ItemText", "NO." + String.valueOf(i+1) + "�ѹر�");// �������ItemText
			    lstImageItem.set(i+1, map);
			} 
			else if (sta.str_stateLd[i].equals("open")) {
				map.put("ItemImage", R.drawable.open);// ���ͼ����Դ��ID
				map.put("ItemText", "NO." + String.valueOf(i+1) + "�ѿ���");// �������ItemText
				lstImageItem.set(i+1, map);
			}		
		saImageItems.notifyDataSetChanged();
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gra_main);
		gridview = (GridView) findViewById(R.id.gridView_ld);
		getLdState();
		// ���ɶ�̬���飬����ת������
		lstImageItem = new ArrayList<HashMap<String, Object>>();
		map_0 = new HashMap<String, Object>();
		map_0.put("ItemImage", R.drawable.add);// ���ͼ����Դ��ID
		map_0.put("ItemText", "�����·��");// �������ItemText
		lstImageItem.add(map_0);
		for (int i = 1; i < sta.str_UrlLd.length+1; i++) {
		    map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.close);// ���ͼ����Դ��ID
			map.put("ItemText", "NO." + String.valueOf(i));// �������ItemText
			lstImageItem.add(map);
		}
		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		saImageItems = new SimpleAdapter(this, // ûʲô����
				lstImageItem,// ������Դ
				R.layout.gridviewtd,// night_item��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemText" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.imageView_Ldimage, R.id.textView_Ldname });
		// ��Ӳ�����ʾ
		gridview.setAdapter(saImageItems);
		// �����Ϣ����
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2 == 0)
				{Log.d("ok","ok");}
				else{
				if (sta.str_stateLd[arg2-1].equals("open")) {
					sendLdState(arg2-1, "ffff10");//��
					Log.d("eee",arg2-1+"�ŵƣ�״̬���������ڹر�");
					
				} else {
					sendLdState(arg2-1, "ffff11");//��
					Log.d("eee",arg2-1+"�ŵƣ�״̬�رգ����ڿ���");
				}
				Log.d("eee","arg:"+arg2+","+sta.str_stateLd[0]+","+sta.str_stateLd[1]+","+sta.str_stateLd[2]+","+sta.str_stateLd[3]+","+sta.str_stateLd[4]+","+sta.str_stateLd[5]+".");
				}
			}
		});
	}

	public void sendLdState(int i, String str) {
		final int index = i;
		final String value = str;
		new Thread(new Runnable() {

			public void run() {

				HttpURLConnection conn = null;
				try {
					Log.d("aaa", "00");
					// Create url to the Device Cloud server for a given web
					// service request
					URL url = new URL("http://login.etherios.com:80/ws/sci");
					conn = (HttpURLConnection) url.openConnection();
					// conn.setDoOutput(true);
					// conn.setDoInput(true);
					conn.setRequestMethod("POST");
					// can change this to use a different base64 encoder
					String encodedAuthorization = Base64.encodeBytes(
							sta.userpassword.getBytes()).trim();

					// set request headers
					conn.setRequestProperty("Authorization", "Basic "
							+ encodedAuthorization);
					conn.setRequestProperty("Content-Type", "text/xml");

					// Send data to server
					OutputStreamWriter out = new OutputStreamWriter(conn
							.getOutputStream());
				
					out.write("<sci_request version=\"1.0\">\r\n");
					out.write("  <send_message cache=\"false\">\r\n");
					out.write("    <targets>\r\n");
					out.write("    <device id=\"00000000-00000000-00409DFF-FF55A0F1\"/>\r\n");
					out.write("    </targets>\r\n");
					out.write("    <rci_request version=\"1.1\">\r\n");
					out.write("      <do_command target=\"serial_device\">\r\n");
					out.write("      <"+sta.send_UrlLd[index]+" value=\""+value+"\"/>\r\n");
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

					// add line returns between tags to make it a bit more
					// readable
					responseContent = responseContent.replaceAll("><", ">\n<");

					// Output response to standard out
					System.out.println(responseContent);
					Log.d("aaa", "11");
				} catch (Exception e) {
					// Print any exceptions that occur
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void getLdState() {
		for(int i = 0 ;i<sta.str_stateLd.length;i++)
		{
			new Thread(new Runnable(){

				@Override
				public void run() {
					
					// TODO Auto-generated method stub
					int a = index;
					index = index+1;
					Log.d("fff",a+"�ŵ��߳̿���");
					while (true) {
						try {
								// Create url to the iDigi server for a given web
								// service
								// request
								URL url = new URL(
										"http://login.etherios.com:80/ws/DataStream/dia/"
												+ sta.str_UrlLd[a]);
								HttpURLConnection conn = (HttpURLConnection) url
										.openConnection();
								conn.setRequestMethod("GET");
								// can change this to use a different base64 encoder
								String encodedAuthorization = Base64.encodeBytes(
										sta.userpassword.getBytes()).trim();
								conn.setRequestProperty("Authorization", "Basic "
										+ encodedAuthorization);
								InputStream is = conn.getInputStream();
								Scanner isScanner = new Scanner(is);
								StringBuffer buf = new StringBuffer();
								while (isScanner.hasNextLine()) {
									buf.append(isScanner.nextLine() + "\n");
								}
								String responseContent = buf.toString();
								// Output response to standard out
								System.out.println(responseContent);

								// xml��������
								// ����һ���µ��ַ���
								StringReader read = new StringReader(responseContent);
								// �����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
								InputSource source = new InputSource(read);
								try {
									SAXParserFactory spf = SAXParserFactory
											.newInstance();
									SAXParser sp = spf.newSAXParser();
									XMLReader xr = sp.getXMLReader();
									SaxXmlContentHandler myExampleHandler = new SaxXmlContentHandler();
									xr.setContentHandler(myExampleHandler);
									xr.parse(source);
									ParsedExampleDataSet parsedExampleDataSet = myExampleHandler
											.getParsedData();

									String getstring = parsedExampleDataSet.toString();

									sta.str_stateLd[a] = getstring;
									//Log.d("fff", a+"�ŵ����ڻ�ȡ״̬");
									// �߳�ui����
								} catch (Exception e) {
									Log.d("test", "Error:" + e.getMessage());
								}
								h_oneHandler.sendEmptyMessage(a);
											
						    try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}			
						}
						catch (IOException e) {
							// Print any exceptions that occur
							e.printStackTrace();
						}
					}
				}}).start();
		}	
	}

}
