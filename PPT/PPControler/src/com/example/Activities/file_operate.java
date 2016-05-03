package com.example.Activities;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.Data.Data;
import com.example.meimei.R;

@SuppressLint("ShowToast")
public class file_operate extends AbstractActivity {
	private BluetoothSocket ClientBlueSocket;
	private Socket ClientWifiSocket;
	private OutputStream ClientOutputStream;
	private Vibrator mVibrator;

	private ImageButton screenButton, exitButton, preButton, nextButton,
			closeButton;
	private Button jumpButton;
	private EditText jumpEditText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_operate);

		mVibrator = (Vibrator) getApplication().getSystemService(
				Context.VIBRATOR_SERVICE);
		if (Data.SocketType.equals("WifiSocket")) {
			ClientWifiSocket = Data.ClientwifiSocket;
			try {
				ClientOutputStream = ClientWifiSocket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (Data.SocketType.equals("BluetoothSocket")) {
			ClientBlueSocket = Data.ClientblueSocket;
			try {
				ClientOutputStream = ClientBlueSocket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		jumpEditText = (EditText) findViewById(R.id.JumpEditText);
		jumpButton = (Button) findViewById(R.id.JumpButton);
		screenButton = (ImageButton) findViewById(R.id.screenButton);
		exitButton = (ImageButton) findViewById(R.id.exitButton);
		preButton = (ImageButton) findViewById(R.id.preButton);
		nextButton = (ImageButton) findViewById(R.id.nextButton);
		closeButton = (ImageButton) findViewById(R.id.closeButton);
		

		buttonListener listener = new buttonListener();

		screenButton.setOnClickListener(listener);
		exitButton.setOnClickListener(listener);
		preButton.setOnClickListener(listener);
		nextButton.setOnClickListener(listener);
		closeButton.setOnClickListener(listener);
		jumpButton.setOnClickListener(listener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			file_search.getRevHandler().sendEmptyMessage(0x04);
			System.out.println("come back");

		}
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			System.out.println("---------ACTION_DOWN------");
			if (ClientOutputStream == null) {
				System.out.println("clientsocket创建失败");
			} else {
				System.out.println("clientsocket创建成功");
				int left = 1;
				System.out.println("左" + "   " + left);

				byte[] b_left = (left + "").getBytes();

				try {
					ClientOutputStream.write(b_left);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			System.out.println("---------ACTION_DOWN------");
			if (ClientOutputStream == null) {
				System.out.println("clientsocket创建失败");
			} else {
				System.out.println("clientsocket创建成功");
				int right = 2;

				System.out.println("右" + "    " + right);
				byte[] b_right = (right + "").getBytes();

				try {
					ClientOutputStream.write(b_right);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showTips() {
		AlertDialog alertDialog = new AlertDialog.Builder(file_operate.this)
				.setTitle("删除文件")
				.setMessage("是否删除文件")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						if (ClientOutputStream == null) {
							System.out.println("clientsocket创建失败");
							Toast.makeText(file_operate.this, "连接已断开",
									Toast.LENGTH_SHORT);
						} else {
							System.out.println("clientsocket创建成功");

							byte[] info = "删除文件".getBytes();

							try {
								ClientOutputStream.write(info);
							} catch (IOException e) {
								e.printStackTrace();
								Toast.makeText(file_operate.this, "连接已断开",
										Toast.LENGTH_SHORT);
							}
						}
						file_operate.this.finish();

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// file_search.getRevHandler().sendEmptyMessage(0x04);

						file_operate.this.finish();

					}
				}).create(); // 创建对话框
		alertDialog.show(); // 显示对话框
	}

	class buttonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (screenButton.isPressed() || exitButton.isPressed()
					|| preButton.isPressed() || nextButton.isPressed()
					|| closeButton.isPressed()) {
				mVibrator.vibrate(new long[] { 0, 100, 0, 0 }, -1);
				/*
				 * 第一個參數：停0ms，震15ms,停0ms,震0ms， 第二個參數：-1表示不重復
				 * 非-1表示從指定的下標開始震動，如果第二個參數是0，則一圈一圈的震動下去了
				 * 如果是2，這第一次震動后，從"20"這個參數開始再循環震動
				 * 
				 * 通过设置震动的时间大小使我们可以感觉到震动的差异
				 */

			} else {
				mVibrator.cancel();

			}
			switch (v.getId()) {
			case R.id.JumpButton:
				if (ClientOutputStream == null) {
					System.out.println("clientsocket创建失败");
				} else {
					System.out.println("clientsocket创建成功");

					String page = jumpEditText.getText().toString();
					byte[] info = (page+"跳到具体某一页").getBytes();

					try {
						ClientOutputStream.write(info);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				break;

			case R.id.screenButton:
				if (ClientOutputStream == null) {
					System.out.println("clientsocket创建失败");
				} else {
					System.out.println("clientsocket创建成功");

					byte[] info = "最大化文件".getBytes();

					try {
						ClientOutputStream.write(info);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				break;
			case R.id.exitButton:
				if (ClientOutputStream == null) {
					System.out.println("clientsocket创建失败");
				} else {
					System.out.println("clientsocket创建成功");

					byte[] info = "退出最大化".getBytes();
					Data.isExtand = false;

					try {
						ClientOutputStream.write(info);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				break;
			case R.id.preButton:
				if (ClientOutputStream == null) {
					System.out.println("clientsocket创建失败");
				} else {
					System.out.println("clientsocket创建成功");
					int left = 1;
					System.out.println("左" + "   " + left);

					byte[] b_left = (left + "").getBytes();

					try {
						ClientOutputStream.write(b_left);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				break;
			case R.id.nextButton:
				if (ClientOutputStream == null) {
					System.out.println("clientsocket创建失败");
				} else {
					System.out.println("clientsocket创建成功");
					int right = 2;

					System.out.println("右" + "    " + right);
					byte[] b_right = (right + "").getBytes();

					try {
						ClientOutputStream.write(b_right);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				break;

			case R.id.closeButton:

				if (ClientOutputStream == null) {
					System.out.println("clientsocket创建失败");
				} else {
					System.out.println("clientsocket创建成功");

					if (Data.isExtand) {
						Toast.makeText(file_operate.this, "请先退出最大化模式",
								Toast.LENGTH_SHORT).show();
					} else {
						byte[] info = "关闭文件".getBytes();

						try {
							ClientOutputStream.write(info);
						} catch (IOException e) {
							e.printStackTrace();
						}
						showTips();
					}

				}
				break;
			default:

				break;

			}
		}

	}

}
