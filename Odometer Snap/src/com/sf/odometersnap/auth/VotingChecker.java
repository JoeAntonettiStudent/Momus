package com.sf.odometersnap.auth;

import java.util.ArrayList;
import com.sf.odometersnap.ocr.VotingEngine;
import com.sf.odometersnap.util.Tuple;

public class VotingChecker extends Test{

	public VotingChecker(){
		super(null, "Voting Checker");
	}
	
	@Override
	public boolean run() {
		VotingEngine voter = new VotingEngine(generateFakeData(10, 100), null);
		voter.printWeights();
		voter.getFailureRate();
		return false;
	}
	
	private ArrayList<Tuple<String, String>> generateFakeData(int failures, int size){
		ArrayList<Tuple<String, String>> return_data = new ArrayList<Tuple<String, String>>();
		int failures_made = 0;
		for(int i = 0; i < size; i++){
			if(failures_made < failures){
				return_data.add(new Tuple<String, String>("", ""));
				failures_made++;
			}else{
				double random = Math.random();
				if(random < 0.3)
					return_data.add(new Tuple<String, String>("", "12345"));
				else if(random < 0.7)
					return_data.add(new Tuple<String, String>("", "67890"));
				else 
					return_data.add(new Tuple<String, String>("", "Run Barry Run"));
			}
		}
		return return_data;
	}

}
