package com.sf.odometersnap.image.filter;

import android.graphics.Bitmap;

public class BT601 extends WeightedGrayscale{
	
	public BT601(Bitmap i){
		super(0.299, 0.587, 0.114, "BT601", i);
	}

}