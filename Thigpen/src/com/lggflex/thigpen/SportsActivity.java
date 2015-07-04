package com.lggflex.thigpen;

import java.util.ArrayList;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.backend.DAO;
import com.lggflex.thigpen.fragment.RecommendationsFragment;
import com.lggflex.thigpen.fragment.SimpleListFragment;
import com.lggflex.thigpen.fragment.SportsCategoryFragment;
import com.lggflex.thigpen.fragment.TVCategoryFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;

public class SportsActivity extends TabbedActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		super.onCreate(savedInstanceState);
		setContentView(LAYOUT);
		initUIFlourishes(false, 1, 1);
		String[] tabNames = getResources().getStringArray(R.array.home_screen_sports_list);
		Fragment[] tabs = new Fragment[tabNames.length];
		for(int i = 0; i < tabs.length; i++){
			tabs[i] = new SportFragment();
			((SportFragment) tabs[i]).setItems(ChatroomModel.makeFromList(DAO.getStringsForID(tabNames[i])));
		}
		initTabs(R.array.home_screen_sports_list, tabs);
        setUpNavDrawer();

	}

	@Override
	public <T> void onItemClick(View view, T viewModel) {
		// TODO Auto-generated method stub
		
	}
}

class SportFragment extends SimpleListFragment{
	
	private ArrayList<ChatroomModel> items;
	
	public void setItems(ArrayList<ChatroomModel> items){
		this.items = items;
	}

	@Override
	protected ArrayList<ChatroomModel> getItems() {
		return items;
	}
	
}