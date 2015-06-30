package com.lggflex.thigpen.adapter;

import java.util.List;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.holder.ListViewHolder;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListAdapter extends RecyclerViewAdapter<ListViewHolder, ChatroomModel>{

	public ListAdapter(List<ChatroomModel> items){
		super(items);
	}
	
	@Override
	public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		View v = LayoutInflater.from(parent.getContext()).inflate(LIST_LAYOUT, parent, false);
		v.setOnClickListener(this);
		return new ListViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ListViewHolder holder, int position) {
		ChatroomModel item = items.get(position);
		holder.title.setText(item.getTitle());
		holder.itemView.setTag(item);
		GradientDrawable shape = (GradientDrawable) holder.color.getDrawable();
		shape.setColor(item.getColor()[0]);
	}

}