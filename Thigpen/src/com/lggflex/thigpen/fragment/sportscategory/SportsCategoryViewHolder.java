package com.lggflex.thigpen.fragment.sportscategory;

import com.lggflex.thigpen.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SportsCategoryViewHolder extends RecyclerView.ViewHolder{
	
	public ImageView image;
	public TextView text;

	public SportsCategoryViewHolder(View itemView) {
		super(itemView);
		image = (ImageView) itemView.findViewById(R.id.category_image);
		text = (TextView) itemView.findViewById(R.id.category_title);
	}
	
}