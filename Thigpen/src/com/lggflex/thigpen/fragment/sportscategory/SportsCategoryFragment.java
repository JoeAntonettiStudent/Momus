package com.lggflex.thigpen.fragment.sportscategory;

import java.util.ArrayList;
import java.util.List;

import com.lggflex.thigpen.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportsCategoryFragment extends Fragment implements OnItemClickListener{
	
    protected static final String TAG = "SportsCategoryFragment";
    
    private static String packageName;
    
    private static List<SportsCategoryViewModel> categories = new ArrayList<SportsCategoryViewModel>();
    
    private static View view;
 
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
        for(String category : categoryNames){
        	int drawableID = getResources().getIdentifier(category.toLowerCase(), "drawable", packageName);
        	if(drawableID != 0){
        		categories.add(new SportsCategoryViewModel(category, getResources().getDrawable(drawableID)));
        	}else{
        		categories.add(new SportsCategoryViewModel(category, null));
        	}
        }
 
        initCategoryView();
        
        return view;
    }
    
    public void initCategoryView(){
    	RecyclerView categoryView = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);
    	categoryView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(), 2));
    	SportsCategoryViewAdapter adapter = new SportsCategoryViewAdapter(categories);
    	adapter.setOnCategoryClickListener(this);
    	categoryView.setAdapter(adapter);
    }

	@Override
	public void onItemClick(View view, SportsCategoryViewModel viewModel) {
		// TODO Auto-generated method stub
		
	}
}
