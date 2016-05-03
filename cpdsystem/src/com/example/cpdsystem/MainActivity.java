package com.example.cpdsystem;

import com.example.fragment.AndTextFragmentMainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button lamppeizhi_button;
	Button sendfile_button;
	Button ldtime_Button;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lamppeizhi_button = (Button)findViewById(R.id.button_lamppeizhi);
		lamppeizhi_button.setOnClickListener(lamppeizhilistener);
		sendfile_button = (Button)findViewById(R.id.button_sendfile);
		sendfile_button.setOnClickListener(sendfilelistener);
		ldtime_Button = (Button)findViewById(R.id.button_ldtime);
		ldtime_Button.setOnClickListener(ldtimelistener);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public OnClickListener lamppeizhilistener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, AndTextFragmentMainActivity.class);
            startActivity(intent);
			
		}
	};
    public OnClickListener ldtimelistener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, GraMainActivity.class);
            startActivity(intent);
			
			
		}
	};
    public OnClickListener sendfilelistener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, sendfileActivity.class);
            startActivity(intent);
			
		}
	};

}
