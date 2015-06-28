package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lggflex.thigpen.ListViewActivity;
import com.lggflex.thigpen.R;

public class TelevisionViewTextAdapter extends RecyclerView.Adapter<TelevisionViewTextAdapter.ViewHolder> {
 
    private String[] data;
    private static Context context;
 
    public TelevisionViewTextAdapter(String[] dataSet, Context c) {
        data = dataSet;
        context = c;
    }
    
    public TelevisionViewTextAdapter(ArrayList<String> dataSet){
    	data = ((String[]) dataSet.toArray());
    }
    
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(data[position]);
    }
 
    @Override
    public int getItemCount() {
        return data.length;
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
    	
        private final TextView textView;
 
        public ViewHolder(View v) {
        	super(v);
            v.setOnClickListener(new View.OnClickListener() {
            	
                @Override
                public void onClick(View v) {
                	Intent i = new Intent(context, ListViewActivity.class);
                	i.putExtra("name", textView.getText());
                	context.startActivity(i);
                }
                
            });
            textView = (TextView) v.findViewById(android.R.id.text1);
        }
 
        public TextView getTextView() {
            return textView;
        }
    }
}
