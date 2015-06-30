package com.lggflex.thigpen;

import com.lggflex.thigpen.R;
import com.lggflex.thigpen.ui.SlidingTabLayout;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class HomeActivity extends LollipopActivity {
	
	ViewPager pager;
	ViewPagerAdapter pagerAdapter;
	SlidingTabLayout tabLayout;
	Toolbar toolbar;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUIFlourishes(false, 1, 1);
		
		String[] tabNames = getResources().getStringArray(R.array.home_screen_tabs);
        pagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(), tabNames);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdapter);

        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
      //  tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent_light);
            }
        });
        tabLayout.setViewPager(pager);
 
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
	
	@Override
	public <T> void onItemClick(View view, T viewModel) {
		// TODO Auto-generated method stub
		
	}
	
}