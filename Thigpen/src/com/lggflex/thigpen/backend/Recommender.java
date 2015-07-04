package com.lggflex.thigpen.backend;

import java.util.ArrayList;

import com.lggflex.model.ChatroomModel;

public class Recommender {
	
	private String location;
	
	public Recommender(String location){
		this.location = location;
	}
	
	public ArrayList<ChatroomModel> recommend(){
		ArrayList<String> rawRecommendations = DAO.getStringsForID(location);
		return ChatroomModel.makeFromList(rawRecommendations);
	}

}
