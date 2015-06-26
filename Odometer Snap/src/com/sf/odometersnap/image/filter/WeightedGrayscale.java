package com.sf.odometersnap.image.filter;

import com.sf.odometersnap.image.Filter;

import android.graphics.Bitmap;
import android.graphics.Color;

public abstract class WeightedGrayscale extends Filter{
	
	private double redWeight;
	private double greenWeight;
	private double blueWeight;
	
	public WeightedGrayscale(double red, double blue, double green, String name, Bitmap i){
		super(i, name);
		redWeight = red;
		blueWeight = blue;
		greenWeight = green;
	}

	@Override
	public int[] changePixel(int x, int y) {
		int c = image().getPixel(x, y);
		double red = Color.red(c);
		double green = Color.green(c);
		double blue = Color.blue(c);
		double grey = ((red*redWeight) + (green*greenWeight) + (blueWeight*blue))/3;
		int gray = (int)(grey);
		int[] value = {Color.alpha(c),gray,gray,gray};
		return value;
	}

}