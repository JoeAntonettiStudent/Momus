package com.sf.odometersnap.image.filter;

import android.graphics.Bitmap;

public class Luma extends WeightedGrayscale{
	
	public Luma(Bitmap i){
		super(0.216, 0.7152, 0.0722, "Luma", i);
	}

}