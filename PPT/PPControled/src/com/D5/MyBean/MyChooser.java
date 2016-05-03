package com.D5.MyBean;

import com.D5.OfJFrams.importPPT;


public class MyChooser {
	private static importPPT importppt;
	private static boolean isSetPath = false;
	private static String choosePath = "";

	public static boolean isSetPath() {
		return isSetPath;
	}

	public static void setSetPath(boolean isSetPath) {
		MyChooser.isSetPath = isSetPath;
	}

	public static String getChoosePath() {
		return choosePath;
	}

	public static void setChoosePath(String choosePath) {
		MyChooser.choosePath = choosePath;
	}

	public static importPPT getImportppt() {
		return importppt;
	}

	public static void setImportppt(importPPT importppt) {
		MyChooser.importppt = importppt;
	}
}
