package com.lggflex.thigpen.adapter;

import java.util.List;

import com.lggflex.model.ChatItemModel;
import com.lggflex.thigpen.R;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends RecyclerViewAdapter<ChatViewHolder, ChatItemModel>{
	
	private static final int CHAT_ITEM_LAYOUT = R.layout.chat_item_layout;
	
	private Context context;

	public ChatAdapter(List<ChatItemModel> items, Context c) {
		super(items);
		context = c;
	}

	@Override
	public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(CHAT_ITEM_LAYOUT, parent, false);
		v.setOnClickListener(this);
		return new ChatViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ChatViewHolder holder, int position) {
		ChatItemModel item = items.get(items.size() - position - 1);
		holder.userField.setText(item.user);
		holder.messageField.setText(item.message);
		GradientDrawable shape = (GradientDrawable) holder.messageField.getBackground();
		shape.setColor(item.color);
		setAnimation(holder.container, position);
	}
	
	private void setAnimation(View viewToAnimate, int position){
			Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
			animation.setDuration(750);
			viewToAnimate.startAnimation(animation);
			
	}

}

class ChatViewHolder extends RecyclerView.ViewHolder{
	
	public TextView userField;
	public TextView messageField;
	public LinearLayout container;

	public ChatViewHolder(View itemView) {
		super(itemView);
		userField = (TextView) itemView.findViewById(R.id.chat_user);
		messageField = (TextView) itemView.findViewById(R.id.chat_message);
		container = (LinearLayout) itemView.findViewById(R.id.chat_container);
	} 
	
}
