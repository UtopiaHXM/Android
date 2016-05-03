package com.D5.MyBean;

import com.D5.OfJFrams.Information;


public class Inform {
	private static Information infor;
	private static boolean isIn = false;
	public static Information getInfor() {
		return infor;
	}
	public static void setInfor(Information infor) {
		Inform.infor = infor;
	}
	public static boolean isIn() {
		return isIn;
	}
	public static void setIn(boolean isIn) {
		Inform.isIn = isIn;
	}
}
