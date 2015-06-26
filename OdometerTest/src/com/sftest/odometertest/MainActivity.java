package com.sftest.odometertest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends Activity implements AnimationListener{

	
	protected Button camera_button;
	protected TextView text_out;
	protected ImageView image;
	protected boolean taken;
	
	public static final String lang = "eng";
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().toString() + "/OdometerReading/";
	String IMAGE_PATH;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };
		for (String path : paths) {
			File dir = new File(path);
			if (!dir.exists() && !dir.mkdirs()) {
				Log.v("ODOMETER", "ERROR: Creation of directory " + path + " on sdcard failed");
				return;
			}
		}
		
		
		if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
			try {
				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
				//GZIPInputStream gin = new GZIPInputStream(in);
				OutputStream out = new FileOutputStream(DATA_PATH
						+ "tessdata/" + lang + ".traineddata");
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (IOException e) {
			}
		}
		
		setContentView(R.layout.activity_main);
		
		image = (ImageView)findViewById(R.id.photo);
		
		camera_button = (Button)findViewById(R.id.camerabutton);
		camera_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openCamera();		
			}
			
		});
		text_out = (TextView)findViewById(R.id.output);
		IMAGE_PATH = DATA_PATH + "/image.jpg";
	}
	
	private void openCamera(){
		File file = new File(IMAGE_PATH);
		Uri outputUri = Uri.fromFile(file);
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		startActivityForResult(i, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(resultCode){
			case 0: 
				System.out.println("Failed");
				break;
			case -1:
				photoTaken();
				break;
		}
	}
	
	private void photoTaken(){
		taken = true;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_PATH, options);
		try {
			text_out.setText(getText(correctBitmap(bitmap)));
			//image.setImageBitmap(correctBitmap(bitmap));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Bitmap correctBitmap(Bitmap input) throws IOException{
		ExifInterface exif = new ExifInterface(IMAGE_PATH);	
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
	
	private String getText(Bitmap input){
		TessBaseAPI api = new TessBaseAPI();
		api.init(DATA_PATH, lang);
		api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789.");
		api.setPageSegMode(7);
		api.setImage(input);
		String text = api.getUTF8Text();
		api.end();
		return text;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
