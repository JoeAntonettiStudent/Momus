package com.lggflex.thigpen.adapter;

import java.util.List;
import com.lggflex.model.CategoryModel;
import com.lggflex.thigpen.holder.CardViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryAdapter extends RecyclerViewAdapter<CardViewHolder, CategoryModel>{

	public CategoryAdapter(List<CategoryModel> items) {
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
		CategoryModel item = items.get(position);
		holder.text.setText(item.getTitle());
		if(item.getDrawable() != null){
			holder.image.setImageDrawable(item.getDrawable());
			holder.text.setBackgroundColor(item.getCardColor());
		}else
			holder.image.setImageBitmap(null);
		holder.itemView.setTag(item);
	}

}