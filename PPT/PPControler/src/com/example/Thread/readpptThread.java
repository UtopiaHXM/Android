package com.example.Thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.example.Activities.file_search;
import com.example.Data.Data;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi")
public class readpptThread extends Thread {

	private List<String> NameList = new ArrayList<String>();

	private static Boolean isAlive = true;
	private byte b_byte[];

	private Message readMessage;
	private Bundle bundleMessage;
	private BluetoothSocket ClientblueSocket;
	private Socket ClientwifiSocket;
	private InputStream ClientinputStream;

	private String getString;
	private static String[] NameString;
	private Handler handler;

	public static Boolean getIsAlive() {
		return isAlive;
	}

	public static void setIsAlive(Boolean isAlive) {
		readpptThread.isAlive = isAlive;
	}

	public readpptThread(Handler handler) {

		this.handler = handler;

	}

	public static String[] Get_String() {

		return NameString;

	}

	private List<String> getPPTs(String ppts) {
		List<String> list = new ArrayList<String>();
		String[] pptCollections = ppts.split(".");
		int num = pptCollections.length;
		if (num == 2) {// 只存在一个文件
			list.add(pptCollections[0]);
			return list;
		} else if (num == 1) {// 不存在文件
			return null;
		} else {// 网络环境不好时，收到多个文件；
			while (ppts.indexOf(".") != ppts.lastIndexOf(".")) {
				int index = ppts.indexOf(".");

				// 第一个ppt文件
				String ppt = ppts.substring(0, index + 3);
				list.add(ppt);
				// 将收到的字符串改为不包含第一个文件的字符串
				ppts = ppts.substring(index + 4);
			}
			list.add(ppts);
			return list;
		}
	}

	@Override
	public void run() {

		if (isAlive == true) {
			if (Data.SocketType != null && isAlive == true) {
				isAlive = true;
				Log.d("thread", "readpptThread is run");
				NameList.clear();
				if (Data.SocketType.equals("WifiSocket")) {
					ClientwifiSocket = Data.ClientwifiSocket;
					try {
						ClientinputStream = ClientwifiSocket.getInputStream();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (Data.SocketType.equals("BluetoothSocket")) {
					ClientblueSocket = Data.ClientblueSocket;
					try {
						ClientinputStream = ClientblueSocket.getInputStream();
						Log.d("tag", "blueinputStream in readppt"
								+ ClientinputStream);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Log.d("tag", "未知类型socket");
				}

				Log.d("tag", "blueSocket=" + ClientblueSocket + " wifiSocket="
						+ ClientwifiSocket + " input=" + ClientinputStream
						+ " isAlive=" + isAlive);
				if ((ClientblueSocket != null || ClientwifiSocket != null)
						&& ClientinputStream != null && isAlive == true) {
					Log.d("tag", "thread 接收");
					while (true && isAlive == true) {
						try {
							while (ClientinputStream.available() > 0) {
								Log.d("02",
										"num" + ClientinputStream.available());
								b_byte = new byte[ClientinputStream.available()];
								ClientinputStream.read(b_byte);
								getString = new String(b_byte,
										Charset.forName("GB2312"));

								Log.d("name1", "recievePPT:" + getString);

								if (getString.equals("当前路径下没有ppt文件")) {

									handler.sendEmptyMessage(0x110);
									Log.d("getString", "getFromServer"
											+ getString);

								} else if (getString.equals("存在BlueTooth连接")) {
									Data.isExit = "bluetooth";
									Log.d("tag", "isExit in thread "
											+ Data.isExit);

								} else if (getString.equals("存在wifi连接")) {
									Data.isExit = "wifi";
									Log.d("tag", "isExit in thread "
											+ Data.isExit);

								} else if (getString.equals("END")) {
									Log.d("name1", ".." + NameString);
									NameString = new String[NameList.size()];
									System.out.println("List的长度："
											+ NameList.size() + "String[]长度："
											+ NameString.length);

									NameList.toArray(NameString);
									bundleMessage = new Bundle();

									bundleMessage.putStringArray("2",
											NameString);
									readMessage = new Message();
									readMessage.what = 0x02;

									readMessage.setData(bundleMessage);

									handler.sendMessage(readMessage);
									NameList.clear();
									
								} else if (getString.endsWith(".ppt")
										|| getString.endsWith(".PPT")
										|| getString.endsWith(".dps")
										|| getString.endsWith(".DPS")) {
									List<String> list = getPPTs(getString);
									if (list != null) {
										int times = 0;
										for (String ppt : list) {
											if (times != 0) {
												Log.d("times", "不只收到一个文件");
											}
											ppt = list.get(times);
											NameList.add(ppt);

											System.out.println("PPT_名字:"
													+ getString);
											times++;

										}
									} else {
										handler.sendEmptyMessage(0x03);

									}

								} else if (getString.equals("连接已断开")) {
									handler.sendEmptyMessage(0x04);
									Data.isExit = "none";

								} else if (getString.equals("文件不存在")) {

									handler.sendEmptyMessage(0x03);
								} else if (getString.equals("Max")) {
									Data.isExtand = true;
								} else if (getString.equals("download")) {
									DataInputStream dataInputStream = new DataInputStream(ClientinputStream);
									//本地保存路径，文件名会自动从服务器端继承而来。
						            String savePath = Data.SDPath+"/"+dataInputStream.readUTF();
						            System.out.println("savePath=="+savePath);
						            int bufferSize = 8192;
						            byte[] buf = new byte[bufferSize];
						            int passedlen = 0;
						            long len=0;
						            
						            File file = new File(savePath);
						            if(file.exists()){
						            	//Toast.makeText(readpptThread.this, "文件已存在，无需下载！", Toast.LENGTH_LONG).show();
						            }
						            DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(savePath));
						            len = dataInputStream.readLong();
						            
						            Log.d("tag", "len=="+len);
									int read = 0;
						            while ((read=dataInputStream.read(buf))>0) {
						            	passedlen += read;
						            	//下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
						                int temp = (int)(passedlen * 100/ len);	                
						                Log.d("tag", "文件接收了"+(passedlen * 100/ len) + "%\n");
						          
						                
						                fileOut.write(buf, 0, read);
						                if(temp==100){
						                	//Toast.makeText(context, "下载完成", Toast.LENGTH_LONG).show();
						                	break;
						                }
									}              
						            System.out.println("接收完成，文件存为" + savePath + "\n");
						            fileOut.close();
								} else {
									/*bundleMessage = new Bundle();
									bundleMessage.putString("1", getString);
									readMessage = new Message();
									readMessage.what = 0x01;
									readMessage.setData(bundleMessage);
									Log.d("tag", "readThread" + getString);
									handler.sendMessage(readMessage);*/
								}
							}

						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							b_byte = null;
							getString = null;
							NameString = null;
							bundleMessage = null;
							readMessage = null;
						}
					}

				} else {
					System.out.println("socket is null");
				}
			}
		} else {
			isAlive = false;
		}
	}
}
