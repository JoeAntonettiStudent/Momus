package com.lggflex.thigpen.fragment;

import com.lggflex.thigpen.adapter.RecyclerViewAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

public abstract class RecyclerViewFragment extends Fragment implements OnRecyclerViewClickListener{
	
	protected RecyclerView recyclerView;
	public RecyclerViewAdapter<?, ?> adapter;
	protected FloatingActionButton floatingActionButton;
	protected int primary, accent, buildNumber;
	public GridLayoutManager recyclerLayoutManager;
	protected View view;
	
	protected static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	protected static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
	protected static final String EXTRA_IMAGE = "com.lggflex.thigpen.extraImage";
	protected static final String EXTRA_TITLE = "com.lggflex.thigpen.extraTitle";
	
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

 