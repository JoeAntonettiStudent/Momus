/*
 * Filter - Abstract class which represents a single filter to perform on an image. The image is stored in the filter. When apply is called,
 * the bitmap within the filter is changed to represent the image after the filter is applied. The resulting image can be obtained using 
 * image().
 * 
 * NOTE: This class is meant to be extended to create different image filters. When extending Filter, override both the constructor
 * and the changePixel() method. The constructor should only take one parameter - a bitmap object - and it should call the default
 * filter constructor with the name of the filter and this bitmap. The changePixel() method should be changed to apply whatever filter
 * you seek to create. Nothing else should be changed when extending.
 * 
 *  Constructors Parameters:
 *  	- Bitmap i - The image which the filter is to be applied to
 *  	- String title - The name of the filter
 *  
 *  Static Methods:
 *  	- makeAll: Creates an array of filters based on the specified image
 *  		- Parameters: 
 *  			* Bitmap i - The image the filters are to be performed on
 *  		- Return Type: An array of filter objects, representing every filter
 *  
 * 		- apply: Runs an array of filter objects and places their resulting bitmaps in an array of bitmap objects
 * 			- Parameters:
 * 				* Filter[] f - The array of filter objects to be ran
 * 			- Return Type: The resulting array of bitmap objects
 * 
 * 		- applyAll: Runs every filter on a specified bitmap
 * 			- Parameters:
 * 				* Bitmap b - The image to run filters on
 * 			- Return Type: An array of bitmaps showing every result
 * 
 * Public Methods:
 * 		- title: Gets the title of the filter
 * 			- Return Type: A String representing the filter's name
 * 
 * 		- image: Gets the image object stored in a filter
 * 			- Return Type: A Bitmap representing the image stored in the filter
 * 
 * 		- changePixel: An abstract method meant to be overridden in subclasses. Changes a specified pixel in Filter's bitmap object, and 
 * 					   returns a new int array representing the new value for this pixel. When creating subclasses, override only this 
 * 					   method to change the pixel according to whatever filter you wish to create.
 * 			- Parameters:	
 * 				* int x: The x-coordinate of the pixel to change
 * 				* int y: The y-coordinate of the pixel to change
 * 			- Return Type: An integer array representing the new value of this pixel.
 *  
 *  Author: Joseph Antonetti
 *  Last Revision: 5/19/2015
 */

package com.sf.odometersnap.image;

import com.sf.odometersnap.image.filter.BT601;
import com.sf.odometersnap.image.filter.Grayscale;
import com.sf.odometersnap.image.filter.Luma;
import com.sf.odometersnap.image.filter.Regular;
import com.sf.odometersnap.image.filter.Saturate;

import android.graphics.Bitmap;
import android.graphics.Color;

public abstract class Filter{
	
	//Private Variables
		private Bitmap image;
		private String title;
		
	//Constructors
		
		public Filter(Bitmap i, String t){
			image = i;
			title = t;
		}
		
	//Static Methods	
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
		
	//Public Methods
		public String title(){
			return title;
		}

		public Bitmap image() {
			return image;
		}
	
		abstract public int[] changePixel(int x, int y);
}