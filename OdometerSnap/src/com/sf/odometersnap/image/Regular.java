package com.sf.odometersnap.image;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Regular extends Filter{
	
	public Regular(Bitmap i){
		super(i, "Regular");
	}

	@Override
	public int[] changePixel(int x, int y) {
		int c = image().getPixel(x, y);
		int[] value = {Color.alpha(c),Color.red(c),Color.green(c),Color.blue(c)};
		return value;
	}

}
