package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lggflex.thigpen.ChatActivity;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.SportListActivity;
import com.lggflex.thigpen.fragment.favoritecategory.FavoriteCategoryOnItemClickListener;
import com.lggflex.thigpen.fragment.favoritecategory.FavoriteCategoryViewAdapter;
import com.lggflex.thigpen.fragment.favoritecategory.FavoriteCategoryViewModel;
import com.lggflex.thigpen.fragment.sportscategory.SportsCategoryOnItemClickListener;
import com.lggflex.thigpen.fragment.sportscategory.SportsCategoryViewAdapter;
import com.lggflex.thigpen.fragment.sportscategory.SportsCategoryViewModel;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeScreenMainViewFragment extends Fragment implements SportsCategoryOnItemClickListener{
	
    private static final String TAG = "HomeScreenMainViewFragment";
    
    private static String city = "Cleveland";
    
	private static final String EXTRA_TITLE = "com.lggflex.thigpen.extraTitle";
	private static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	private static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
    
    private List<SportsCategoryViewModel> sports = new ArrayList<SportsCategoryViewModel>();
    private List<FavoriteCategoryViewModel> favorites = new ArrayList<FavoriteCategoryViewModel>();
 
    private View view;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        view = inflater.inflate(R.layout.card, container, false);
        view.setTag(TAG);
        
        String packageName = getActivity().getPackageName();
        
        String[] categoryNames = getResources().getStringArray(R.array.home_screen_sports_list);
        for(final String category : categoryNames){
        	final int drawableID = getResources().getIdentifier(category.toLowerCase(), "drawable", packageName);
        	if(drawableID != 0){
        		/*Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(drawableID)).getBitmap();
        		 Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        	            public void onGenerated(Palette p) {
        	            	applyPalleteToCard(p, drawableID, category);
        	            }
        	        });	*/
            	boolean isRepeat = false;
            	for(SportsCategoryViewModel model : sports){
            		if(model.getTitle().equals(category))
            			isRepeat = true;
            	}
            	if(!isRepeat)
            		sports.add(new SportsCategoryViewModel(category, getResources().getDrawable(drawableID), drawableID, getResources().getColor(R.color.primary)));
        	}else{
        		sports.add(new SportsCategoryViewModel(category, null, 0, 0));
        	}
        }
 
        initCategoryView();
   
        SharedPreferences prefs = getActivity().getSharedPreferences("FAVORITE_ITEMS", Context.MODE_PRIVATE);
        Map<String, ?> allFavs = prefs.getAll();  
        favorites.clear();
        for(String key : allFavs.keySet()){
        	if(prefs.getBoolean(key, false))
        		favorites.add(new FavoriteCategoryViewModel(key));
        }

        initFavs();
    
        return view;
    }


	public void initFavs(){
		RecyclerView categoryView = (RecyclerView) view.findViewById(R.id.favoritesRecycler);
		categoryView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(), 1));
		FavoriteCategoryViewAdapter adapter = new FavoriteCategoryViewAdapter(favorites);
		adapter.setOnCategoryClickListener(new FavoriteCategoryOnItemClickListener(){

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
		});
		
		categoryView.setAdapter(adapter);
	}
	
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void applyPalleteToCard(Palette pallete, int drawableID, String name){
    	int primary = getResources().getColor(R.color.primary);
    	int mutedPrimary = pallete.getVibrantColor(primary);
    	boolean isRepeat = false;
    	for(SportsCategoryViewModel model : sports){
    		if(model.getTitle().equals(name))
    			isRepeat = true;
    	}
    	if(!isRepeat)
    		sports.add(new SportsCategoryViewModel(name, getResources().getDrawable(drawableID), drawableID, mutedPrimary));
    }
    
    public void initCategoryView(){
    	RecyclerView categoryView = (RecyclerView) view.findViewById(R.id.recyclerView);
    	categoryView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(), 4));
    	SportsCategoryViewAdapter adapter = new SportsCategoryViewAdapter(sports);
    	adapter.setOnCategoryClickListener(this);
    	adapter.notifyDataSetChanged();
    	categoryView.setAdapter(adapter);
    }

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onItemClick(View view, SportsCategoryViewModel viewModel) {
		Intent i = new Intent(getActivity(), ChatActivity.class);
		i.putExtra("name", viewModel.getTitle());
		i.putExtra(EXTRA_PRIMARY_COLOR, viewModel.getCardColor());
		i.putExtra(EXTRA_ACCENT_COLOR, viewModel.getCardColor());
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getActivity().startActivity(i);
	}
}
