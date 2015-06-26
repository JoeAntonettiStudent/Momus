package com.sf.odometersnap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.sf.odometersnap.ocr.OCR;

class OCRTask extends AsyncTask<String, Void, String[]>{
	
	private ProgressDialog progressDialog;
	private Context context;
	private String userInput;
	
	/*
	 * Overrided Constructors to pass Context to create Progress Dialog, as well as to pass user input
	 */
	
	public OCRTask(Context c){
		super();
		context = c;
		userInput = null;
	}
	
	public OCRTask(Context c, String s){
		super();
		context = c;
		userInput = s;
	}
	
	protected void onPreExecute(){
		super.onPreExecute();
		
		//Show the Progess Dialog
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Running OCR...");
		progressDialog.setTitle("Processing Image");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}

	@Override
	protected String[] doInBackground(String... params) {
		String[] results = new String[params.length];
		for(int i = 0; i < params.length; i++){
			if(userInput != null)
				results[i] = OCR.processFromFile(params[i], userInput);
			else{
				/*TODO: Handle processing without user input*/
			}
		}
		return results;
	}

	protected void onPostExecute(String[] result){
		progressDialog.dismiss();
		String ret = "";
		for(String s : result){
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