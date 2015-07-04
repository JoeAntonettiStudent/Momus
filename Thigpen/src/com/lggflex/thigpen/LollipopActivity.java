package com.lggflex.thigpen;

import com.lggflex.thigpen.adapter.RecyclerViewAdapter;
import com.lggflex.thigpen.backend.DAO;
import com.lggflex.thigpen.fragment.OnRecyclerViewClickListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public abstract class LollipopActivity extends AppCompatActivity implements OnRecyclerViewClickListener{
	
	protected Toolbar toolbar;
	protected RecyclerView recyclerView;
	protected RecyclerViewAdapter<?, ?> adapter;
	protected FloatingActionButton floatingActionButton;
	protected int primary, accent, buildNumber;
	protected GridLayoutManager recyclerLayoutManager;
	protected NavigationView navigationView;
	
	protected static final String EXTRA_IMAGE = "com.lggflex.thigpen.extraImage";
	protected static final String EXTRA_TITLE = "com.lggflex.thigpen.extraTitle";
	protected static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	protected static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
	
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
	public void open(Class<?> cls){
		Intent settings = new Intent(this.getApplicationContext(), cls);
		startActivity(settings);
	}
	protected int getStatusBarHeight(){
		int result = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resId > 0)
			result = getResources().getDimensionPixelSize(resId);
		return result;
	}
	protected void setUsername(){
		TextView nameField = (TextView) findViewById(R.id.username);
		nameField.setText(DAO.getUsername());
	}
	protected void setUpNavDrawer() {
	       if (toolbar != null) {
	           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	           toolbar.setNavigationIcon(R.drawable.ic_drawer);
	           toolbar.setNavigationOnClickListener(new View.OnClickListener() {
	               @Override
	               public void onClick(View v) {
	                   ((DrawerLayout) findViewById(R.id.drawer)).openDrawer(GravityCompat.START);
	               }
	           });
	       }
	       navigationView = (NavigationView) findViewById(R.id.nav);
	       setUsername();
	       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
	            @Override
	            public boolean onNavigationItemSelected(MenuItem menuItem) {
	                menuItem.setChecked(true);
	                switch (menuItem.getItemId()) {
	                    case R.id.settings_menu:
	                        open(SettingsActivity.class);
	                        return true;
	                    case R.id.home_menu:
	                        open(HomeActivity.class);
	                        return true;
	                    case R.id.tv_menu:
	                    	open(TVActivity.class);
	                    	return true;
	                    case R.id.sports_menu:
	                    	open(SportsActivity.class);
	                    	return true;
	                    default:
	                        return true;
	                }
	            }
	        });
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
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);	
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
	}
	@SuppressLint("InlinedApi")
	private void createToolbar(){
		if(buildNumber >= Build.VERSION_CODES.JELLY_BEAN)
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}
	
	@Override
	abstract public <T> void onItemClick(View view, T viewModel);
	

}
