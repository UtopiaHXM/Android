package com.D5.Function;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.D5.MyBean.MyChooser;
import com.D5.OfJFrams.tabShow;
import com.D5.Threads.Getrobot;


public class IsFunction {
	private Getrobot getRobot = new Getrobot();
	@SuppressWarnings("unused")
	private OutputStream outputStream;
	private FileAccept acceptfile;
	private File filePPT = null;
	private static List<String> fileName = null;
	private static File files;
	private static File file;

	public IsFunction() {
		this.acceptfile = new FileAccept("ppt", "dps");
	}

	public static List<String> getFileName() {
		return fileName;
	}

	public static void setFileName(List<String> fn) {
		fileName = fn;
	}

	public File getPPTfile() {
		return filePPT;
	}

	public static void setFile(File f) {
		file = f;
	}

	public static File getFile() {
		return file;
	}

	public Boolean JumpToPage(int page,int[] pages){
		return getRobot.robotmake(page,pages);
	}
	
	public Boolean TurnPage(int page) {
		return getRobot.robotmake(page,null);
	}

	public Boolean MaxSlide(int Max) {
		return getRobot.robotmake(Max,null);
	}

	public Boolean unMaxSlide(int unMax) {
		return getRobot.robotmake(unMax,null);
	}

	public Boolean CloseFile(int closeFile) {
		return getRobot.robotmake(closeFile,null);
	}

	public Boolean DeleteFile(String deleteFile, File deleteF,
			OutputStream outputStream) {
		Boolean isSuccess = false;
		this.outputStream = outputStream;
		File currentFile = deleteF;
		if (currentFile.exists()) {// 删除文件
			currentFile.delete();
		}
		try {
			if (outputStream != null) {
				String str = currentFile.getName() + "删除成功";
				byte[] info = str.getBytes();
				outputStream.write(info);
				outputStream.flush();
				isSuccess = true;
			} else {
				String str = currentFile.getName() + "删除失败";
				byte[] info = str.getBytes();
				outputStream.write(info);
				outputStream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	public void OpenFolder(String openFile, OutputStream outputStream) {
		this.outputStream = outputStream;
		files = new File(MyChooser.getChoosePath());
		fileName = tabShow.getFilesNameString(files);
		try {
			if (outputStream != null) {
				if (fileName.size() <= 0) {
					byte[] buffer = "当前路径下没有ppt文件".getBytes();
					outputStream.write(buffer);
					outputStream.flush();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					for (int i = 0; i < fileName.size(); i++) {
						byte[] buffer = fileName.get(i).getBytes();
						outputStream.write(buffer);
						outputStream.flush();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					outputStream.write("END".getBytes());
					outputStream.flush();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			} else {
				System.out.println("outputStream is null");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void OpenFile(String openFile, OutputStream outputStream) {
		this.outputStream = outputStream;
		// 获取打开文件的路径的文件的名字
		file = new File(openFile);
		setFile(file);
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
