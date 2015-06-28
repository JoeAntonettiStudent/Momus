package com.lggflex.thigpen.fragment;

import com.lggflex.thigpen.R;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeScreenTelevisionViewFragment extends HomeScreenSportsViewFragment{
	
	@Override
	protected void makeData(){
		data = getResources().getStringArray(R.array.home_screen_television_list);
	}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    	
	        View rootView = inflater.inflate(R.layout.fragment_television_view, container, false);
	        rootView.setTag(TAG);
	 
	        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
	        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	        recyclerView.setAdapter(new TelevisionViewTextAdapter(data, getActivity()));
	        
	        return rootView;
	    }

}
