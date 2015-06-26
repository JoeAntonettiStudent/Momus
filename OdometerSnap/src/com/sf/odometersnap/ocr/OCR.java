package com.sf.odometersnap.ocr;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.sf.odometersnap.image.BitmapTools;
import com.sf.odometersnap.image.Filter;

/*
 * Static class to hold all OCR interactions. Should be the only interaction any class outside the ocr package has with 
 * ocr
 */

public class OCR {
	
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
	
	/*
	 * Find the odometer reading from a bitmap and match it against user input
	 */
	
	public static String processFromBitmap(Bitmap image, String userInput){
		
		ArrayList<OCRResult> results = new ArrayList<OCRResult>();
		Filter[] filters = Filter.makeAll(image);
		if(image == null){
			Log.e("Error", "Image passed to processFromBitmap is null");
			return "ERROR: FAILED";
		}
		int width = image.getWidth();
		int height = image.getHeight();
		Bitmap[] outputs = new Bitmap[filters.length];
		
		for(int i = 0; i < outputs.length; i++){
			outputs[i] = filters[0].image().copy(Bitmap.Config.ARGB_8888, true);
		}
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
					for(int i = 0; i < filters.length; i++){
						int[] newColor = filters[i].changePixel(x, y);
						outputs[i].setPixel(x, y, Color.argb(newColor[0], newColor[1], newColor[2], newColor[3]));
					}
			}
		}
		
		for(int i = 0; i < filters.length; i++){
			/*TODO: Refine this shit*/
			results.add(new OCRResult(filters[i].title() + " Dig", new OCREngine().getText(outputs[i])));	
			results.add(new OCRResult(filters[i].title() + " Eng", new OCREngine(OCR.LANGUAGE_ENG).getText(outputs[i])));
			results.add(new OCRResult(filters[i].title() + " Dig2", new OCREngine(OCR.LANGUAGE_DS, OCREngine.WHITELIST_ONLY_NUMBERS, OCREngine.PAGEMODE_SINGLE_WORD).getText(outputs[i])));	
			results.add(new OCRResult(filters[i].title() + " Eng", new OCREngine(OCR.LANGUAGE_ENG).getText(outputs[i])));	
			results.add(new OCRResult(filters[i].title() + " Dig1", new OCREngine(OCREngine.PAGEMODE_SINGLE_WORD).getText(outputs[i])));	
			results.add(new OCRResult(filters[i].title() + " Eng1", new OCREngine(OCR.LANGUAGE_ENG, OCREngine.WHITELIST_ONLY_NUMBERS, OCREngine.PAGEMODE_SINGLE_WORD).getText(outputs[i])));	
		}
		
		return pickAnswer(results, userInput);
	}
	
	/*
	 * Pick the best answer from an ArrayList of results and a string representing the user's input for the odometer's value
	 */
	
	private static String pickAnswer(ArrayList<OCRResult> results, String userInput){
		
		int min = Integer.MIN_VALUE;
		String bestAnswer = null;
		
		for(OCRResult r : results){
			String resultValue = r.result;
			resultValue.replace(" ", "");
			int compValue = compareStrings(resultValue, userInput);
			int difference = Math.max(resultValue.length() - compValue, userInput.length() - compValue);
			Log.i("Result", "Original: " + userInput + ", Found: " + resultValue + ", Diff = " + difference);
			if(difference < min){
				bestAnswer = resultValue;
				min = difference;
			}
		}
		
		return bestAnswer;
	}
	
	private static int compareStrings(String a, String b){
		if(a.length() == 0)
			return 0;
		int result = Math.max(score(a, b, 0, 0), score(a, b, 0, 1));
		if(result == 1)
			return 1 + compareStrings(a.substring(1), b.substring(1));
		if(a.length() >= b.length())
			return compareStrings(a.substring(1), b);
		return compareStrings(a, b.substring(1));
	}
	
	private static int score(String a, String b, int x, int y){
		if(y >= 0 && x >= 0 && x < a.length() && y < b.length() && a.charAt(x) == b.charAt(y))
			return 1;
		return 0;
	}

}
