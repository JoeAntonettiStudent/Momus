package com.lggflex.thigpen;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ToolbarActivity extends ActionBarActivity{
	
	protected Toolbar toolbar;
	
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
	
	public boolean onOptionsItemSelected(MenuItem item){
	    finish();
	    return true;
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

}
