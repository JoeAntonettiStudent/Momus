package com.lggflex.thigpen;

import java.util.ArrayList;

import com.lggflex.model.CategoryModel;
import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.backend.DAO;
import com.lggflex.thigpen.backend.NetDAO;
import com.lggflex.thigpen.fragment.RecommendationsFragment;
import com.lggflex.thigpen.fragment.SimpleListFragment;
import com.lggflex.thigpen.fragment.SportsCategoryFragment;
import com.lggflex.thigpen.fragment.TVCategoryFragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;

public class TVActivity extends TabbedActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);
		initUIFlourishes(false, 1, 1);
		
		
		Fragment[] tabs = {
				new PopularFragment(),
				new LiveFragment()
		};
		initTabs(R.array.television_tabs, tabs);
        setUpNavDrawer();
	}

	@Override
	public <T> void onItemClick(View view, T viewModel) {
		// TODO Auto-generated method stub
		
	}
}

class LiveFragment extends SimpleListFragment{

	@Override
	protected ArrayList<ChatroomModel> getItems() {
		return NetDAO.getLiveTV();
	}
	
}

class PopularFragment extends SimpleListFragment{

	@Override
	protected ArrayList<ChatroomModel> getItems() {
		return NetDAO.getPopularTV();
	}
	
}