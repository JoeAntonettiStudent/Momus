package com.lggflex.thigpen;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.lggflex.model.ChatItemModel;
import com.lggflex.model.UserModel;
import com.lggflex.thigpen.adapter.ChatAdapter;
import com.lggflex.thigpen.backend.DAO;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)

public class ChatActivity extends LollipopActivity {
	
	//Intent Extras
	private static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	private static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
	private static final String EXTRA_CHATROOM_NAME = "name";
	
	//Internal State Variables
	private String username;
	private String currentChatroom;
	private boolean isFavoriteChatroom;
	private SharedPreferences favoriteChatroomsPreferences;
	private static HashMap<String, UserModel> currentUserMap;
	
	//Server Variables
	private final String SERVER_URL="http://52.25.255.158:3000";
	private Socket socket;
	
	//UI Variables
	ArrayAdapter<String> chatHistoryAdapter;
	ArrayList<ChatItemModel> chatHistory;
	private EditText textEntry;
	
	private static final int FAVORITED_ICON = R.drawable.ic_favorite_white_36dp;
	private static final int UNFAVORITED_ICON = R.drawable.ic_favorite_border_white_36dp;

	MenuItem favoriteButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		setupChatButton();
		//Get Intent Extras
		currentChatroom = getIntent().getStringExtra(EXTRA_CHATROOM_NAME);
		int primaryColor = getIntent().getIntExtra(EXTRA_PRIMARY_COLOR, -1);
		int accentColor = getIntent().getIntExtra(EXTRA_ACCENT_COLOR, -1);
		
		initUIFlourishes(true, primaryColor, accentColor);
		makeChildOfRoot();
		setTitle(currentChatroom);
		
		username = DAO.getUsername();
		Log.i("ChatActivity", username);
		
		favoriteChatroomsPreferences = getSharedPreferences("FAVORITE_ITEMS", Context.MODE_PRIVATE);
		isFavoriteChatroom = favoriteChatroomsPreferences.getBoolean(currentChatroom, false);
		
		currentUserMap = new HashMap<String, UserModel>();
		currentUserMap.put(username, new UserModel(username));
		
		chatHistory = new ArrayList<ChatItemModel>();
		
		adapter = new ChatAdapter(chatHistory);
		initRecyclerView(R.id.chat, 1);
		recyclerLayoutManager.setReverseLayout(true);
		textEntry = (EditText) findViewById(R.id.entry );
		
		themeToColors();
		
		configureSocket();
		
		ArrayList<String> titles = DAO.getStringsForID("titles");
		int rand = (int) (Math.random() * titles.size());
		addMessage("Momus", "Momus: " + titles.get(rand));
	}
	
	private void setupChatButton(){
		
		final Animation FABInit = AnimationUtils.loadAnimation(this, R.anim.fab_in);
		FABInit.setDuration(750);
		FABInit.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		final Animation FABSpin = AnimationUtils.loadAnimation(this, R.anim.fab_spin);
		FABSpin.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		
		initFAB(R.id.send, new OnClickListener(){

			@Override
			public void onClick(View v) {
				v.startAnimation(FABSpin);
				sendMessage(textEntry.getText().toString());
				textEntry.setText("");
			}
			
		});
		
		floatingActionButton.startAnimation(FABInit);
	}

	public void sendMessage(String message){
		socket.emit("messageSent", message);
	}
	
	public void addMessage(String username, String message){
		chatHistory.add(new ChatItemModel(username, message, getUserColor(username)));
		adapter.notifyDataSetChanged();
	}
	
	private int getUserColor(String username){
		UserModel model = currentUserMap.get(username);
		if(model != null)
			return model.color;
		else{
			model = new UserModel(username);
			currentUserMap.put(username, model);
			return model.color;
		}
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
					socket.emit("changeRoom", currentChatroom + DAO.getLocation());
				}
				
			});
			socket.on("update", new Emitter.Listener() {
				
				@Override
				public void call(final Object... args) {
					Log.i("SERVER", (String)args[0] + ": " + (String)args[1]);
					runOnUiThread(new Runnable() {
					     @Override
					     public void run() {
					    	 addMessage((String)args[0], (String)args[1]);
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

	@Override
	public <T> void onItemClick(View view, T viewModel) {
		// TODO Auto-generated method stub
		
	}
}
