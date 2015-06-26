package com.lggflex.thigpen.fragment;

import com.lggflex.thigpen.R;

public class HomeScreenSportsViewFragment extends RecyclerViewFragment{
	
	@Override
	protected void makeData(){
		data = getResources().getStringArray(R.array.home_screen_sports_list);
	}

}
