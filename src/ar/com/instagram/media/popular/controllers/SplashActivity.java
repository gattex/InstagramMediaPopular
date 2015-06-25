package ar.com.instagram.media.popular.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import ar.com.instagram.media.popular.MainActivity;
import ar.com.instagram.media.popular.R;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 5000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_splash);
	        new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
	                SplashActivity.this.startActivity(mainIntent);
	                SplashActivity.this.finish();
	            }
	        }, SPLASH_DISPLAY_LENGTH);
	}

}
