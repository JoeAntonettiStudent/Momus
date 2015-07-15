package com.lggflex.thigpen;

import com.lggflex.thigpen.ui.SlidingTabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public abstract class TabbedActivity extends LollipopActivity{
	
	protected ViewPager pager;
	protected ViewPagerAdapter pagerAdapter;
	protected SlidingTabLayout tabLayout;
	
	protected final int LAYOUT = R.layout.activity_tabbed;
	
	protected void initTabs(int tabNames, Fragment[] tabs){
		
		String[] names = getResources().getStringArray(tabNames);
		
        pagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(), names, tabs);
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(pagerAdapter);

        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        tabLayout.setViewPager(pager);
	}


	@Override
	abstract public <T> void onItemClick(View view, T viewModel);

}

class ViewPagerAdapter extends FragmentStatePagerAdapter{
	
	String tabNames[];
	Fragment[] tabs;
	
    public ViewPagerAdapter(FragmentManager manager, String names[], Fragment[] tabs) {
        super(manager);
        this.tabNames = names;
        this.tabs = tabs;
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
