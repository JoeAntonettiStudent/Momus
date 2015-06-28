package com.lggflex.thigpen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewActivity extends ActionBarActivity {
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.list);
		createToolbar();
		String name = getIntent().getStringExtra("name");
	    int resId = getResources().getIdentifier(name.toLowerCase() + "_list", "array", getPackageName());
	    if(resId == 0){
	    	startChatActivity(name);
	    	finish();
	    }else{
	    	int drawableID = getResources().getIdentifier(name.toLowerCase() + "", "drawable", getPackageName());
	    	ImageView image = (ImageView) findViewById(R.id.headerImage);
	    	image.setImageDrawable(getResources().getDrawable(drawableID));
	    	makeList(R.id.main_list, android.R.layout.simple_list_item_1, resId, new OnItemClickListener(){

	    		@Override
	    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    				long arg3) {
	    			TextView text = (TextView) arg1.findViewById(android.R.id.text1);
					startChatActivity(text.getText());
	    		}
			
	    	});
	    	getSupportActionBar().setTitle(name);
	    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    }
	}
	
	public void startChatActivity(CharSequence charSequence){
		Intent i = new Intent(context, ChatActivity.class);
		i.putExtra("name", charSequence);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	protected void createToolbar(){
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
	    finish();
	    return true;

	}
	
	private int getStatusBarHeight(){
		int result = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resId > 0)
			result = getResources().getDimensionPixelSize(resId);
		return result;
	}
	
	protected void makeList(int id, int layout, int data, OnItemClickListener listener){
		ListView list = (ListView) findViewById(id);
		String[] data_items = getResources().getStringArray(data);
		list.setAdapter(new ArrayAdapter<String>(this, layout, data_items));
		if(listener != null)
			list.setOnItemClickListener(listener);
	}
}
