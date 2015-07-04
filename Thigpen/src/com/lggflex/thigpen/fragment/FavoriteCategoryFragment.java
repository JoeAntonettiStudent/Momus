package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.ChatActivity;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.adapter.ListAdapter;
import com.lggflex.thigpen.backend.DAO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoriteCategoryFragment extends RecyclerViewFragment{
	
    protected static final String TAG = "FavoriteCategoryFragment";
    private List<ChatroomModel> favorites = new ArrayList<ChatroomModel>();
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	view = inflater.inflate(R.layout.fragment_list, container, false);
        view.setTag(TAG);
        
		SharedPreferences prefs = getActivity().getSharedPreferences("FAVORITE_ITEMS", Context.MODE_PRIVATE);
        Map<String, ?> allFavs = prefs.getAll();  
        favorites.clear();
        for(String key : allFavs.keySet()){
        	if(prefs.getBoolean(key, false))
        		favorites.add(new ChatroomModel(key));
        }
 
        adapter = new ListAdapter(favorites);
        if(DAO.get(R.string.pref_twocolumn, false))
        	initRecyclerView(R.id.content, 2);
        else
        	initRecyclerView(R.id.content, 1);
        
        return view;
    }

	@Override
	public <T> void onItemClick(View view, T viewModel) {
		Intent i = new Intent(getActivity(), ChatActivity.class);
		i.putExtra("name", ((ChatroomModel) viewModel).getTitle());
		i.putExtra(EXTRA_PRIMARY_COLOR, ((ChatroomModel) viewModel).getColor()[0]);
		i.putExtra(EXTRA_ACCENT_COLOR, ((ChatroomModel) viewModel).getColor()[1]);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getActivity().startActivity(i);
	}
}
