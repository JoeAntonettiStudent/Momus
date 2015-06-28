package com.lggflex.thigpen.fragment.tvcategory;

import com.lggflex.thigpen.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TVCategoryViewHolder extends RecyclerView.ViewHolder {
	
    public TextView title;
    public ImageView color;
    
    public TVCategoryViewHolder(View v) { 	
    	super(v);
        title = (TextView) v.findViewById(R.id.tv_title);
        color = (ImageView) v.findViewById(R.id.tv_row_color);
    }

}