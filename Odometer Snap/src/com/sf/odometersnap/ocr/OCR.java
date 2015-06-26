/*
 * OCR - The main class to handle all OCR processing. This class wraps both filtering the image using the com.sf.odometersnap.image package,
 * as well as calls OCREngine to extract text from the image. When writing an OCR application, this class should be the only one called from
 * either the ocr or image packages, as it handles all of the objects in both
 * 
 * Static Methods:
 * 	- processFromFile: Grab the OCR result from a file path
 * 		- Parameters:
 * 			* String path - The path to the image
 * 			* String userInput (Optional) - The user claim for the text value in the image. This value can be used for matching and
 * 										   verification.
 * 		- Return Type: The value of the image
 * 
 * 	- processFromBitmap: Grab the OCR result from a bitmap
 * 		- Parameters:
 * 			* Bitmap b - The bitmap to process
 * 			* String userInput (Optional) - The user claim for the text value in the image. This value can be used for matching and
 * 										    verification.
 *  
 *  Author: Joseph Antonetti
 *  Last Revision: 5/19/2015
 *  
 *  TODO: processFromFile without userInput
 */

package com.sf.odometersnap.ocr;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.sf.odometersnap.auth.Test;
import com.sf.odometersnap.image.BitmapTools;
import com.sf.odometersnap.image.Filter;
import com.sf.odometersnap.util.StringComparison;
import com.sf.odometersnap.util.Tuple;

public final class OCR {
	
	/*
	 * Public Variables
	 */
	
	public static final String LANGUAGE_ENG = "eng";
	public static final String LANGUAGE_DS = "ds";
	
	/*TODO: processFromFile and processFromBitmap without requiring user input
	
	/*
	 * Converts a path into a bitmap and calls processFromBitmap
	 */
	
	public static String processFromFile(String path, String userInput){
		
		Bitmap picture = BitmapTools.readFromFile(path);
		return processFromBitmap(picture, userInput);
		
	}
	
	public static String processFromBitmap(Bitmap b, String userInput){
		return processFromFilter(Filter.makeAll(b), userInput);
	}
	
	public static String processFromBitmap(Bitmap b){
		return processFromFilter(Filter.makeAll(b));
	}
	
	/*
	 * Find the odometer reading from an array of filters and match it against user input
	 */
	
	private static String processFromFilter(Filter[] filter){
		Bitmap[] outputs = process(filter);
		/*TODO: Matching*/
		return new OCREngine(null, null, -1).getText(outputs[1]);
	}
	
	//TODO: Cleanup
	private static String processFromFilter(Filter[] filter, String userInput){
		
		Log.i(Test.TEST_TAG_NAME, "Running processFromFilter...");
		ArrayList<Tuple<String, String>> results = new ArrayList<Tuple<String, String>>();
		Bitmap[] outputs = process(filter);
		
		for(int i = 0; i < filter.length; i++){
			Log.i(Test.TEST_TAG_NAME, "Running OCR for filter " + filter[i].title());
			results.add(new Tuple<String, String>(filter[i].title() + " Dig", new OCREngine(null, null, -1).getText(outputs[i])));	
			results.add(new Tuple<String, String>(filter[i].title() + " Eng", new OCREngine(OCR.LANGUAGE_ENG, null, -1).getText(outputs[i])));
			results.add(new Tuple<String, String>(filter[i].title() + " Dig2", new OCREngine(OCR.LANGUAGE_DS, OCREngine.WHITELIST_ONLY_NUMBERS, OCREngine.PAGEMODE_SINGLE_WORD).getText(outputs[i])));	
			results.add(new Tuple<String, String>(filter[i].title() + " Eng", new OCREngine(OCR.LANGUAGE_ENG, null,-1).getText(outputs[i])));	
			results.add(new Tuple<String, String>(filter[i].title() + " Dig1", new OCREngine(null, null, OCREngine.PAGEMODE_SINGLE_WORD).getText(outputs[i])));	
			results.add(new Tuple<String, String>(filter[i].title() + " Eng1", new OCREngine(OCR.LANGUAGE_ENG, OCREngine.WHITELIST_ONLY_NUMBERS, OCREngine.PAGEMODE_SINGLE_WORD).getText(outputs[i])));	
			Log.i(Test.TEST_TAG_NAME, "Successfully ran OCR for filter " + filter[i].title());
		}
		
		VotingEngine voter = new VotingEngine(results, userInput);
		voter.getFailureRate();
		voter.printWeights();
		return voter.getResult().first;
		//return pickAnswer(results, userInput);
	}
	
	/*
	 * Private Methods
	 */
	
	private static Bitmap[] process(Filter[] filter){
		Log.v(Test.TEST_TAG_NAME, "Attempting to run filters...");
		int width = filter[0].image().getWidth();
		int height = filter[0].image().getHeight();
		int size = width * height;
		Bitmap[] outputs = new Bitmap[filter.length];
		
		for(int i = 0; i < outputs.length; i++){
			outputs[i] = filter[0].image().copy(Bitmap.Config.ARGB_8888, true);
		}
		Log.i(Test.TEST_TAG_NAME, "Created Output Bitmap Array");
		for(int i = 0; i < filter.length; i++){
			double progress = 0;
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					progress++;
					int[] newColor = filter[i].changePixel(x, y);
					outputs[i].setPixel(x, y, Color.argb(newColor[0], newColor[1], newColor[2], newColor[3]));
					if((progress/size) % 10 == 0)
						Log.i(Test.TEST_TAG_NAME, "" + (progress/size) + "% complete");
				}
			}
			Log.v(Test.TEST_TAG_NAME, "Successfully Ran Filter " + filter[i].title());
		}
		return outputs;
	}
	
	private static String pickAnswer(ArrayList<Tuple<String, String>> results, String userInput){
		
		Log.v(Test.TEST_TAG_NAME, "Running pickAnswer for input " + userInput + ".");
		int min = Integer.MIN_VALUE;
		String bestAnswer = null;
		
		for(Tuple<String, String> r : results){
			Log.v(Test.TEST_TAG_NAME, "   Current Best Value: " + bestAnswer);
			String resultValue = r.second;
			resultValue = resultValue.replace(" ", "");
			Log.v(Test.TEST_TAG_NAME, "   Challenger Value: " + resultValue);
			int compValue = (new StringComparison(resultValue, userInput)).getDifference();
			int difference = Math.max(resultValue.length() - compValue, userInput.length() - compValue);
			Log.v(Test.TEST_TAG_NAME, "    Difference: " + difference);
			if(difference > min){
				bestAnswer = resultValue;
				min = difference;
			}
		}
		
		return bestAnswer;
	}
}
