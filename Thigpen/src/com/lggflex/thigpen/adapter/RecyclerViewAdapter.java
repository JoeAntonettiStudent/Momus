package com.lggflex.thigpen.adapter;

import java.util.List;

import com.lggflex.thigpen.R;
import com.lggflex.thigpen.fragment.OnRecyclerViewClickListener;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


abstract public class RecyclerViewAdapter<VH extends RecyclerView.ViewHolder, VM> extends RecyclerView.Adapter<VH> implements View.OnClickListener{
	
	protected List<VM> items;
	private OnRecyclerViewClickListener onCategoryClickListener;
	
	protected static final int CARD_LAYOUT = R.layout.category_item;
	protected static final int LIST_LAYOUT = R.layout.list_item;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void onClick(final View v){
		if(onCategoryClickListener != null)
			onCategoryClickListener.onItemClick(v, (VM) v.getTag());
	}

}