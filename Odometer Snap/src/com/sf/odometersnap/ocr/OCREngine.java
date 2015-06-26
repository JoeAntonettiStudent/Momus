/*
 * OCR Engine - Interfaces with Tesseract to perform OCR on a bitmap.
 * 
 * NOTE: This class should not be called anywhere in the program besides the OCR class. This is simply a wrapper for TessBaseAPI,
 * because the ORA project requires multiple settings to be preset on TessBaseAPI. 
 * 
 *  Constructors Parameters:
 *  	- String lang: Creates an OCR Engine with specified lang. For example, OCREngine("eng") creates an OCR Engine using English
 *  	- String whitelist: Specifies which characters Tesseract can return
 *  	- int pagemode: Specifies the "psm" command line argument, which tells Tesseract how to process the 
 *  
 *  Public Methods:
 *  	- getText(Bitmap input): Returns the text in a specified bitmap
 *  
 *  Static Variables:
 *  	- PAGEMODE_SINGLE_WORD: Tells Tesseract to view the image as a single word
 *  	- WHITELIST_ONLY_NUMBERS: Limits recognized characters to 0-9.
 *  
 *  Author: Joseph Antonetti
 *  Last Revision: 5/19/2015
 */

package com.sf.odometersnap.ocr;

import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.sf.odometersnap.auth.Test;

public class OCREngine {
	
	/*
	 * Static Variables
	 */

	public static final int PAGEMODE_SINGLE_WORD = 7;
	public static final String WHITELIST_ONLY_NUMBERS = "0123456789,";
	
	/*
	 * Class Constructors
	 */

	public OCREngine(String lang, String whitelist, int pagemode){
		if(lang != null)
			language = lang;
		this.whitelist = whitelist;
		if(pagemode != -1)
			this.pagemode = pagemode;
		initEngine();
	}
	
	/*
	 * Public Methods
	 */
	
	public String getText(Bitmap input){
		
		api.setImage(input);
		String text = api.getUTF8Text();
		api.end();
		if(text.equals("")){
			return "No Text Detected!";
		}
		for(char c : text.toCharArray()){
			if(!(WHITELIST_ONLY_NUMBERS.contains("" + c)))
				text = text.replace("" + c, "");
		}
		Log.v(Test.TEST_TAG_NAME, "   OCR Result: " + text);
		return text;
	}
	
	
	/*
	 * Private Variables
	 */
	
	private TessBaseAPI api;
	private String path = com.sf.odometersnap.ui.MainActivity.DATA_PATH;
	private String language = OCR.LANGUAGE_DS;
	private String whitelist = "";
	private int pagemode = -1;
	
	/*
	 * Private Methods
	 */
	
	private OCREngine initEngine(){
		api = new TessBaseAPI();	
		api.init(path, language);
		if(whitelist != null && !whitelist.equals("")){
			api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, whitelist);
		}
		if(pagemode != -1){
			api.setPageSegMode(pagemode);
		}
		return this;
	}
}