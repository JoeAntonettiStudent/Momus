package com.lggflex.thigpen;

import com.lggflex.thigpen.backend.DAO;
import com.lggflex.thigpen.fragment.RecommendationsFragment;
import com.lggflex.thigpen.fragment.SportsCategoryFragment;
import com.lggflex.thigpen.fragment.TVCategoryFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;

public class TVActivity extends TabbedActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		DAO.initDAO(getBaseContext());
		setContentView(LAYOUT);
		initUIFlourishes(false, 1, 1);
		Fragment[] tabs = {
				new RecommendationsFragment(),
				new SportsCategoryFragment(),
				new TVCategoryFragment()
		};
		initTabs(R.array.television_tabs, tabs);
        setUpNavDrawer();

	}

	@Override
	public <T> void onItemClick(View view, T viewModel) {
		// TODO Auto-generated method stub
		
	}
}