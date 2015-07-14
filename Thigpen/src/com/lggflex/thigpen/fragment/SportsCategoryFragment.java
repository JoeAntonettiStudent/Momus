package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lggflex.model.CategoryModel;
import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.ChatActivity;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.SportListActivity;
import com.lggflex.thigpen.adapter.CategoryAdapter;
import com.lggflex.thigpen.backend.DAO;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportsCategoryFragment extends RecyclerViewFragment{
	
    protected static final String TAG = "SportsCategoryFragment";
    private static String packageName;
    private List<CategoryModel> categories = new ArrayList<CategoryModel>();
    private int mutedPrimary = getResources().getColor(R.color.primary);
    private int accent = getResources().getColor(R.color.accent);
    
 
    @SuppressWarnings("deprecation")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	view = inflater.inflate(R.layout.fragment_category, container, false);
        view.setTag(TAG);
        
        packageName = getActivity().getPackageName();
        
        String[] categoryNames = getResources().getStringArray(R.array.home_screen_sports_list);
        for(final String category : categoryNames){
        	final int drawableID = getResources().getIdentifier(category.toLowerCase(Locale.ENGLISH), "drawable", packageName);
        	if(drawableID != 0){
        		if(DAO.get(R.string.pref_pallete, false)){
        			Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(drawableID)).getBitmap();
        			Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        	            public void onGenerated(Palette p) {
        	            	applyPalleteToCard(p, drawableID, category);
        	            }
        			});	
        		}else{
        			boolean isRepeat = false;
        			for(CategoryModel model : categories){
        				if(model.getTitle().equals(category))
        					isRepeat = true;
        			}
        			if(!isRepeat)
        				categories.add(new CategoryModel(category, getResources().getDrawable(drawableID), drawableID, getResources().getColor(R.color.primary)));
        		}
        	}else{
        		categories.add(new CategoryModel(category, null, 0, 0));
        	}
        }
 
        adapter = new CategoryAdapter(categories);
        initRecyclerView(R.id.categoryRecyclerView, 2);
        
        return view;
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void applyPalleteToCard(Palette pallete, int drawableID, String name){
    	int primary = getResources().getColor(R.color.primary);
    	mutedPrimary = pallete.getMutedColor(primary);
    	accent = pallete.getLightVibrantColor(primary);
    	boolean isRepeat = false;
    	for(CategoryModel model : categories){
    		if(model.getTitle().equals(name))
    			isRepeat = true;
    	}
    	if(!isRepeat)
    		categories.add(new CategoryModel(name, getResources().getDrawable(drawableID, null), drawableID, mutedPrimary));
    }

	@SuppressLint("NewApi")
	@Override
	public <T> void onItemClick(View view, T viewModel) {
		Intent i = new Intent(getActivity(), ChatActivity.class);
		i.putExtra("name", ((CategoryModel) viewModel).getTitle());
		i.putExtra(EXTRA_PRIMARY_COLOR, mutedPrimary);
		i.putExtra(EXTRA_ACCENT_COLOR, accent);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getActivity().startActivity(i);
	}
}
