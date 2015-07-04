package com.lggflex.thigpen.backend;

import java.util.ArrayList;

import com.lggflex.model.ChatroomModel;

import android.content.Context;
import android.os.StrictMode;

public class DAO {
	
	public static Context DAOContext;
	public static String TAG = "DAO";
	
	public static void initDAO(Context c){
		
		DAOContext = c;
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		FileDAO.init();
		NetDAO.init();
		SharedPrefsDAO.init();


	}
	
	public static String getUsername(){
		return SharedPrefsDAO.getUsername();
	}
	
	public static String getLocation(){
		return SharedPrefsDAO.getLocation();
	}
	
	public static boolean get(int id, boolean d){
		return SharedPrefsDAO.get(id, d);
	}

	public static ArrayList<ChatroomModel> getFavorites(){
		return SharedPrefsDAO.getFavorites();
	}
	
	public static ArrayList<ChatroomModel> getLiveTV(){
		return NetDAO.getLiveTV();
	}
	
	public static ArrayList<ChatroomModel> getPopularTV(){
		return NetDAO.getPopularTV();
	}
	
	public static ArrayList<String> getStringsForID(String id){
		return FileDAO.getFromDirectory(id);
	}
	
	public static void setUsername(String username){
		SharedPrefsDAO.setUsername(username);
	}
	
	public static void setLocation(String location){
		SharedPrefsDAO.setLocation(location);
	}
}