package com.example.cleverlampcontrol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	
	Button bluefilecontrol_button;
	Button bluenowsend_button;
	Button wififilecontrol_button;
	Button wifinowsend_button;
	Button creatfile_button;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bluefilecontrol_button = (Button)findViewById(R.id.button_bluefilecontrol);
		bluenowsend_button = (Button)findViewById(R.id.button_bluenowsend);
		wififilecontrol_button = (Button)findViewById(R.id.button_wififilecontrol);
		wifinowsend_button = (Button)findViewById(R.id.button_wifinowsend);
		creatfile_button = (Button)findViewById(R.id.button_creatfile);
		
		bluefilecontrol_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, bluefilecontrol.class);
                startActivity(intent);
				
			}
		});
		bluenowsend_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, bluefilenowsend.class);
                startActivity(intent);
			}
		});
		wififilecontrol_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, wififilecontrol.class);
                startActivity(intent);
			}
		});
		wifinowsend_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, wififilenowsend.class);
                startActivity(intent);
			}
		});
		creatfile_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, creatfile.class);
                startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
