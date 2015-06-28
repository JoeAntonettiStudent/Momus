package com.lggflex.thigpen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lggflex.thigpen.fragment.HomeScreenMainViewFragment;
import com.lggflex.thigpen.fragment.HomeScreenSportsViewFragment;
import com.lggflex.thigpen.fragment.RecyclerViewFragment;
import com.lggflex.thigpen.fragment.favoritecategory.FavoriteCategoryFragment;
import com.lggflex.thigpen.fragment.sportscategory.SportsCategoryFragment;
import com.lggflex.thigpen.fragment.tvcategory.TVCategoryFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
	
	String tabNames[];
	Fragment[] tabs = {
			new HomeScreenMainViewFragment(),
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
