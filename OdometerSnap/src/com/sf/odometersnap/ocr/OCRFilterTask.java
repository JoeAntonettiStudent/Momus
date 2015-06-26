package com.sf.odometersnap.ocr;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import com.sf.odometersnap.image.Filter;

class OCRFilterTask extends AsyncTask<Filter, String, Void>{
	
	private ProgressDialog progressDialog;
	private Context context;
	private ArrayList<String> results = new ArrayList<String>();

	public OCRFilterTask(Context c){
		super();
		context = c;
	}
	
	protected void onPreExecute(){
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Running OCR...");
		progressDialog.setTitle("Processing Image");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}

	@Override
	protected Void doInBackground(Filter... params) {
		int width = params[0].image().getWidth();
		int height = params[0].image().getHeight();
		Bitmap[] outputs = new Bitmap[params.length];
		for(int i = 0; i < outputs.length; i++){
			outputs[i] = params[0].image().copy(Bitmap.Config.ARGB_8888, true);
		}
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
					for(int i = 0; i < params.length; i++){
						int[] newColor = params[i].changePixel(x, y);
						outputs[i].setPixel(x, y, Color.argb(newColor[0], newColor[1], newColor[2], newColor[3]));
					}
			}
		}
		for(int i = 0; i < params.length; i++){
			results.add(params[i].title() + ": " + new OCREngine().getText(outputs[i]));	
		}
		return null;
	}
	
	protected void onPostExecute(Void result){
		progressDialog.dismiss();
		String ret = "";
		for(String s : results){
			if(s.length() < 50){
				ret += s + "\n";
			}
		}
		showText("OCR Result", ret);
	}
	
	private void showText(String title, String value){
		new AlertDialog.Builder(context)
	    .setTitle(title)
	    .setMessage(value)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
}