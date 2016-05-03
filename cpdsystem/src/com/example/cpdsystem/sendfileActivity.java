package com.example.cpdsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class sendfileActivity extends Activity{
	Button bluesend_button;
	Button zigbeesend_button;
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.sendfile);
	        zigbeesend_button = (Button)findViewById(R.id.button_zigbeesend);
	        zigbeesend_button.setOnClickListener(zigbeesendlistener);
	        bluesend_button = (Button)findViewById(R.id.button_bluesend);
	        bluesend_button.setOnClickListener(bluesendlistener);
	 }
	 public OnClickListener zigbeesendlistener = new OnClickListener() {
			
		public void onClick(View v) {
			Intent intent = new Intent(sendfileActivity.this, zigbeeAcitvity.class);
            startActivity(intent);
			
		}
	}; 
	public OnClickListener bluesendlistener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent(sendfileActivity.this, bluesendActivity.class);
            startActivity(intent);
			
		}
	};
	 
}
