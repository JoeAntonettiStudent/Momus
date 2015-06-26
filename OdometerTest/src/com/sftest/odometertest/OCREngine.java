package com.sftest.odometertest;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OCREngine {
	
	private TessBaseAPI api;
	private String path;
	
	private String TESS_LANGUAGE = "eng";
	private String TESS_WHITELIST = "0123456789.";
	private int TESS_PAGEMODE = 7;
	
	
	public OCREngine(String image_path){
		path = image_path;
		initEngine();
	}
	
	public OCREngine(String image_path, String language){
		path = image_path;
		TESS_LANGUAGE = language;
		initEngine();
	}
	
	public OCREngine(String image_path, String language, int pagemode){
		path = image_path;
		TESS_LANGUAGE = language;
		TESS_PAGEMODE = pagemode;
		initEngine();
	}
	
	public OCREngine(String image_path, int pagemode){
		path = image_path;
		TESS_PAGEMODE = pagemode;
		initEngine();
	}
	
	private void initEngine(){
		api = new TessBaseAPI();	
		api.init(path, TESS_LANGUAGE);
		api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, TESS_WHITELIST);
		api.setPageSegMode(TESS_PAGEMODE);
	}
	
	public String getText(Bitmap input){
		api.setImage(input);
		String text = api.getUTF8Text();
		api.end();
		return text;
	}
	

}
