package com.lggflex.thigpen.adapter;

import java.util.List;
import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.holder.CardViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecommendationsAdapter extends RecyclerViewAdapter<CardViewHolder, ChatroomModel>{

	public RecommendationsAdapter(List<ChatroomModel> items) {
		super(items);
	}

	@Override
	public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(CARD_LAYOUT, parent, false);
		v.setOnClickListener(this);
		return new CardViewHolder(v);
	}

	@Override
	public void onBindViewHolder(CardViewHolder holder, int position) {
		ChatroomModel item = items.get(position);
		holder.text.setText(item.getTitle());
		holder.image.setBackgroundColor(item.getColor()[0]);
		holder.itemView.setTag(item.getTitle());
	}

}