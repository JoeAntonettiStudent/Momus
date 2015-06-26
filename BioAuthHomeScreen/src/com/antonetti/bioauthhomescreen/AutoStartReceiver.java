package com.antonetti.bioauthhomescreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartReceiver extends BroadcastReceiver{
	
	public void onReceive(Context arg0, Intent arg1) {
        if(arg1.getAction().equals("android.intent.action.SCREEN_ON") || arg1.getAction().equals("android.intent.action.SCREEN_OFF")) {
        	System.out.println("here");
            Intent localIntent = new Intent(arg0, LockscreenActivity.class);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.putExtra("LOCK", false);
            arg0.startActivity(localIntent);
        }
    }

}
