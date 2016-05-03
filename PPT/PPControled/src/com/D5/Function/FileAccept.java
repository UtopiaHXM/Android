package com.D5.Function;

import java.io.File;
import java.io.FilenameFilter;

class FileAccept implements FilenameFilter {
	String ppt = null;
	String dps = null;

	FileAccept(String ppt, String dps) {
		this.ppt = "." + ppt;
		this.dps = "." + dps;
	}

	public boolean accept(File dir, String namestring) {
		if (namestring.endsWith("dps")) {
			return namestring.endsWith(dps);
		} else {
			return namestring.endsWith(ppt);
		}
	}
}