package com.example.Activities;
import java.io.IOException;
import com.example.Data.Data;
import com.example.Tools.ExitApplication;
import com.example.meimei.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AbstractActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
		Configuration config = getResources().getConfiguration();
		int ori = config.orientation;
		if(ori == config.ORIENTATION_LANDSCAPE){
			  //设置竖屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {   
		    case R.id.item_exit:
		    	Toast.makeText(this, "退出应用程序", Toast.LENGTH_SHORT).show();

				if(Data.SocketType!=null){
					if(Data.SocketType.equals("BluetoothSocket")){
						try {
							Data.ClientblueSocket.getOutputStream().write("断开连接".getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if(Data.SocketType.equals("WifiSocket")){
						try {
							Data.ClientwifiSocket.getOutputStream().write("断开连接".getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					Log.d("tag", "无连接");
				}
				
		    	ExitApplication.getInstance().exit(this);
		        break;  
		    case R.id.item_tips:
		    	Log.d("tag", "tag count = 0");
		    	View_flow_activity.count = 0;
		    	View_flow_activity.isRun = true;
		    	Intent intent = new Intent(AbstractActivity.this, View_flow_activity.class);
		    	startActivityForResult(intent, 0);
		    default:  
		        break;  
		}  

		return super.onMenuItemSelected(featureId, item);
	}

	protected void onActivityResult(int requestCode, int resultCode) {
		switch(resultCode) {
		case 0:
			//onStart();
			Log.d("tag", "back");
			break;
		default:
			break;
		}
 	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
