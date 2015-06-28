package com.lggflex.thigpen.fragment.sportscategory;

import java.util.List;

import android.os.Build;
import android.os.Handler;
import com.lggflex.thigpen.R;

import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SportsCategoryViewAdapter extends RecyclerView.Adapter<SportsCategoryViewHolder> implements View.OnClickListener{
	
	private List<SportsCategoryViewModel> categories;
	private SportsCategoryOnItemClickListener onCategoryClickListener;
	
	public SportsCategoryViewAdapter(List<SportsCategoryViewModel> items){
		categories = items;
	}
	
	public void setOnCategoryClickListener(SportsCategoryOnItemClickListener listener){
		onCategoryClickListener = listener;
	}
	
	@Override
	public SportsCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_category_item, parent, false);
		v.setOnClickListener(this);
		return new SportsCategoryViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(SportsCategoryViewHolder holder, int position){
		SportsCategoryViewModel item = categories.get(position);
		holder.text.setText(item.getTitle());
		if(item.getDrawable() != null){
			holder.image.setImageDrawable(item.getDrawable());
			holder.text.setBackgroundColor(item.getCardColor());
		}else
			holder.image.setImageBitmap(null);
		holder.itemView.setTag(item);
	}
	
	@Override
	public int getItemCount(){
		return categories.size();
	}
	
	@Override
	public void onClick(final View v){
		if(onCategoryClickListener != null)
			onCategoryClickListener.onItemClick(v, (SportsCategoryViewModel) v.getTag());
	}

}