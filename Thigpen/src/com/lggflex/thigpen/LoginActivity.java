package com.lggflex.thigpen;

import org.json.JSONObject;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lggflex.thigpen.backend.DAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LoginActivity extends Activity {
	
	public static LoginButton loginButton;
	public static CallbackManager callbackManager;
	public static Spinner locationSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences prefs = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
		boolean alreadyLoggedIn = prefs.getBoolean("completed", false);
		
		if(alreadyLoggedIn){
			Intent home = new Intent(this, HomeActivity.class);
			startActivity(home);
		}
		
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		
		callbackManager = CallbackManager.Factory.create();
		
		setContentView(R.layout.activity_login);
		
		locationSpinner = (Spinner)findViewById(R.id.locations_spinner);
		DAO.initDAO(getBaseContext());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DAO.getStringsForID("locations"));
		locationSpinner.setAdapter(adapter);
		
		loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setReadPermissions("user_friends");
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>(){

			@Override
			public void onSuccess(LoginResult result) {
				 GraphRequest request = GraphRequest.newMeRequest(
	                        result.getAccessToken(),
	                        new GraphRequest.GraphJSONObjectCallback() {
	                            @Override
	                            public void onCompleted(JSONObject object, GraphResponse response) {
	                            	
	                            	Log.v("Facebook", object.toString());
	                            	Log.v("Facebook", response.toString());
	                            	save(object.get("name").toString(), locationSpinner.getSelectedItem().toString());
	                            }
	                        });
	                Bundle parameters = new Bundle();
	                parameters.putString("fields", "id,name,email,gender, birthday");
	                request.setParameters(parameters);
	                request.executeAsync();
			}

			@Override
			public void onCancel() {
				Log.w("Facebook", "Cancelled");
			}

			@Override
			public void onError(FacebookException error) {
				Log.e("Facebook", error.toString());
			}
			
		});
		
	}
	
	public void save(String name, String location){
		DAO.setLocation(location);
		DAO.setUsername(name);
		SharedPreferences prefs = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("completed", true);
		editor.commit();
		Intent home = new Intent(this.getApplicationContext(), HomeActivity.class);
		startActivity(home);
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
