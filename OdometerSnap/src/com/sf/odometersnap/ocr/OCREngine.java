package com.sf.odometersnap.ocr;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.sf.odometersnap.MainActivity;

public class OCREngine {
	
	/*
	 * Static Variables
	 */

	public static final int PAGEMODE_SINGLE_WORD = 7;
	public static final String WHITELIST_ONLY_NUMBERS = "0123456789";
	public static final String TRAINING_PATH = MainActivity.DATA_PATH;
	
	/*
	 * Class Constructors
	 */
	
	public OCREngine(){
		initEngine();
	}
	
	public OCREngine(String lang){
		language = lang;
		initEngine();
	}
	
	public OCREngine(int pagemode){
		this.pagemode = pagemode;
		initEngine();
	}
	
	public OCREngine(String lang, String whitelist){
		language = lang;
		this.whitelist = whitelist;
		initEngine();
	}
	
	public OCREngine(String lang, String whitelist, int pagemode){
		language = lang;
		this.whitelist = whitelist;
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
		return text;
	}
	
	
	/*
	 * Private Variables
	 */
	
	private TessBaseAPI api;
	private String path = TRAINING_PATH;
	private String language = OCR.LANGUAGE_DS;
	private String whitelist = "";
	private int pagemode = -1;
	
	/*
	 * Private Methods
	 */
	
	private OCREngine initEngine(){
		api = new TessBaseAPI();	
		api.init(path, language);
		if(!whitelist.equals("")){
			api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, whitelist);
		}
		if(pagemode != -1){
			api.setPageSegMode(pagemode);
		}
		return this;
	}
}