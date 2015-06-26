package com.sf.odometersnap.auth;

import java.io.File;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import com.sf.odometersnap.ocr.OCR;
import com.sf.odometersnap.ui.MainActivity;

public class AccuracyChecker extends Test{
	
	int perfect_finds = 0;
	int off_by_one = 0;
	int off_by_two = 0;
	int failed = 0;

	public AccuracyChecker(Bitmap i) {
		super(i, "Accuracy Check");
		
	}

	@Override
	public boolean run() {
		Log.w(TEST_TAG_NAME, "Running Accuracy Check.");
		//Log.i(TEST_TAG_NAME, "'You can't stop me, Flash, and you never will'");
		String[] file_name_list = (new File(MainActivity.DATA_PATH + "testimages/")).list();
		int total_file_count = file_name_list.length;
		Log.w(TEST_TAG_NAME, "Found " + total_file_count + " files.");
		int files_processed = 1;
		for(String current_file_name: file_name_list){
			Log.v(TEST_TAG_NAME, String.format("%d of %d files processed", files_processed, total_file_count));
			testImagePath(current_file_name);
			files_processed++;
		}
		Log.e(Test.TEST_TAG_NAME, "Perfect: " + perfect_finds);
		Log.e(Test.TEST_TAG_NAME, "Off By One: " + off_by_one);
		Log.e(Test.TEST_TAG_NAME, "Off By Two: " + off_by_two);
		Log.e(Test.TEST_TAG_NAME, "Failed: " + failed);
		return true;
	}
	
	public boolean testImagePath(String image_path){
		Log.w(TEST_TAG_NAME, "--------------------");
		String image_value = image_path.replace(".jpg", "");
		Log.w(TEST_TAG_NAME, "Current Image Value: " + image_value);
		Log.v(TEST_TAG_NAME, "Current Image Path: " + image_path);
		String result = OCR.processFromFile(Environment.getExternalStorageDirectory().toString() + "/OdometerReading/testimages/" + image_path, image_value);
		Log.w(TEST_TAG_NAME, "OCR Result: " + result);
		if(result.length() > 2)
			result = result.substring(0, result.length() - 2);
		if(image_value.length() > 2)
			image_value = image_value.substring(0, image_value.length() - 2);
		if(result.equals(image_value))
			perfect_finds++;
		else if(result.substring(0, result.length() - 1).equals(image_value.substring(0, image_value.length())))
				off_by_one++;
		else if(result.length() > 1 && result.substring(0, result.length() - 2).equals(image_value.substring(0, image_value.length() - 2)))
				off_by_two++;
		else
			failed++;
		Log.w(TEST_TAG_NAME, "--------------------");
		return false;
	}

}
