package com.lggflex.thigpen.backend;

import java.util.ArrayList;

import com.lggflex.model.ChatroomModel;

public class Recommender {
	
	private String location;
	private ArrayList<ChatroomModel> recommended;
	
	public Recommender(String location){
		this.location = location;
		recommended = new ArrayList<ChatroomModel>();
	}
	
	public ArrayList<ChatroomModel> recommend(){
		recommended.add(new ChatroomModel("Arrow"));
		return recommended;
	}

}
