package com.lggflex.thigpen.holder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CategoryImage extends ImageView{
	
	public CategoryImage(Context context){
		super(context);
	}
	
	public CategoryImage(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public CategoryImage(Context context, AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = getMeasuredWidth();
		setMeasuredDimension(width, width);
	}

}
