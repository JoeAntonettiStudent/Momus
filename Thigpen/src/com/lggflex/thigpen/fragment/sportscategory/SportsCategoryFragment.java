package com.lggflex.thigpen.fragment.sportscategory;

import java.util.ArrayList;
import java.util.List;
import com.lggflex.pallete.PalleteTools;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.SportListActivity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportsCategoryFragment extends Fragment implements SportsCategoryOnItemClickListener{
	
    protected static final String TAG = "SportsCategoryFragment";
    
	private static final String EXTRA_IMAGE = "com.lggflex.thigpen.extraImage";
	private static final String EXTRA_TITLE = "com.lggflex.thigpen.extraTitle";
    
    private static String packageName;
    
    private List<SportsCategoryViewModel> categories = new ArrayList<SportsCategoryViewModel>();
    
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
        
        packageName = getActivity().getPackageName();
        
        String[] categoryNames = getResources().getStringArray(R.array.home_screen_sports_list);
        for(final String category : categoryNames){
        	final int drawableID = getResources().getIdentifier(category.toLowerCase(), "drawable", packageName);
        	if(drawableID != 0){
        		Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(drawableID)).getBitmap();
        		 Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        	            public void onGenerated(Palette p) {
        	            	applyPalleteToCard(p, drawableID, category);
        	            }
        	        });	
        	}else{
        		categories.add(new SportsCategoryViewModel(category, null, 0, 0));
        	}
        }
 
        initCategoryView();
        
        return view;
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void applyPalleteToCard(Palette pallete, int drawableID, String name){
    	int primary = getResources().getColor(R.color.primary);
    	int mutedPrimary = pallete.getMutedColor(primary);
		categories.add(new SportsCategoryViewModel(name, getResources().getDrawable(drawableID), drawableID, mutedPrimary));
    }
    
    public void initCategoryView(){
    	RecyclerView categoryView = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);
    	categoryView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(), 2));
    	SportsCategoryViewAdapter adapter = new SportsCategoryViewAdapter(categories);
    	adapter.setOnCategoryClickListener(this);
    	adapter.notifyDataSetChanged();
    	categoryView.setAdapter(adapter);
    }

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onItemClick(View view, SportsCategoryViewModel viewModel) {
    	Intent i = new Intent(getActivity(), SportListActivity.class);
    	i.putExtra(EXTRA_IMAGE, viewModel.getDrawableID());
		i.putExtra(EXTRA_TITLE, viewModel.getTitle());
		ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.category_image), "Sports Header Transition");
    	ActivityCompat.startActivity(getActivity(), i, transitionActivityOptions.toBundle());
	}
}
