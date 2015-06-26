package com.sf.odometersnap.auth;

import android.graphics.Bitmap;

public class SizeChecker extends Test{
	
	private static final int MIN_WIDTH = 0;
	private static final int MIN_HEIGHT = 0;
	private static final String SIZE_NAME = "Size Check";
	
	public SizeChecker(Bitmap i) {
		super(i, SIZE_NAME);
	}

	@Override
	public boolean run() {
		boolean result;
		if(image == null || image.getWidth() <= MIN_WIDTH || image.getHeight() <= MIN_HEIGHT){
			result = false;
		}
		result = true;
		onPostRun(result);
		return result;
	}

}
