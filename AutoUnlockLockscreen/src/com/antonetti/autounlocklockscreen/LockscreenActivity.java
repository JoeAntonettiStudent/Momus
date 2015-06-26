package com.antonetti.autounlocklockscreen;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class LockscreenActivity extends ActionBarActivity {

	public WindowManager winManager;
	public RelativeLayout wrapperView;

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams( WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
	                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
	                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
	                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
	                PixelFormat.TRANSLUCENT);
	        this.winManager = ((WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE));
	        this.wrapperView = new RelativeLayout(getBaseContext());
	        getWindow().setAttributes(localLayoutParams);
	        View.inflate(this, R.layout.activity_lockscreen, this.wrapperView);
	        this.winManager.addView(this.wrapperView, localLayoutParams);
	}

	 public void onDestroy()
	    {
	        this.winManager.removeView(this.wrapperView);
	        this.wrapperView.removeAllViews();
	        super.onDestroy();
	    }
}
