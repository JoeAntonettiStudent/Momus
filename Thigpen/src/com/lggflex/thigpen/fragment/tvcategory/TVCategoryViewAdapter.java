package com.lggflex.thigpen.fragment.tvcategory;

import java.util.List;

import com.lggflex.thigpen.R;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TVCategoryViewAdapter extends RecyclerView.Adapter<TVCategoryViewHolder> implements View.OnClickListener{
	
	private List<TVCategoryViewModel> shows;
	private TVCategoryOnItemClickListener onCategoryClickListener;
	
	public TVCategoryViewAdapter(List<TVCategoryViewModel> items){
		shows = items;
	}
	
	public void setOnCategoryClickListener(TVCategoryOnItemClickListener listener){
		onCategoryClickListener = listener;
	}
	
	@Override
	public TVCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_category_item, parent, false);
		v.setOnClickListener(this);
		return new TVCategoryViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(TVCategoryViewHolder holder, int position){
		TVCategoryViewModel item = shows.get(position);
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
			onCategoryClickListener.onItemClick(v, (TVCategoryViewModel) v.getTag());
	}

}