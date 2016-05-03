package com.example.cpdsystem;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.DB.DBAdapter;

import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class sendData implements Runnable{

	ArrayList<byte[]> sendArrayList = new ArrayList<byte[]>();
	String dataString="";
	private static final String username = "wfy"; // put your Device Cloud username here
    private static final String password = "!QA2ws#ED"; // put your Device Cloud password here
	
    String path,name,table;
    DBAdapter dbAdapter;
    String gateway,node;
    FileDeal fd = new FileDeal();
    Bundle b_one;
    Message m_one;
    Handler h_one;
    public sendData(String path,String name,String table,DBAdapter db,Handler handler) {
		// TODO Auto-generated constructor stub
		Log.d("test","000000");
		this.name=name;
		this.path=path;
		this.table = table;
		dbAdapter=db;
		h_one=handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		

	    /**
	     * Run the web service request
	     */
		int count =0;
		Log.d("test","11111");
		String[] infos =dbAdapter.quaryOne(table,name);
		gateway=infos[0];
		node = infos[1];
		String value = "        <"+node+" value=\""+"ffff01"+"\"/>\r\n";
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
	            String userpassword = username + ":" + password;

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
	            b_one.putString("result", "SENDING");
	            m_one = new Message();
	            m_one.setData(b_one);
	            h_one.sendMessage(m_one);
	        } catch (Exception e) {
	            // Print any exceptions that occur
	            e.printStackTrace();
	            b_one = new Bundle();
	            b_one.putString("result", "FALSE");
	            m_one = new Message();
	            m_one.setData(b_one);
	            h_one.sendMessage(m_one);
	        }
		sendArrayList = fd.readtxtFile(path);
		String dataString = "";
		for(int i=0;i<sendArrayList.size();i++)
		{
			byte[] databyte= sendArrayList.get(i);
			dataString =  bytesToHexString(databyte);
			Log.d("test","data"+dataString);
			 value = "        <"+node+" value=\""+dataString+"\"/>\r\n";
			ID = "      <device id=\"" + gateway + "\"/>\r\n";
		        

		        try {
		        	Log.d("aaa","00");
		            // Create url to the Device Cloud server for a given web service request
		            URL url = new URL("http://login.etherios.com:80/ws/sci");
		            conn = (HttpURLConnection) url.openConnection(); 
		            //conn.setDoOutput(true);
		            //conn.setDoInput(true);
		            conn.setRequestMethod("POST"); 
		            
		            // Build authentication string
		            String userpassword = username + ":" + password;

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
		            count++;
		            Log.d("aaa","11");
		            
		        } catch (Exception e) {
		            // Print any exceptions that occur
		            e.printStackTrace();
		            b_one = new Bundle();
		            b_one.putString("result", "FALSE");
		            m_one = new Message();
		            m_one.setData(b_one);
		            h_one.sendMessage(m_one);
		        } finally {
		        	Log.d("aaa","22");
		        	
		            if (conn != null)
		                conn.disconnect();
		        }
		        try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
		}
		if(count==sendArrayList.size()){
			b_one = new Bundle();
            b_one.putString("result", "OK");
            m_one = new Message();
            m_one.setData(b_one);
            h_one.sendMessage(m_one);
		}
		
	            
	       
	    }
	
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}
	

	
}
