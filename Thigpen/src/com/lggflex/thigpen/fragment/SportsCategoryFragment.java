package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lggflex.model.CategoryModel;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.SportListActivity;
import com.lggflex.thigpen.adapter.CategoryAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
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
        		/*Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(drawableID)).getBitmap();
        		 Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        	            public void onGenerated(Palette p) {
        	            	applyPalleteToCard(p, drawableID, category);
        	            }
        	        });	*/
        		boolean isRepeat = false;
            	for(CategoryModel model : categories){
            		if(model.getTitle().equals(category))
            			isRepeat = true;
            	}
            	if(!isRepeat)
            		categories.add(new CategoryModel(category, getResources().getDrawable(drawableID), drawableID, getResources().getColor(R.color.primary)));
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
    	int mutedPrimary = pallete.getMutedColor(primary);
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
		Intent i = new Intent(getActivity(), SportListActivity.class);
    	i.putExtra(EXTRA_IMAGE, ((CategoryModel) viewModel).getDrawableID());
		i.putExtra(EXTRA_TITLE, ((CategoryModel) viewModel).getTitle());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.category_image), "Sports Header Transition");
			ActivityCompat.startActivity(getActivity(), i, transitionActivityOptions.toBundle());
		}else{
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getActivity().getApplicationContext().startActivity(i);
		}
	}
}
