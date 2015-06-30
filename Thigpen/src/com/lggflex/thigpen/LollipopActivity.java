package com.lggflex.thigpen;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class LollipopActivity extends AppCompatActivity implements OnRecyclerViewClickListener{
	
	protected Toolbar toolbar;
	protected RecyclerView recyclerView;
	protected RecyclerViewAdapter<?, ?> adapter;
	protected FloatingActionButton floatingActionButton;
	protected int primary, accent, buildNumber;
	protected GridLayoutManager recyclerLayoutManager;
	
	private boolean hasFAB;
	
	//Set up the UI stuff in the app. Needs to be called after view is initialized
	protected void initUIFlourishes(boolean includeFAB, int primary, int accent){
		
		createToolbar();
		
		this.primary = primary;
		this.accent = accent;
		this.hasFAB = includeFAB;
		this.buildNumber = Build.VERSION.SDK_INT;
		
		//Only setup Lollipop features on Lollipop
		if(buildNumber >= Build.VERSION_CODES.LOLLIPOP){
			setStatusBarColor();
		}
	}
	protected void initFAB(int id, OnClickListener listener){
		floatingActionButton = (FloatingActionButton) findViewById(R.id.send);
		floatingActionButton.setOnClickListener(listener);
	}
	protected void initRecyclerView(int id, int columns){
		recyclerView = (RecyclerView) findViewById(id);
		recyclerLayoutManager = new GridLayoutManager(getApplicationContext(), columns);
    	recyclerView.setLayoutManager(recyclerLayoutManager);
    	adapter.setOnCategoryClickListener(this);
    	recyclerView.setAdapter(adapter);
	}
	protected void makeChildOfRoot(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}	
	protected void setTitle(String title){
		getSupportActionBar().setTitle(title);
	}
	protected int getStatusBarHeight(){
		int result = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resId > 0)
			result = getResources().getDimensionPixelSize(resId);
		return result;
	}
	@SuppressLint("NewApi")
	protected void themeToColors(){
		if(primary != -1)
			toolbar.setBackgroundColor(primary);
		if(accent != -1 && hasFAB)
			floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(accent));
	}
	@SuppressLint("NewApi")
	
	private void setStatusBarColor(){
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
	}
	@SuppressLint("InlinedApi")
	private void createToolbar(){
		if(buildNumber >= Build.VERSION_CODES.JELLY_BEAN)
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);	
	}
	
	@Override
	abstract public <T> void onItemClick(View view, T viewModel);
	

}
