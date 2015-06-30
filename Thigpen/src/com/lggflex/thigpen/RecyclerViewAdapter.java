package com.lggflex.thigpen;

import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


abstract public class RecyclerViewAdapter<VH extends RecyclerView.ViewHolder, VM> extends RecyclerView.Adapter<VH> implements View.OnClickListener{
	
	protected List<VM> items;
	private OnRecyclerViewClickListener onCategoryClickListener;
	
	public RecyclerViewAdapter(List<VM> items){
		this.items = items;
	}
	
	public void setOnCategoryClickListener(OnRecyclerViewClickListener listener){
		onCategoryClickListener = listener;
	}
	
	@Override
	abstract public VH onCreateViewHolder(ViewGroup parent, int viewType);
	
	@Override
	abstract public void onBindViewHolder(VH holder, int position);
	
	@Override
	public int getItemCount(){
		return items.size();
	}
	
	@Override
	public void onClick(final View v){
		if(onCategoryClickListener != null)
			onCategoryClickListener.onItemClick(v, (VM) v.getTag());
	}

}