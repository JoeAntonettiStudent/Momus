package com.lggflex.thigpen;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends ActionBarActivity {
	
	private static int CHAT_LINE_LAYOUT = android.R.layout.simple_list_item_1;
	
	ArrayAdapter<String> chatHistoryAdapter;
	ArrayList<String> chatHistory;
	
	Button chatButton;
	EditText textEntry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		createToolbar();
		
		String name = getIntent().getStringExtra("name");
		getSupportActionBar().setTitle(name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		chatHistory = new ArrayList<String>();
		chatHistoryAdapter = new ArrayAdapter<String>(this, CHAT_LINE_LAYOUT, chatHistory);
		
		makeList(R.id.mainList, android.R.layout.simple_list_item_1, new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		chatButton = (Button) findViewById(R.id.send);
		textEntry = (EditText) findViewById(R.id.entry);
		chatButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				addMessage(textEntry.getText().toString());
			}
			
		});
	}
	
	public void addMessage(String message){
		chatHistory.add(message);
		chatHistoryAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
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
	
	protected void makeList(int id, int layout, OnItemClickListener listener){
		ListView list = (ListView) findViewById(id);
		list.setAdapter(chatHistoryAdapter);
		if(listener != null)
			list.setOnItemClickListener(listener);
	}
}
