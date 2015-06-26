package com.sf.odometersnap.auth;

import com.sf.odometersnap.ui.TestActivity;

import android.graphics.Bitmap;
import android.util.Log;

abstract public class Test{
	
	public final static String TEST_TAG_NAME = "TEST";
	public String name;
	public Bitmap image;
	
	public Test(Bitmap i, String name){
		this.image = i;
		this.name = name;
	}
	
	abstract public boolean run();
	
	public void onPostRun(Boolean result){
		if(!result)
			Log.i(TEST_TAG_NAME, "Failed test " + name);
		else
			Log.i(TEST_TAG_NAME, "Passed test " + name);
	}
	
	public static boolean testAll(Bitmap i){
		Test[] tests = makeAll(i);
		for(Test t : tests){
			if(!(t.run()))
				return false;
		}
		return true;
	}
	
	public static Test[] makeAll(Bitmap b){
		return new Test[]{
			new SizeChecker(b)
		};
	}
	
	public static boolean runTest(Bitmap image, int test_id){
		switch(test_id){
			case TestActivity.TEST_ACCURACY:
				return new AccuracyChecker(image).run();
			case TestActivity.TEST_SIZE:
				return new SizeChecker(image).run();
			case TestActivity.TEST_WHITESPACE:
				return new WhitespaceChecker(image).run();
			case TestActivity.TEST_STATS:
				return testAll(image);
			case TestActivity.TEST_MATRIX:
				return new MatrixChecker().run();
			case TestActivity.TEST_VOTING:
				return new VotingChecker().run();
		}
		Log.i(TEST_TAG_NAME, "Invalid Test ID: " + test_id);
		return false;
	}

}