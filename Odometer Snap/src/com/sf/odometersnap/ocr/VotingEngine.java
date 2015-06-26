package com.sf.odometersnap.ocr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.util.Log;

import com.sf.odometersnap.auth.Test;
import com.sf.odometersnap.util.Tuple;

public class VotingEngine {
	
	private int total_results = 0;
	private int failed_results = 0;
	private HashMap<String, Integer> results_mapping;
	private String userInput;
	
	public VotingEngine(ArrayList<Tuple<String, String>> results, String userInput){
		results_mapping = new HashMap<String, Integer>();
		total_results = results.size();
		for(Tuple<String, String> result : results){
			String value = result.second;
			if(!(value.equals(""))){
				if(results_mapping.get(value) != null)
					results_mapping.put(value, results_mapping.get(value) + 1);
				else
					results_mapping.put(value, 1);
			}else{
				failed_results++;
			}
		}
		this.userInput = userInput;
	}
	
	private double getOdds(String key){
		double number = (double)(results_mapping.get(key));
		double total = (double)(total_results);
		return number/total;
	}
	
	private Tuple<String, Integer> autocorrect(String result){
		String return_result = "";
		if(result.charAt(0) != userInput.charAt(0) && result.charAt(1) == userInput.charAt(0)){
			result = result.substring(1, result.length());
			return_result = return_result + userInput.charAt(0);
		}
		return null;
	}
	
	public Tuple<String, Double> getResult(){
		if(failed_results != total_results){
			ArrayList<String> options = makeOrderedList();
			if(userInput != null){
				if(options.get(0).equals(userInput) || (options.size() > 1 && options.get(1).equals(userInput)))
					return new Tuple<String, Double>(userInput, 0.0);
			}
			return new Tuple<String, Double>(options.get(0), 0.0);
		}else
			return new Tuple<String, Double>("Failure", 0.0);
	}
	
	private ArrayList<String> makeOrderedList(){
		ArrayList<String> ordered_list = new ArrayList<String>();
		while(results_mapping.size() > 0){
			String key = getLargest();
			ordered_list.add(key);
			results_mapping.remove(key);
		}
		return ordered_list;
	}
	
	private String getLargest(){
		Set<String> keys = results_mapping.keySet();
		int biggest_value = Integer.MIN_VALUE;
		String biggest_key = "";
		for(String key : keys){
			int value = results_mapping.get(key);
			if(value > biggest_value){
				biggest_key = key;
				biggest_value = value;
			}
		}
		return biggest_key;
	}
	
	public void printWeights(){
		Set<String> keys = results_mapping.keySet();
		for(String key : keys){
			int value = results_mapping.get(key);
			Log.i(Test.TEST_TAG_NAME, key + " : " + value);
		}
	}
	
	public double getFailureRate(){
		Log.i(Test.TEST_TAG_NAME, "Results Failure Rate: " + 100.00 * ((double)(failed_results)) / ((double)(total_results)) + "%");
		return (double)(failed_results / total_results);
	}

}
