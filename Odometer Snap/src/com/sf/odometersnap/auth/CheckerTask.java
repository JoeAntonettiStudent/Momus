package com.sf.odometersnap.auth;

import android.content.Context;
import com.sf.odometersnap.util.ProgressSpinnerTask;

public class CheckerTask extends ProgressSpinnerTask<Test, String, Boolean>{
	
	String test_name;
	
	public CheckerTask(Context c){
		super(c, "Running Test","Scanning Image...");
	}

	@Override
	protected Boolean doInBackground(Test... params) {
		for(Test test : params){
			test_name = test.name;
			return test.run();
		}
		return true;
	}

	public void onPostRun(Boolean result){
		super.onPostExecute(result);
		if(result)
			showText("Passed Test", test_name + " passed.");
		else
			showText("Failed Test", test_name + " failed.");
	}
}