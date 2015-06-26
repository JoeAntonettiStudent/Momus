package com.sf.odometersnap.ui;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.sf.odometersnap.R;
import com.sf.odometersnap.auth.Test;
import com.sf.odometersnap.image.BitmapTools;
import com.sf.odometersnap.util.NotificationHelper;

public class TestActivity extends ListActivity{
	
	private int test_id = -1;
	
	public final static int TEST_WHITESPACE = 0;
	public final static int TEST_SIZE = 1;
	public final static int TEST_HISTORY = 2;
	public final static int TEST_STATS = 3;
	public final static int TEST_ACCURACY = 4;
	public final static int TEST_MATRIX = 5;
	public final static int TEST_NOTIFICATION = 6;
	public final static int TEST_VOTING = 7;
	public final static int TEST_BARRY = 8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		OnItemClickListener listener = new OnItemClickListener(){	
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
				test_id = arg2;
				if(test_id == TEST_ACCURACY)
					Test.runTest(null, TEST_ACCURACY);
				if(test_id == TEST_MATRIX)
					Test.runTest(null, TEST_MATRIX);
				if(test_id == TEST_NOTIFICATION)
					NotificationHelper.popNotification(getBaseContext(), "Odometer Reading Required", "Click to enter a odometer reading", android.R.drawable.ic_menu_camera, getResources().getColor(R.color.primary));
				if(test_id == TEST_VOTING)
					Test.runTest(null, TEST_VOTING);
				else
					startActivity(GALLERY_INTENT, null);
			}
		};
		makeList(R.id.main_list, android.R.layout.simple_list_item_1, R.array.test_cases, listener);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
			case CAMERA_INTENT:
				startActivity(CROP_INTENT, Uri.fromFile((new File(IMAGE_PATH))));
				break;
			case CROP_INTENT:
				Bitmap picture = BitmapTools.readFromFile(Environment.getExternalStorageDirectory() + "/temp.png");
				try {
					picture = BitmapTools.correctBitmap(picture, IMAGE_PATH);
					Test.runTest(picture, test_id);
				} catch (IOException e) {
					System.out.println("Error executing filter task!");
				}
				break;
			case GALLERY_INTENT:
				if(data != null){
					Uri selectedImage = data.getData();
			        String[] filePathColumn = { MediaStore.Images.Media.DATA };
			        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			        cursor.moveToFirst();
			        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			        String picturePath = cursor.getString(columnIndex);
			        cursor.close();
			        IMAGE_PATH = picturePath;
			        startActivity(CROP_INTENT, Uri.fromFile((new File(picturePath))));
			        
				}
		}
	}
}