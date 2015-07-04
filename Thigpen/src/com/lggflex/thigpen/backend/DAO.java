package com.lggflex.thigpen.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class DAO {
	
	public static Context DAOContext;
	public static String TAG = "DAO";
	
	private static HashMap<String, ArrayList<String>> fileDirectory;
	
	private static ArrayList<ChatroomModel> favorites;
	private static ArrayList<ChatroomModel> tv;
	
	public static void initDAO(Context c){
		
		DAOContext = c;
		AssetManager manager = DAOContext.getAssets();
		fileDirectory = new HashMap<String, ArrayList<String>>();
		try {
			for(String asset : manager.list("Files")){
				Log.i(TAG, asset);
				ArrayList<String> rooms = getAssetsForFile(asset);
				fileDirectory.put(asset.toLowerCase(Locale.US).replace(".txt", ""), rooms);
			}
		} catch (IOException e) {
			Log.e(TAG, "Failed to create DAO object");
		}
		initFavorites();
		initTV();
	}
	
	private static void initFavorites(){
		SharedPreferences prefs = DAOContext.getSharedPreferences("FAVORITE_ITEMS", Context.MODE_PRIVATE);
        Map<String, ?> allFavs = prefs.getAll();  
        favorites = new ArrayList<ChatroomModel>();
        for(String key : allFavs.keySet()){
        	if(prefs.getBoolean(key, false))
        		favorites.add(new ChatroomModel(key));
        }
	}
	
	private static void initTV(){
		 tv = new ArrayList<ChatroomModel>();
		 String[] tvShowNames = DAOContext.getResources().getStringArray(R.array.home_screen_television_list);
	     for(final String show : tvShowNames){
	    	 tv.add(new ChatroomModel(show));
	     }
	}
	
	public static ArrayList<ChatroomModel> getTV(){
		return tv;
	}
	
	
	public static ArrayList<ChatroomModel> getFavorites(){
		return favorites;
	}
	 
	public static String getUsername(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAOContext);
		return sharedPref.getString(DAOContext.getString(R.string.pref_username), DAOContext.getString(R.string.default_username));
	}
	
	public static String getLocation(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAOContext);
		return sharedPref.getString(DAOContext.getString(R.string.pref_location), "Cleveland");
	}
	
	public static boolean get(int id, boolean d){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAOContext);
		return sharedPref.getBoolean(DAOContext.getString(id), d);
	}
	
	public static void setUsername(String username){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAOContext);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(DAOContext.getString(R.string.pref_username), username);
		editor.commit();
	}
	
	public static void setLocation(String loc){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAOContext);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(DAOContext.getString(R.string.pref_location), loc);
		editor.commit();
	}
	
	public static ArrayList<String> getStringsForID(String id){
		ArrayList<String> recs = fileDirectory.get(id.toLowerCase(Locale.US));
		if(recs != null)
			return recs;
		recs = new ArrayList<String>();
		recs.add("No Recs Found");
		return recs;
	}
	
	private static ArrayList<String> getAssetsForFile(String name){
		AssetManager manager = DAOContext.getAssets();
		try {
			InputStream input = manager.open("Files/" + name.toLowerCase(Locale.US));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			ArrayList<String> items = new ArrayList<String>();
			String line = "";
			do{
				line = reader.readLine();
				if(line != null)
					items.add(line);
			}while(line != null);
			reader.close();
			return items;
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			Log.e(TAG, "Couldn't open resource for file " + name);
			return null;
		}
		
	}
}