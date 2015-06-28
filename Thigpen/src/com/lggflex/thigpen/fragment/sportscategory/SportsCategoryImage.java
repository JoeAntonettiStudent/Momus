package com.lggflex.thigpen.fragment.sportscategory;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SportsCategoryImage extends ImageView{
	
	public SportsCategoryImage(Context context){
		super(context);
	}
	
	public SportsCategoryImage(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public SportsCategoryImage(Context context, AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		setMeasuredDimension(width, width);
	}

}
