package com.D5.Function;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class getIPOFPC {

	public static String getLocalIp() {
		String IP = new String();
		try {
			InetAddress myip = InetAddress.getLocalHost();
			IP = (myip.toString()).substring(myip.toString().indexOf("/") + 1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return IP;
	}
}
