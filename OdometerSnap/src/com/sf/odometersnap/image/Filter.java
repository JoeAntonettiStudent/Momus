package com.sf.odometersnap.image;

import android.graphics.Bitmap;
import android.graphics.Color;

public abstract class Filter{
	
	public static int NUM_VARIANTS = 4;
	
	private Bitmap image;
	private String title;
	
	public Filter(Bitmap i, String t){
		image = i;
		title = t;
	}
	
	public String title(){
		return title;
	}

	public Bitmap image() {
		return image;
	}
	
	abstract public int[] changePixel(int x, int y);
	
	public static Filter[] makeAll(Bitmap i){
		Filter[] colors = {
				new Regular(i),
				new Grayscale(i),
				new Luma(i),
				new BT601(i),
				new Saturate(i)
		};
		return colors;
	}
	
	public static Bitmap[] apply(Filter[] f){
		
		Bitmap[] output = new Bitmap[f.length];
		for(int i = 0; i < f.length; i++)
			output[i] = f[i].image().copy(Bitmap.Config.ARGB_8888, true);
		
		int width = f[0].image().getWidth();
		int height = f[0].image().getHeight();
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				for(int i = 0; i < f.length; i++){
					int[] newColor = f[i].changePixel(x, y);
					output[i].setPixel(x, y, Color.argb(newColor[0], newColor[1], newColor[2], newColor[3]));
				}
			}
		}
		
		return output;
	}
	
	public static Bitmap[] applyAll(Bitmap b){
		return apply(Filter.makeAll(b));
	}
	
}