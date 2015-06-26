package com.antonetti.bioauthhomescreen;

import java.net.URISyntaxException;

import org.json.JSONObject;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class LockscreenActivity extends ActionBarActivity {
	
	private boolean isUnlocked = false;
	
	private final String SERVER_URL = "http://192.168.1.3:3000";
	private final String USERNAME = "Harrison Wells";
	private final String PASSWORD = "StateFarm1234";
	private Socket socket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent service = new Intent(this, KeyguardService.class);
		startService(service);
		setContentView(R.layout.activity_lockscreen);
		final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
		final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		wallpaperDrawable.setAlpha(125);
		RelativeLayout background = (RelativeLayout) findViewById(R.id.mainLayout);
		background.setBackground(wallpaperDrawable); 
		Button launcherButton = (Button) findViewById(R.id.home); 
		launcherButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				attemptUnlock();
			}			
		});
		try {
			socket = IO.socket(SERVER_URL);
			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){

				@Override
				public void call(Object... arg0) {
					Log.i("SERVER", "Connected");
					
				}
				
			});
			socket.on("sendDescriptionOfYourself", new Emitter.Listener(){

				@Override
				public void call(Object... arg0) {
					JSONObject sentObject = new JSONObject();
					sentObject.put("username", USERNAME);
					socket.emit("firstConnection", sentObject);
				}
				
			});
			socket.on("authenticationDidSucceed", new Emitter.Listener(){

				@Override
				public void call(Object... arg0) {
					Log.i("Test", "succeed");
					  enableUnlock();
				}
				
			});
			socket.on("authenticationDidFail", new Emitter.Listener(){

				@Override
				public void call(Object... arg0) {
					Log.i("Test", "fail");
					lock();
				}
				
			});
			socket.connect();
			Log.i("SERVER", "Connecting...");
			//socket.emit("firstConnection", "Hi");
		} catch (URISyntaxException e) {
			Log.i("Server", e.toString());
		}
	}
	
	public void attemptUnlock(){
	//	JSONObject sentObject = new JSONObject();
	//	sentObject.put("username", USERNAME);
	//	sentObject.put("password", PASSWORD);
		socket.emit("promptForAuthentication");
	}

	
	public void lock(){
		isUnlocked = false;
	}
	
	public void enableUnlock(){
		isUnlocked = true;
		unlock();
	}
	
	public void unlock(){
		Log.i("Test", "succeed");
		Intent launcherActivity = getPackageManager().getLaunchIntentForPackage("com.teslacoilsw.launcher");
		startActivity(launcherActivity);
	}
	
	@Override
	protected void onNewIntent(Intent intent){
		isUnlocked = intent.getBooleanExtra("LOCK", true);
		super.onNewIntent(intent);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(isUnlocked)
			unlock();
	}
}