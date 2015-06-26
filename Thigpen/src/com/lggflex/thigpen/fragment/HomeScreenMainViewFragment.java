package com.lggflex.thigpen.fragment;

import com.lggflex.thigpen.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeScreenMainViewFragment extends Fragment{
	
    private static final String TAG = "HomeScreenMainViewFragment";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.card, container, false);
        rootView.setTag(TAG);
        return rootView;
    }
}
