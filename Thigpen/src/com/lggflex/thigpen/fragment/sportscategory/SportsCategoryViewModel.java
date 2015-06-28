package com.lggflex.thigpen.fragment.sportscategory;

import android.graphics.drawable.Drawable;

public class SportsCategoryViewModel {
	
	private String title;
	private Drawable drawable;
	
	public SportsCategoryViewModel(String t, Drawable id){
		title = t;
		drawable = id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public Drawable getDrawable(){
		return drawable;
	}

}
