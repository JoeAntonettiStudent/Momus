package com.lggflex.thigpen;

import com.lggflex.model.ChatroomModel;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class RecyclerViewFragment extends Fragment implements OnRecyclerViewClickListener{
	
	protected RecyclerView recyclerView;
	protected RecyclerViewAdapter<?, ?> adapter;
	protected FloatingActionButton floatingActionButton;
	protected int primary, accent, buildNumber;
	protected GridLayoutManager recyclerLayoutManager;
	protected View view;
	
	protected void initRecyclerView(int id, int columns){
		recyclerView = (RecyclerView) view.findViewById(id);
		recyclerLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), columns);
    	recyclerView.setLayoutManager(recyclerLayoutManager);
    	adapter.setOnCategoryClickListener(this);
    	recyclerView.setAdapter(adapter);
	}

	@Override
	public abstract <T> void onItemClick(View view, T viewModel);


}

 