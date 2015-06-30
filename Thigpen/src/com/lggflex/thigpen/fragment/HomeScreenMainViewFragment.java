package com.lggflex.thigpen.fragment;

import java.util.ArrayList;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.ChatActivity;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.RecyclerViewFragment;
import com.lggflex.thigpen.adapter.ChatAdapter;
import com.lggflex.thigpen.adapter.RecommendationsAdapter;
import com.lggflex.thigpen.backend.Recommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeScreenMainViewFragment extends RecyclerViewFragment{
	
    private static final String TAG = "HomeScreenMainViewFragment";
    
    private static String city = "Cleveland";
    
	private static final String EXTRA_TITLE = "com.lggflex.thigpen.extraTitle";
	private static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	private static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
	
	private static ArrayList<ChatroomModel> recommendations;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        view = inflater.inflate(R.layout.fragment_sports_category, container, false);
        view.setTag(TAG);
        
        Recommender myRecommendations = new Recommender(city);
        recommendations = myRecommendations.recommend();
        
		adapter = new RecommendationsAdapter(recommendations);
		initRecyclerView(R.id.categoryRecyclerView, 1);
    
        return view;
    }

	@Override
	public <T> void onItemClick(View view, T m) {
		ChatroomModel viewModel = new ChatroomModel((String) m);
		for(ChatroomModel model : recommendations){
			if(model.getTitle().equals(m))
				viewModel = model;
		}
		Intent i = new Intent(getActivity(), ChatActivity.class);
		i.putExtra("name", viewModel.getTitle());
		i.putExtra(EXTRA_PRIMARY_COLOR, viewModel.getColor()[0]);
		i.putExtra(EXTRA_ACCENT_COLOR, viewModel.getColor()[1]);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getActivity().startActivity(i);
	}
}
