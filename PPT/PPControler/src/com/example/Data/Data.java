package com.example.Data;

import java.net.Socket;
import android.bluetooth.BluetoothSocket;

public class Data {

	public static BluetoothSocket ClientblueSocket;
	public static String deviceName = "none";
	public static String IP = "none";
	public static Socket ClientwifiSocket;
	public static String SDPath;
	public static String UpFile;
	public static String DownFile;
	public static String isExit = "none";
	public static String SocketType;
	public static Boolean isExtand = false;
	public static String currentFileFrom = "";
	public static Boolean isUpLoaded = false;
	public static Boolean isDownLoaded = false;

}
