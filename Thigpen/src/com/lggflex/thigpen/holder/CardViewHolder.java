package com.lggflex.thigpen.holder;

import com.lggflex.thigpen.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewHolder extends RecyclerView.ViewHolder{
	
	public ImageView image;
	public TextView text;

	public CardViewHolder(View itemView) {
		super(itemView);
		image = (ImageView) itemView.findViewById(R.id.category_image);
		text = (TextView) itemView.findViewById(R.id.category_title);
	}
	
}