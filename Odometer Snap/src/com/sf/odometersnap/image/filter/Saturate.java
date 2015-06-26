package com.sf.odometersnap.image.filter;

import com.sf.odometersnap.image.Filter;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Saturate extends Filter{
	
	public Saturate(Bitmap i){
		super(i, "Saturate");
	}

	@Override
	public int[] changePixel(int x, int y) {
		int c = image().getPixel(x, y);
		float[] hsv = {0, 0, 0};
		Color.colorToHSV(c, hsv);
		hsv[1] = hsv[1] * 2;
		c = Color.HSVToColor(Color.alpha(c), hsv);
		int[] value = {Color.alpha(c),Color.red(c),Color.green(c),Color.blue(c)};
		return value;
	}

}
