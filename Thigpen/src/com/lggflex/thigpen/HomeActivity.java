package com.lggflex.thigpen;

import com.lggflex.thigpen.backend.DAO;
import com.lggflex.thigpen.ui.SlidingTabLayout;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class HomeActivity extends LollipopActivity implements OnSharedPreferenceChangeListener{
	
	ViewPager pager;
	ViewPagerAdapter pagerAdapter;
	SlidingTabLayout tabLayout;
	Toolbar toolbar;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		
		super.onCreate(savedInstanceState);
		
		DAO.initDAO(getBaseContext());
		
		 PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
		
		setContentView(R.layout.activity_home);

		initUIFlourishes(false, 1, 1);
		
		String[] tabNames = getResources().getStringArray(R.array.home_screen_tabs);
        pagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(), tabNames);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdapter);

        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        tabLayout.setViewPager(pager);
        setUpNavDrawer();

	}

	@Override
	public <T> void onItemClick(View view, T viewModel) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }
   

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		finish();
		startActivity(new Intent(this, HomeActivity.class));
	}
	
}