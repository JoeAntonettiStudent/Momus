package com.lggflex.thigpen;

import java.util.ArrayList;

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
import com.lggflex.thigpen.backend.FileDAO;
import com.lggflex.thigpen.backend.SharedPrefsDAO;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class LoginActivity extends Activity {
	
	public static LoginButton loginButton;
	public static CallbackManager callbackManager;
	public static Spinner locationSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DAO.initDAO(getBaseContext());
		SharedPrefsDAO.init();
		boolean firstRun = SharedPrefsDAO.get("logged in", false);
		if(firstRun)
			goHome();
		
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		
		callbackManager = CallbackManager.Factory.create();
		
		setContentView(R.layout.activity_login);
		
		//locationSpinner = (Spinner)findViewById(R.id.locations_spinner);
		DAO.initDAO(getBaseContext());
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DAO.getStringsForID("locations"));
		//locationSpinner.setAdapter(adapter);
		
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
	                            	 DAO.setUsername(object.get("name").toString());
	                 		        showLocationPicker();
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
		
		Button createAccountButton = (Button) findViewById(R.id.create_account);
		createAccountButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				promptForAccount();
			}
			
		});
		
	}
	
	public void promptForAccount(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter a Username");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        String text = input.getText().toString();
		        DAO.setUsername(text);
		        showLocationPicker();
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		builder.show();
	}
	
	private void showLocationPicker(){
		FileDAO.init();
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select a City");
		final ArrayList<String> cities = DAO.getStringsForID("locations");
		String[] locs = new String[cities.size()];
		for(int i = 0; i < cities.size(); i++){
			locs[i] = cities.get(i);
		}
		builder.setItems(locs, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DAO.setLocation(cities.get(which));
				dialog.dismiss();
				SharedPrefsDAO.set("logged in", true);
				goHome();
			}
			
		});
		builder.show();
    }
	
	public void createLocalAccount(String name, String location){
		SharedPrefsDAO.setUsername(name);
		SharedPrefsDAO.setLocation(location);
		
	}
	
	public void goHome(){
		Intent home = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(home);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
