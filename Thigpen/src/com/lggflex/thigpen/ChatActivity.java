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
	
	private final String SERVER_URL="http://52.26.146.218:3000";
	
	private static int CHAT_LINE_LAYOUT = android.R.layout.simple_list_item_1;
	
	private static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	private static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
	
	String username;
	
	String currentChatroom;
	
	ArrayAdapter<String> chatHistoryAdapter;
	ArrayList<String> chatHistory;

	EditText textEntry;
	
	private Socket socket;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		setupWindowAnimations();
		
		createToolbar();
		isChildOfRoot();
		
		currentChatroom = getIntent().getStringExtra("name");
		setTitle(currentChatroom);
		
		SharedPreferences prefs = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
		username = prefs.getString("username", "Barry Allen");
		
		chatHistory = new ArrayList<String>();
		chatHistoryAdapter = new ArrayAdapter<String>(this, CHAT_LINE_LAYOUT, chatHistory);
		
		makeList(R.id.mainList, android.R.layout.simple_list_item_1, chatHistoryAdapter, null);
		
		fab = (FloatingActionButton) findViewById(R.id.send);
		textEntry = (EditText) findViewById(R.id.entry);
		fab.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sendMessage(textEntry.getText().toString());
			}
			
		});
		
		int primaryColor = getIntent().getIntExtra(EXTRA_PRIMARY_COLOR, -1);
		int accentColor = getIntent().getIntExtra(EXTRA_ACCENT_COLOR, -1);
		themeToColors(primaryColor, accentColor);
		
		configureSocket();
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
