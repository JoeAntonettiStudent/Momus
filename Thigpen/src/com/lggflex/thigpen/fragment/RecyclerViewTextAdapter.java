package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import com.lggflex.thigpen.R;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewTextAdapter extends RecyclerView.Adapter<RecyclerViewTextAdapter.ViewHolder> {
 
    private String[] data;
    private static Context context;
    private Fragment fragment;
 
    public RecyclerViewTextAdapter(String[] dataSet, Context c, Fragment f) {
        data = dataSet;
        context = c;
        fragment = f;
    }
    
    public RecyclerViewTextAdapter(ArrayList<String> dataSet){
    	data = ((String[]) dataSet.toArray());
    }
    
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(v, fragment);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(data[position]);
        int drawableID = fragment.getResources().getIdentifier(viewHolder.getTextView().getText().toString().toLowerCase() + "", "drawable", fragment.getActivity().getPackageName());
        if(drawableID != 0){
    		viewHolder.getImageView().setImageDrawable(fragment.getResources().getDrawable(drawableID));
    	}
    }
 
    @Override
    public int getItemCount() {
        return data.length;
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
    	
        private final TextView textView;
        private final ImageView imageView;
        private final Fragment fragment;
 
        public ViewHolder(View v, Fragment f) {
        	super(v);
            v.setOnClickListener(new View.OnClickListener() {
            	
                @SuppressLint("NewApi")
				@Override
                public void onClick(View v) {
                	/*Intent i = new Intent(fragment.getActivity(), ListViewActivity.class);
                	i.putExtra("name", textView.getText());
                	@SuppressWarnings("unchecked")
					ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(fragment.getActivity(), v.findViewById(R.id.imageView), "Sports Header Transition");
                	ActivityCompat.startActivity(fragment.getActivity(), i, transitionActivityOptions.toBundle());*/
                	//context.startActivity(i);
                }
                
            });
            textView = (TextView) v.findViewById(R.id.textView);
            fragment = f;
            imageView = (ImageView) v.findViewById(R.id.imageView);
	    	
        }
 
        public TextView getTextView() {
            return textView;
        }
        
        public ImageView getImageView(){
        	return imageView;
        }
    }
}
