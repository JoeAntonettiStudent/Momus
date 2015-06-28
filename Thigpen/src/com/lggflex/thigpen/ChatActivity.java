package com.lggflex.thigpen;

import java.net.URISyntaxException;
import java.util.ArrayList;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class ChatActivity extends ToolbarActivity {
	
	private static int CHAT_LINE_LAYOUT = android.R.layout.simple_list_item_1;
	
	private static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	private static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
	
	String username;
	
	String currentChatroom;
	
	ArrayAdapter<String> chatHistoryAdapter;
	ArrayList<String> chatHistory;
	
	FloatingActionButton chatButton;
	EditText textEntry;
	
	boolean isReallyInArrow = false;
	
	private final String SERVER_URL="http://52.26.146.218:3000";
	private Socket socket;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		setupWindowAnimations();
		
		createToolbar();
		isChildOfRoot();
		
		currentChatroom = getIntent().getStringExtra("name");
		
		setTitle(currentChatroom);
		
		if(currentChatroom.equals("Arrow")){
			currentChatroom = "Cleveland Browns";
			setTitle("Arrow");
		}
		
		int primaryColor = getIntent().getIntExtra(EXTRA_PRIMARY_COLOR, -1);
		if(primaryColor != -1){
			toolbar.setBackgroundColor(primaryColor);
		}
		
		SharedPreferences prefs = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
		username = prefs.getString("username", "Barry Allen");
		
		chatHistory = new ArrayList<String>();
		chatHistoryAdapter = new ArrayAdapter<String>(this, CHAT_LINE_LAYOUT, chatHistory);
		
		makeList(R.id.mainList, android.R.layout.simple_list_item_1, chatHistoryAdapter, new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Log.i("ChatActivity", "Clicked Message");
			}
			
		});
		
		chatButton = (FloatingActionButton) findViewById(R.id.send);
		textEntry = (EditText) findViewById(R.id.entry);
		chatButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sendMessage(textEntry.getText().toString());
			}
			
		});
		
		int accentColor = getIntent().getIntExtra(EXTRA_ACCENT_COLOR, -1);
		if(accentColor != -1){
			chatButton.setBackgroundTintList(ColorStateList.valueOf(accentColor));
		}
		
		configureSocket();
	}
	
	 @TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void setupWindowAnimations() {
	        Explode explode = new Explode();
	        explode.setDuration(2000);
	        getWindow().setEnterTransition(explode);
	}
	
	public void sendMessage(String message){
		socket.emit("messageSent", message);
	}
	
	public void addMessage(String message){
		chatHistory.add(message);
		chatHistoryAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		socket.disconnect();
		socket.close();
		socket.off("update");
		socket.off(Socket.EVENT_CONNECT);
	}
	
	public void configureSocket(){
		try {
			Log.i("ChatActivity", "Registering Socket Actions");
			socket = IO.socket(SERVER_URL);
			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){

				@Override
				public void call(Object... arg0) {
					Log.i("SERVER", "Connected");
					socket.emit("userConnected", username);
					socket.emit("changeRoom", currentChatroom);
				}
				
			});
			socket.on("update", new Emitter.Listener() {
				
				@Override
				public void call(final Object... args) {
					Log.i("SERVER", (String)args[0] + ": " + (String)args[1]);
					runOnUiThread(new Runnable() {
					     @Override
					     public void run() {
					    	 addMessage((String)args[0] + ": " + (String)args[1]);
					    }
					});
					
				}
			});
			socket.connect();
			Log.i("SERVER", "Connecting...");
		} catch (URISyntaxException e) {
			Log.i("Server", e.toString());
		}
	}
}
