package com.sf.odometersnap.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

abstract public class ProgressSpinnerTask<A, B, C> extends AsyncTask<A, B, C>{
	
	private ProgressDialog progressDialog;
	private Context context;
	private String title;
	private String description;
	
	public ProgressSpinnerTask(Context c, String title, String description){
		super();
		this.context = c;
		this.title = title;
		this.description = description;
	}
	
	protected void onPreExecute(){
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(description);
		progressDialog.setTitle(title);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}
	
	abstract protected C doInBackground(A... params);
	
	protected void onPostExecute(C result){
		progressDialog.dismiss();
	}
	
	protected void showText(String title, String value){
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
