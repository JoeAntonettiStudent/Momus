package com.lggflex.model;

import android.graphics.drawable.Drawable;

public class CategoryModel {

	private String title;
	private Drawable drawable;
	private int drawableID;
	private int cardColor;
	
	public CategoryModel(String t, Drawable draw, int drawID, int color){
		title = t;
		drawable = draw;
		drawableID = drawID;
		cardColor = color;
	}
	
	public String getTitle(){
		return title;
	}
	
	public Drawable getDrawable(){
		return drawable;
	}
	
	public int getDrawableID(){
		return drawableID;
	}
	
	public int getCardColor(){
		return cardColor;
	}

}
