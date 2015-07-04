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
	
	private String title;
	private int primary;
	private int accent;
	
	public ChatroomModel(String t){
		title = t;
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
	
	public int[] getColor(){
		return new int[] {primary, accent};
	}
	
	public static ArrayList<ChatroomModel> makeFromList(ArrayList<String> names){
		ArrayList<ChatroomModel> models = new ArrayList<ChatroomModel>();
		for(String name : names){
			models.add(new ChatroomModel(name));
		}
		return models;
	}
}
