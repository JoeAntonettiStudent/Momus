package com.lggflex.thigpen.fragment.favoritecategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lggflex.thigpen.ChatActivity;
import com.lggflex.thigpen.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoriteCategoryFragment extends Fragment implements FavoriteCategoryOnItemClickListener{
	
    protected static final String TAG = "TVCategoryFragment";
    
	private static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	private static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
    
    private List<FavoriteCategoryViewModel> favorites = new ArrayList<FavoriteCategoryViewModel>();
    
    private View view;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //OnCreate Stuff Here
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	view = inflater.inflate(R.layout.fragment_sports_category, container, false);
        view.setTag(TAG);
        
		SharedPreferences prefs = getActivity().getSharedPreferences("FAVORITE_ITEMS", Context.MODE_PRIVATE);
        Map<String, ?> allFavs = prefs.getAll();  
        favorites.clear();
        for(String key : allFavs.keySet()){
        	if(prefs.getBoolean(key, false))
        		favorites.add(new FavoriteCategoryViewModel(key));
        }
 
        initFavoritesView();
        
        return view;
    }
    
    
    public void initFavoritesView(){
    	RecyclerView categoryView = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);
    	categoryView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(), 1));
    	FavoriteCategoryViewAdapter adapter = new FavoriteCategoryViewAdapter(favorites);
    	adapter.setOnCategoryClickListener(this);
    	categoryView.setAdapter(adapter);
    }

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onItemClick(View view, FavoriteCategoryViewModel viewModel) {
		Intent i = new Intent(getActivity(), ChatActivity.class);
		i.putExtra("name", viewModel.getTitle());
		i.putExtra(EXTRA_PRIMARY_COLOR, viewModel.getColor()[0]);
		i.putExtra(EXTRA_ACCENT_COLOR, viewModel.getColor()[1]);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getActivity().startActivity(i);
		getActivity().overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom);
	}
}
