/*
 * String Comparison: Utility class to find the difference between two strings
 * 
 * 	Constructor Parameters:
 * 		- String a - The first string to compare
 * 		- String b - The second string to compare
 * 
 * 	Public Functions:
 * 		- getDifference: Finds the difference between two Strings
 * 			- Return Type: An int value for the difference
 *  
 *  Author: Joseph Antonetti
 *  Last Revision: 5/19/2015
 */

package com.sf.odometersnap.util;

public class StringComparison {
	
	String x;
	String y;
	
	public StringComparison(String a, String b){
		x = a;
		y = b;
	}
	
	public int getDifference(){
		int compValue = compareStrings(x, y);
		return Math.max(x.length() - compValue, y.length() - compValue);
	}

	private int compareStrings(String a, String b){
		if(a.length() == 0)
			return 0;
		int result = Math.max(score(a, b, 0, 0), score(a, b, 0, 1));
		if(result == 1)
			return 1 + compareStrings(a.substring(1), b.substring(1));
		if(a.length() >= b.length())
			return compareStrings(a.substring(1), b);
		return compareStrings(a, b.substring(1));
	}
	
	private int score(String a, String b, int x, int y){
		if(y >= 0 && x >= 0 && x < a.length() && y < b.length() && a.charAt(x) == b.charAt(y))
			return 1;
		return 0;
	}
}
