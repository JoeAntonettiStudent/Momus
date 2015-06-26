package com.sf.odometersnap.util;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {
	
	public static void popNotification(Context context, String title, String text, int icon, int color){
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
			.setSmallIcon(icon)
			.setContentTitle(title)
			.setColor(color)
			.setContentText(text);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(0, builder.build());
	}

}
