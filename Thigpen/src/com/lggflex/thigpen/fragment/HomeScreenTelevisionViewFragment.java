package com.lggflex.thigpen.fragment;

import com.lggflex.thigpen.R;

public class HomeScreenTelevisionViewFragment extends HomeScreenSportsViewFragment{
	
	@Override
	protected void makeData(){
		data = getResources().getStringArray(R.array.home_screen_television_list);
	}

}
