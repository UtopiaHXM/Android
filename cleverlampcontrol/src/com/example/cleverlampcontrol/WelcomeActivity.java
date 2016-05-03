
package com.example.cleverlampcontrol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;


public class WelcomeActivity extends Activity {

    Thread mthread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        mthread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });
        mthread.start();
    }
}