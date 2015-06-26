/*
 * OCRFilterTask - Child of AsyncTask which offloads all of the OCR into a different thread, as to not mess up the UI. 
 * 
 * NOTE: OCRFilterTask is parameterized with a Bitmap, a String, and a String. While it takes an array of Bitmaps, only the first
 * 		 is actually used. 
 * 
 * 	Constructor Parameters:
 * 		- Context c: Base Context. OCRFilterTask creates a progress dialog to measure it's progress, so a context is needed to display it
 * 		- String u (Optional): User input string used for verification
 *  
 *  Author: Joseph Antonetti
 *  Last Revision: 5/19/2015
 *  
 *  TODO: Remove need for context
 *  TODO: Solve "Out of Memory" error
 */

package com.sf.odometersnap.ui;

import android.content.Context;
import android.graphics.Bitmap;
import com.sf.odometersnap.ocr.OCR;
import com.sf.odometersnap.util.ProgressSpinnerTask;

public class OCRFilterTask extends ProgressSpinnerTask<Bitmap, String, String>{
	
	private String userInput;
	
	public OCRFilterTask(Context c){
		super(c, "Processing Image","Running OCR...");
		userInput = null;
	}

	public OCRFilterTask(Context c, String u){
		super(c, "Processing Image", "Running OCR...");
		userInput = u;
	}

	@Override
	protected String doInBackground(Bitmap... params) {
		//TODO: Better way to handle Bitmaps here
		if(userInput == null)
			return OCR.processFromBitmap(params[0]);
		else
			return OCR.processFromBitmap(params[0], userInput);
	}

	protected void onPostExecute(String result){
		super.onPostExecute(result);
		showText("OCR Result", result);
	}
}