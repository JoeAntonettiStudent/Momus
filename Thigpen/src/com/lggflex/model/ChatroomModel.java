package com.lggflex.model;

import java.util.ArrayList;

import com.lggflex.thigpen.R;
import com.lggflex.thigpen.backend.DAO;

import android.graphics.Color;

public class ChatroomModel {
	
	private static String[][] colors = {
			{"#EF5350", "#FFF176"},
			{"#AB47BC", "#AED581"},
			{"#42A5F5", "#F06292"},
			{"#66BB6A", "#FFF176"},
			{"#FF7043", "#4DB6AC"}
	};
	
	private String title, description;
	private int primary, accent;
	
	public ChatroomModel(String t, String d){
		title = t;
		description = d;
		int random = (int)((Math.random()) * colors.length);
		if(DAO.get(R.string.pref_multicolored, true)){
			primary = Color.parseColor(colors[random][0]);
			accent = Color.parseColor(colors[random][1]);
		}else{
			primary = DAO.DAOContext.getResources().getColor(R.color.primary);
			accent = DAO.DAOContext.getResources().getColor(R.color.accent);
		}
	}
	
	public ChatroomModel(String t){
		title = t;
		description = "";
		int random = (int)((Math.random()) * colors.length);
		if(DAO.get(R.string.pref_multicolored, true)){
			primary = Color.parseColor(colors[random][0]);
			accent = Color.parseColor(colors[random][1]);
		}else{
			primary = DAO.DAOContext.getResources().getColor(R.color.primary);
			accent = DAO.DAOContext.getResources().getColor(R.color.accent);
		}
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int[] getColor(){
		return new int[] {primary, accent};
	}
	
	public static ArrayList<ChatroomModel> makeFromList(ArrayList<String> entries){
		ArrayList<ChatroomModel> models = new ArrayList<ChatroomModel>();
		for(String entry : entries){
			if(entry.length() > 0){
				String name = entry.substring(0, entry.indexOf('\t'));
				String des = entry.substring(entry.indexOf('\t') + 1, entry.length());
				models.add(new ChatroomModel(name, des));
			}
		}
		if(models.size() == 0){
			models.add(new ChatroomModel("Loading..."));
		}
		return models;
	}
}
