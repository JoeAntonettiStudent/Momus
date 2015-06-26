package com.antonetti.autounlocklockscreen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class KeyguardService extends Service{
	
	@Override
	public void onCreate(){
		super.onCreate();
		((KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE)).newKeyguardLock("IN").disableKeyguard();
	     IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
	     AutoStartReceiver receiver = new AutoStartReceiver();
	     registerReceiver(receiver, filter);
	     
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
