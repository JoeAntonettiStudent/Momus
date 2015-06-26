package com.antonetti.floatingactionbutton;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageButton;

public class FABService extends AccessibilityService {

	private WindowManager manager;
	private ImageButton mainButton;
	private String[] blacklist = {
			"com.google.android.apps.magazines/com.google.apps.dots.android.newsstand.reading.ArticleReadingActivity"
	};
	
	String current = "";
	
	@Override 
	public void onCreate(){
		super.onCreate();
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		manager = (WindowManager) getSystemService(WINDOW_SERVICE);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				getDPI(56),
				getDPI(65),
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT
		);
		//params.verticalMargin = getDPI(56);
		params.gravity = Gravity.BOTTOM | Gravity.END;
		params.x = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
		params.y = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
		
        View buttonGroup = inflater.inflate(R.layout.floating_button, null);     
        
        final ViewGroup buttonContainer = (ViewGroup) buttonGroup.findViewById(R.id.fab_container);
        mainButton = (ImageButton) buttonGroup.findViewById(R.id.fab);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	fabAction();
            }
        });
        
        manager.addView(buttonGroup, params);
	}
	
	public int getDPI(int size){
		DisplayMetrics metrics;
		metrics = new DisplayMetrics();         
		((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
	     return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;        
	 }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if(mainButton != null)
			manager.removeView(mainButton);
	}
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        //Configure these here instead of in XML for compatibility with API 13 and below.
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        if (Build.VERSION.SDK_INT >= 16)
            //Just in case this helps
            config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            ComponentName componentName = new ComponentName(
                event.getPackageName().toString(),
                event.getClassName().toString()
            );

            ActivityInfo activityInfo = tryGetActivity(componentName);
            boolean isActivity = activityInfo != null;
            if (isActivity){
                Log.i("CurrentActivity", componentName.flattenToShortString());
                current = componentName.flattenToShortString();
            }
        }
    }

	private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
	
	public void fabAction(){
	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub
		
	}

}
