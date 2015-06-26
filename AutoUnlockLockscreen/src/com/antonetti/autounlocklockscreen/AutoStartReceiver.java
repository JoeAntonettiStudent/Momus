package com.antonetti.autounlocklockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

public class AutoStartReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		String intentAction = intent.getAction();
		if(intentAction.equals(Intent.ACTION_SCREEN_OFF) || intentAction.equals(Intent.ACTION_BOOT_COMPLETED)){
			Intent lockscreenIntent = new Intent(context, LockscreenActivity.class);
			lockscreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			lockscreenIntent.addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
			context.startActivity(lockscreenIntent);
		}
    }
}
