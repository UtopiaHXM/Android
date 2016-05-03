package com.D5.Threads;

import java.io.File;
import java.io.FilenameFilter;

class FileAccept implements FilenameFilter {
	String str = null;

	FileAccept(String s) {
		str = "." + s;
	}

	public boolean accept(File dir, String namestring) {
		return namestring.endsWith(str);
	}
}