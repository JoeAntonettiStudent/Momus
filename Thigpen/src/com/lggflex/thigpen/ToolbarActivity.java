package com.lggflex.thigpen;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ToolbarActivity extends ActionBarActivity{
	
	protected Toolbar toolbar;
	
	protected FloatingActionButton fab;
	
	protected void setTitle(String title){
		getSupportActionBar().setTitle(title);
	}
	
	protected void isChildOfRoot(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	protected void createToolbar(){
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
		if(Build.VERSION.SDK_INT >= 21)
			setStatusBarColor();
	}

	
	private int getStatusBarHeight(){
		int result = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resId > 0)
			result = getResources().getDimensionPixelSize(resId);
		return result;
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void setStatusBarColor(){
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
	}
	
	protected void makeList(int id, int layout, ArrayAdapter<String> adapter, OnItemClickListener listener){
		ListView list = (ListView) findViewById(id);
		list.setAdapter(adapter);
		if(listener != null)
			list.setOnItemClickListener(listener);
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	protected void themeToColors(int primary, int accent){
		if(primary != -1)
			toolbar.setBackgroundColor(primary);
		if(accent != -1)
			fab.setBackgroundTintList(ColorStateList.valueOf(accent));
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	protected void setupWindowAnimations() {
	        Explode explode = new Explode();
	        explode.setDuration(2000);
	        getWindow().setEnterTransition(explode);
	}

}
