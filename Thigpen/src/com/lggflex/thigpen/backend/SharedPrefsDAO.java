package com.lggflex.thigpen.backend;

import java.util.ArrayList;
import java.util.Map;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsDAO {
	
	private static ArrayList<ChatroomModel> favorites;
	
	public static void init(){
		SharedPreferences prefs = DAO.DAOContext.getSharedPreferences("FAVORITE_ITEMS", Context.MODE_PRIVATE);
        Map<String, ?> allFavs = prefs.getAll();  
        favorites = new ArrayList<ChatroomModel>();
        for(String key : allFavs.keySet()){
        	if(prefs.getBoolean(key, false))
        		favorites.add(new ChatroomModel(key));
        }
	}
	
	public static ArrayList<ChatroomModel> getFavorites(){
		return favorites;
	}
	 
	public static String getUsername(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAO.DAOContext);
		return sharedPref.getString(DAO.DAOContext.getString(R.string.pref_username), DAO.DAOContext.getString(R.string.default_username));
	}
	
	public static String getLocation(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAO.DAOContext);
		return sharedPref.getString(DAO.DAOContext.getString(R.string.pref_location), "Cleveland");
	}
	
	public static boolean get(int id, boolean d){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAO.DAOContext);
		return sharedPref.getBoolean(DAO.DAOContext.getString(id), d);
	}
	
	public static boolean get(String id, boolean d){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAO.DAOContext);
		return sharedPref.getBoolean(id, d);
	}
	
	public static void setUsername(String username){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAO.DAOContext);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(DAO.DAOContext.getString(R.string.pref_username), username);
		editor.commit();
	}
	
	public static void set(String id, boolean data){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAO.DAOContext);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean(id, data);
		editor.commit();
	}
	
	public static void setLocation(String loc){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(DAO.DAOContext);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(DAO.DAOContext.getString(R.string.pref_location), loc);
		editor.commit();
	}

}
