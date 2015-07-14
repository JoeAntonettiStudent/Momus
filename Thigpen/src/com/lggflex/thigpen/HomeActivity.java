package com.lggflex.thigpen;

import com.lggflex.thigpen.backend.DAO;
import com.lggflex.thigpen.backend.DAOTask;
import com.lggflex.thigpen.backend.NetDAO;
import com.lggflex.thigpen.backend.SearchTask;
import com.lggflex.thigpen.fragment.FavoriteCategoryFragment;
import com.lggflex.thigpen.fragment.RecommendationsFragment;
import com.lggflex.thigpen.fragment.RecyclerViewFragment;
import com.lggflex.thigpen.fragment.SportsCategoryFragment;
import com.lggflex.thigpen.fragment.TVCategoryFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class HomeActivity extends TabbedActivity implements OnSharedPreferenceChangeListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
		setContentView(LAYOUT);
		initUIFlourishes(false, 1, 1);
		RecyclerViewFragment[] tabs = {
				new RecommendationsFragment(),
				new SportsCategoryFragment(),
				new TVCategoryFragment(),
				new FavoriteCategoryFragment()
		};
		initTabs(R.array.home_screen_tabs, tabs);
		String[] tabNames = getResources().getStringArray(R.array.home_screen_tabs);
		
        setUpNavDrawer();
		DAO.initDAO(getBaseContext());
        new DAOTask((RecyclerViewFragment[]) tabs).execute(null, null, null);
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


