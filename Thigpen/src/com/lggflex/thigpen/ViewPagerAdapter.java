package com.lggflex.thigpen;

import com.lggflex.thigpen.fragment.FavoriteCategoryFragment;
import com.lggflex.thigpen.fragment.RecommendationsFragment;
import com.lggflex.thigpen.fragment.SportsCategoryFragment;
import com.lggflex.thigpen.fragment.TVCategoryFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
	
	String tabNames[];
	Fragment[] tabs = {
			new RecommendationsFragment(),
			new SportsCategoryFragment(),
			new TVCategoryFragment(),
			new FavoriteCategoryFragment()
	};
    
    public ViewPagerAdapter(FragmentManager manager, String names[]) {
        super(manager);
        this.tabNames = names;
    }
 
    @Override
    public Fragment getItem(int position) {
    	return tabs[position];
    }
 
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
