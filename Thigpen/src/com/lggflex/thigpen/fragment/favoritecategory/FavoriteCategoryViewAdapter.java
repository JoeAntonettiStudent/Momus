package com.lggflex.thigpen.fragment.favoritecategory;

import java.util.List;

import com.lggflex.thigpen.R;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FavoriteCategoryViewAdapter extends RecyclerView.Adapter<FavoriteCategoryViewHolder> implements View.OnClickListener{
	
	private List<FavoriteCategoryViewModel> shows;
	private FavoriteCategoryOnItemClickListener onCategoryClickListener;
	
	public FavoriteCategoryViewAdapter(List<FavoriteCategoryViewModel> items){
		shows = items;
	}
	
	public void setOnCategoryClickListener(FavoriteCategoryOnItemClickListener listener){
		onCategoryClickListener = listener;
	}
	
	@Override
	public FavoriteCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_category_item, parent, false);
		v.setOnClickListener(this);
		return new FavoriteCategoryViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(FavoriteCategoryViewHolder holder, int position){
		FavoriteCategoryViewModel item = shows.get(position);
		holder.title.setText(item.getTitle());
		holder.itemView.setTag(item);
		GradientDrawable shape = (GradientDrawable) holder.color.getDrawable();
		shape.setColor(item.getColor()[0]);
	}
	
	@Override
	public int getItemCount(){
		return shows.size();
	}
	
	@Override
	public void onClick(final View v){
		if(onCategoryClickListener != null)
			onCategoryClickListener.onItemClick(v, (FavoriteCategoryViewModel) v.getTag());
	}

}