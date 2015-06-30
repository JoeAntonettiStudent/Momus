package com.lggflex.model;

import android.graphics.Color;

public class UserModel {
	
	public String name;
	public int color;
	
	private static String[] colors = {
			"#F44336", "#FFF176",
			"#9C27B0", "#AED581",
			"#2196F3", "#F06292",
			"#388E3C", "#FFF176",
			"#FF5722", "#4DB6AC"
	};
	
	public UserModel(String name){
		this.name = name;
		int random = (int)((Math.random()) * colors.length);
		this.color = Color.parseColor(colors[random]);
	}

}
