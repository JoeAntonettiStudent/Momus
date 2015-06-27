package com.lggflex.thigpen;

import com.lggflex.thigpen.ui.SlidingTabLayout;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends ActionBarActivity {
	
	ViewPager pager;
	ViewPagerAdapter pagerAdapter;
	SlidingTabLayout tabLayout;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		if(Build.VERSION.SDK_INT >= 21)
			setStatusBarColor();
		
		String[] tabNames = getResources().getStringArray(R.array.home_screen_tabs);
        pagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(), tabNames);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdapter);

        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
      //  tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent);
            }
        });
        tabLayout.setViewPager(pager);
 
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void setStatusBarColor(){
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings)
			return true;
		return super.onOptionsItemSelected(item);
	}
	public int getStatusBarHeight() {
	    int result = 0;
	    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	        result = getResources().getDimensionPixelSize(resourceId);
	    }
	    return result;
	}
	
}