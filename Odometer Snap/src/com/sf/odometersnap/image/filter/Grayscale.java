package com.sf.odometersnap.image.filter;

import com.sf.odometersnap.image.Filter;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Grayscale extends Filter{
	
	public Grayscale(Bitmap i){
		super(i, "Grayscale");
	}

	@Override
	public int[] changePixel(int x, int y) {
		int c = image().getPixel(x, y);
		int red = Color.red(c);
		int green = Color.green(c);
		int blue = Color.blue(c);
		int gray = (red + green + blue)/3;
		int[] value = {Color.alpha(c),gray,gray,gray};
		return value;
	}

}
