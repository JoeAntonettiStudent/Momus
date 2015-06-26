/*
 * MainActivity - The default activity for the program. This handles all of the UI for the main app
 *  
 *  Author: Joseph Antonetti
 *  Last Revision: 5/20/2015
 */

package com.sf.odometersnap.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.sf.odometersnap.R;
import com.sf.odometersnap.image.BitmapTools;
import com.sf.odometersnap.ocr.OCR;

public class MainActivity extends BaseActivity{
	
	private String user_input = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.card);
		createDrawer();
		createToolbar();
		File pathFile = new File(DATA_PATH);
		File tessLangFile = new File(DATA_PATH + "tessdata/");
		
		if((!pathFile.exists() && !pathFile.mkdirs()) || (!tessLangFile.exists() && tessLangFile.mkdirs())){
			System.out.println("Error creating directories for file");
		}
		
		outputTrainingData(OCR.LANGUAGE_DS);
		outputTrainingData(OCR.LANGUAGE_ENG);
		Button b = (Button) findViewById(R.id.camera_button);
		b.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				openPrompt();	
			}
		});
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
					new OCRFilterTask(this, user_input).execute(picture);
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
	
	public void openPrompt(){
		DialogInterface.OnClickListener listenerPos = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(CAMERA_INTENT, null);
			}
		};
		DialogInterface.OnClickListener listenerNeg = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//startActivity(GALLERY_INTENT, null);
				openNumberPrompt();
			}
		};
		DialogInterface.OnClickListener[] listeners = {listenerPos, listenerNeg};
		createPopup(R.array.main_screen_popup, null, true, listeners);
	}
	
	//TODO: Implement Number Prompt
	private void openNumberPrompt(){
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				user_input = input.getText().toString();
				startActivity(GALLERY_INTENT, null);
			}
		};
		createPopup(R.array.input_popup, input, false, listener);
	}
	

	
	private void outputTrainingData(String data){
		if (!(new File(DATA_PATH + "tessdata/" + data + ".traineddata")).exists()) {
			try {
				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/" + data + ".traineddata");
				OutputStream out = new FileOutputStream(DATA_PATH
						+ "tessdata/" + data + ".traineddata");
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (IOException einfla) {
			}
		}
	}
}
