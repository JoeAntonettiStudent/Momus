package com.sf.odometersnap.auth;

import android.graphics.Bitmap;
import android.graphics.Color;

public class WhitespaceChecker extends Test{
	
	private static int WHITE_THRESHOLD = 230;
	private static double WHITE_PERCENT = 0.2;
	private static String WHITE_NAME = "Whitespace Check";
	
	public WhitespaceChecker(Bitmap i){
		super(i, WHITE_NAME);
	}

	@Override
	public boolean run(){
		boolean result;
		int qualifying_pixel_count = 0;
		for(int x = 0; x < image.getWidth(); x++){
			for(int y = 0; y < image.getHeight(); y++){
				int rgba_pixel = image.getPixel(x, y);
				int curr_red = Color.red(rgba_pixel);
				int curr_green = Color.green(rgba_pixel);
				int curr_blue = Color.blue(rgba_pixel);
				if(curr_red >= WHITE_THRESHOLD && curr_green >= WHITE_THRESHOLD && curr_blue >= WHITE_THRESHOLD){
					qualifying_pixel_count++;
				}
			}
		}

		if(qualifying_pixel_count >= (image.getWidth() * image.getHeight() * WHITE_PERCENT)){
			result = false;
		}
		result = true;
		onPostRun(result);
		return result;
	}
}