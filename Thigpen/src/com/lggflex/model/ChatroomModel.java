package com.lggflex.model;

import android.graphics.Color;

public class ChatroomModel {
	
	private static String[][] colors = {
			{"#F44336", "#FFF176"},
			{"#9C27B0", "#AED581"},
			{"#2196F3", "#F06292"},
			{"#388E3C", "#FFF176"},
			{"#FF5722", "#4DB6AC"}
	};
	
	private String title;
	private int primary;
	private int accent;
	
	public ChatroomModel(String t){
		title = t;
		int random = (int)((Math.random()) * colors.length);
		primary = Color.parseColor(colors[random][0]);
		accent = Color.parseColor(colors[random][1]);
		
	}
	
	public String getTitle(){
		return title;
	}
	
	public int[] getColor(){
		return new int[] {primary, accent};
	}
}
