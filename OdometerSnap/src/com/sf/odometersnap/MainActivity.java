package com.sf.odometersnap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sf.odometersnap.image.BitmapTools;
import com.sf.odometersnap.ocr.OCR;

public class MainActivity extends ActionBarActivity{
	
	public static String DATA_PATH, IMAGE_PATH;
	
	public static final String FOLDER_NAME = "/OdometerSnap/";
	public static final String IMAGE_NAME = "image.jpg";
	
	private final int PHOTO_INTENT = 1138;
	private final int CROP_INTENT = 1139;
	private final int GALLERY_INTENT = 1140;
	
	
	/*
	 * Overridden Methods
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		DATA_PATH = Environment.getExternalStorageDirectory().toString() + FOLDER_NAME;
		IMAGE_PATH = DATA_PATH + "/" + IMAGE_NAME;
		
		File pathFile = new File(DATA_PATH);
		File tessLangFile = new File(DATA_PATH + "tessdata/");
		
		if((!pathFile.exists() && !pathFile.mkdirs()) || (!tessLangFile.exists() && tessLangFile.mkdirs())){
			System.out.println("Error creating directories for file");
		}
		
		writeLanguage(OCR.LANGUAGE_DS);
		writeLanguage(OCR.LANGUAGE_ENG);
		
		setContentView(R.layout.activity_main);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
		
			case PHOTO_INTENT:
				crop(Uri.fromFile(new File(IMAGE_PATH)));
				break;
				
			case CROP_INTENT:
				Bitmap b = BitmapTools.readFromFile(IMAGE_NAME);
				if(b == null)
					Log.e("Error", "CROP INTENT returned Null image");
				try {
					b = BitmapTools.correctBitmap(b, IMAGE_PATH);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
				
				if(b == null)
					Log.e("Error", "Correct Bitmap returned Null image");
				new OCRTask(this, "HAIL HYDRA").execute(IMAGE_NAME);
				break;
				
			case GALLERY_INTENT:
				if(data != null){
					Uri selectedImage = data.getData();
			        String[] filePathColumn = { MediaStore.Images.Media.DATA };

			        Cursor cursor = getContentResolver().query(selectedImage,
			                filePathColumn, null, null, null);
			        cursor.moveToFirst();
			        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			        String picturePath = cursor.getString(columnIndex);
			        cursor.close();
			        IMAGE_PATH = picturePath;
			        crop(Uri.fromFile((new File(picturePath))));	        
				}
				break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.shoot) {
			camera();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}
	
	/*
	 * Private Methods
	 */
	
	private void crop(Uri uri){
		Intent crop = new Intent("com.android.camera.action.CROP");
		crop.setDataAndType(uri, "image/*");
		crop.putExtra("crop", true);
		File tempFile = new File(Environment.getExternalStorageDirectory(), "/" + IMAGE_NAME);
		try{
			tempFile.createNewFile();
		}catch(Exception e){
			
		}
		crop.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(crop, CROP_INTENT);
	}
	
	private void gallery(){
		 Intent i = new Intent(Intent.ACTION_PICK,
		            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    startActivityForResult(i, GALLERY_INTENT);
	}
	
	private void camera(){
		File file = new File(IMAGE_PATH);
		Uri outputUri = Uri.fromFile(file);
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		startActivityForResult(i, PHOTO_INTENT);
	}
	
	private void writeLanguage(String lang){
		if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
			try {
				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
				OutputStream out = new FileOutputStream(DATA_PATH
						+ "tessdata/" + lang + ".traineddata");
				byte[] buf = new byte[1024];
				int len;
				
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (IOException einfla) {
				/* TODO: IOException */
			}
		}
	}
	

}