package com.lggflex.thigpen.fragment.sportscategory;

import java.util.List;

import android.os.Handler;
import com.lggflex.thigpen.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportsCategoryViewAdapter extends RecyclerView.Adapter<CategoryViewHolder> implements View.OnClickListener{
	
	private List<SportsCategoryViewModel> categories;
	private OnItemClickListener onCategoryClickListener;
	
	public SportsCategoryViewAdapter(List<SportsCategoryViewModel> items){
		categories = items;
	}
	
	public void setOnCategoryClickListener(OnItemClickListener listener){
		onCategoryClickListener = listener;
	}
	
	@Override
	public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_category_item, parent, false);
		v.setOnClickListener(this);
		return new CategoryViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(CategoryViewHolder holder, int position){
		SportsCategoryViewModel item = categories.get(position);
		holder.text.setText(item.getTitle());
		if(item.getDrawable() != null)
			holder.image.setImageDrawable(item.getDrawable());
		else
			holder.image.setImageBitmap(null);
		holder.itemView.setTag(item);
	}
	
	@Override
	public int getItemCount(){
		return categories.size();
	}
	
	@Override
	public void onClick(final View v){
		if(onCategoryClickListener != null) {
			new Handler().postDelayed(new Runnable() {
	                @Override 
	                public void run() {
	                    onCategoryClickListener.onItemClick(v, (SportsCategoryViewModel) v.getTag());
	                }
			}, 200);
		}
	}

}