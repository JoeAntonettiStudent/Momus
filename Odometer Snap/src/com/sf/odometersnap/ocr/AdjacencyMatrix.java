package com.sf.odometersnap.ocr;

import com.sf.odometersnap.util.DisjointSets;

public class AdjacencyMatrix {
	
	private static DisjointSets adjacency_matrix;
	
	public static void init(){
		adjacency_matrix = new DisjointSets(11);
		
	}
	
	private int getKey(char key){
		if(key == '.')
			return 10;
		return Integer.parseInt("" + key);
	}
	
}
