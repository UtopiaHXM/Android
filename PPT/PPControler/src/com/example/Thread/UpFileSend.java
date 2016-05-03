package com.example.Thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import com.example.Data.Data;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class UpFileSend implements Runnable {

	private Context context;
	private Handler handler;
	private OutputStream outputStream;
	private InputStream inputStream;

	public UpFileSend(Context context, Handler handler,
			InputStream inputStream, OutputStream outputStream) {
		this.context = context;
		this.handler = handler;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.d("tag", "========upThread is run=======");

		try {
			if (Data.UpFile != null) {
				sendMessage(inputStream, outputStream);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendMessage(InputStream inputstream, OutputStream outputStream)
			throws Exception {
		File file = new File(Data.UpFile);
		// file.createNewFile();
		Log.d("tag", "" + file.getPath());
		if (file.exists()) {
			Log.d("tag",
					"------------------------------------------------------------------------------");
			Log.d("tag",
					"you" + file.getPath() + "  fileName:" + file.getName()
							+ "  Data.UpFile:" + Data.UpFile);
			Log.d("tag",
					"------------------------------------------------------------------------------");

			DataInputStream fis = new DataInputStream(new FileInputStream(
					Data.UpFile));
			Log.d("tag", "fis::" + fis.available());

			DataOutputStream ps = new DataOutputStream(outputStream);
			// ���ļ��������ȴ����ͻ��ˡ�����Ҫ������������ƽ̨�������������Ĵ�������Ҫ�ӹ���������Բμ�Think In Java
			// 4th�����ֳɵĴ��롣
			Log.d("tag", "ps::" + ps.size());

			// ps.writeUTF(file.getName());
			// ps.flush();
			ps.writeLong((long) file.length());
			ps.flush();

			int bufferSize = 1024;
			byte[] buf = new byte[bufferSize];
			int read = -1;
			if (fis != null) {
				while ((read = fis.read(buf)) > 0) {
					ps.write(buf, 0, read);
				}
				Log.d("test", "�������");
				Data.isUpLoaded = true;
			}
			ps.flush();
			// ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����
			// ֱ��socket��ʱ���������ݲ�������
			Data.UpFile = null;
			fis.close();
			// ps.close();
			Toast.makeText(context, "�ļ��������", Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessage(0x04);
		}

	}

}
