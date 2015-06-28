package com.lggflex.thigpen.fragment;

import com.lggflex.thigpen.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecyclerViewFragment extends Fragment {
 
    protected static final String TAG = "RecyclerViewFragment";
 
    protected RecyclerView recyclerView;
    protected String[] data;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeData();
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        rootView.setTag(TAG);
 
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewTextAdapter(data, getActivity()));
        
        return rootView;
    }

    protected void makeData() {
    	int dataSetSize = 10;
    	data = new String[dataSetSize];
    	for(int i = 0; i < dataSetSize; i++){
    		data[i] = "Item";
    	}
    }
 
}