/*
 * BitmapTools: Helper class to handle various bitmap saving, loading, and correction functions. 
 * 
 * Static Methods:
 * 	- readFromFile - Reads a file path to a bitmap
 * 		- Parameters:
 * 			* String file - The file path to read from
 * 		- Return Type: A Bitmap object read from the file path
 * 
 * 	- saveToPath - Saves a Bitmap to the external storage directory
 * 		- Parameters:
 * 			* Bitmap input - The bitmap to write to the disk
 * 			* String name - The desired name of the resulting file
 * 
 * 	- correctBitmap - Corrects a bitmap for proper OCR use
 * 		- Parameters:
 * 			* Bitmap input - The Bitmap object to correct
 * 			* String name - The name of the file to correct
 * 		- Return Type: The corrected Bitmap object
 * 
 *  Author: Joseph Antonetti
 *  Last Revision: 5/19/2015
 */
package com.sf.odometersnap.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

public class BitmapTools {
	
	public static Bitmap readFromFile(String file){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		Bitmap bitmap = BitmapFactory.decodeFile(file, options);
		return bitmap;
	}
	
	public static void saveToPath(Bitmap input, String name){
		String path = Environment.getExternalStorageDirectory().toString();
		OutputStream fOut = null;
		File file = new File(path, name + ".jpg");
		try {
			fOut = new FileOutputStream(file);
			input = input.copy(Bitmap.Config.ARGB_8888, true);
			System.out.println("Compressing...");
			input.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			System.out.println("Flusing...");
			fOut.flush();
			System.out.println("Closing...");
			fOut.close();
		} catch (Exception e) {
		}
	}
	
	public static Bitmap correctBitmap(Bitmap input, String path) throws IOException{
		ExifInterface exif = new ExifInterface(path);	
		Bitmap output;
		int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
		int rotate = 0;
		
		switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
		}
		
		if(rotate > 0){
			int width = input.getWidth();
			int height = input.getHeight();
			Matrix m = new Matrix();
			m.preRotate(rotate);
			input = Bitmap.createBitmap(input, 0, 0, width, height, m, false);
		}
		output = input.copy(Bitmap.Config.ARGB_8888, true);
		return output;	
	}
}