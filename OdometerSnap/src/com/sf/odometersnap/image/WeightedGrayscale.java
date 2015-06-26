package com.sf.odometersnap.image;

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

class Luma extends WeightedGrayscale{
	
	public Luma(Bitmap i){
		super(0.216, 0.7152, 0.0722, "Luma", i);
	}

}

class BT601 extends WeightedGrayscale{
	
	public BT601(Bitmap i){
		super(0.299, 0.587, 0.114, "BT601", i);
	}

}
