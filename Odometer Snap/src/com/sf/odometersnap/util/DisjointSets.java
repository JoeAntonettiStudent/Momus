package com.sf.odometersnap.util;

public class DisjointSets {
	
	private int[] ds_array;
	
	public DisjointSets(int count){
		ds_array = new int[count];
		for(int index = 0; index < count; index++)
			ds_array[index] = -1;
	}
	
	private int find(int index){
		if(ds_array[index] == -1)
			return index;
		return (ds_array[index] = find(ds_array[index]));
	}
	
	public void union(int index_a, int index_b){
		ds_array[find(index_a)] = index_b;
	}
	
	public boolean inSameSet(int index_a, int index_b){
		if(find(index_a) == find(index_b))
			return true;
		return false;
	}
}
