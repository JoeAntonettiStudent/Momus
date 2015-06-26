package com.sf.odometersnap.ui;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sf.odometersnap.R;

public class BaseActivity extends ActionBarActivity{
	
	//Intent IDs
	protected static final int CAMERA_INTENT = 1;
	protected static final int CROP_INTENT = 2;
	protected static final int GALLERY_INTENT = 3;
	
	public static final String FOLDER_NAME = "/OdometerReading/";
	public static final String IMAGE_NAME = "image.jpg";
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + FOLDER_NAME;
	public static String IMAGE_PATH = DATA_PATH + "/" + IMAGE_NAME;
	
	ActionBarDrawerToggle toggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		//toggle.syncState();
	}
	
	protected void createToolbar(){
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
	}
	
	private int getStatusBarHeight(){
		int result = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resId > 0)
			result = getResources().getDimensionPixelSize(resId);
		return result;
	}

	protected void createDrawer(){
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.content);
		OnItemClickListener listener = new OnItemClickListener(){	
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
				Intent i = new Intent(getBaseContext(), MainActivity.class);
				if(arg2 == 1)
					i = new Intent(getBaseContext(), TestActivity.class);
				startActivity(i);
			}
		};
		makeList(R.id.drawer, android.R.layout.simple_list_item_1, R.array.nav_drawer_items, listener);
		(findViewById(R.id.drawer)).setPadding(0, getStatusBarHeight(), 0, 0);
		toggle = new ActionBarDrawerToggle(this, drawer, R.string.open_drawer, R.string.close_drawer);
	}
	
	protected void makeList(int id, int layout, int data, OnItemClickListener listener){
		ListView list = (ListView) findViewById(id);
		String[] data_items = getResources().getStringArray(data);
		list.setAdapter(new ArrayAdapter<String>(this, layout, data_items));
		if(listener != null)
			list.setOnItemClickListener(listener);
	}
	
	protected void createPopup(int text_id, View view, boolean secondButton, OnClickListener... listener){
		String[] text = getResources().getStringArray(text_id);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(text[0]);
		alert.setMessage(text[1]);
		if(view != null)
			alert.setView(view);	
		alert.setPositiveButton(text[2], listener[0]);
		if(secondButton)
			alert.setNegativeButton(text[3], listener[1]);
		alert.show();
	}
	
	protected void startActivity(int id, Uri uri){
		Intent i = new Intent();
		switch(id){
			case CAMERA_INTENT:
				File file = new File(IMAGE_PATH);
				Uri outputUri = Uri.fromFile(file);
				i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
				break;
			case GALLERY_INTENT:
				i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				break;
			case CROP_INTENT:
				i = new Intent("com.android.camera.action.CROP");
				i.setDataAndType(uri, "image/*");
				i.putExtra("crop", true);
				File tempFile = new File(Environment.getExternalStorageDirectory(), "/temp.png");
				try{
					tempFile.createNewFile();
				}catch(Exception e){
					System.out.println("Error using crop intent! Are you on AOSP?");
				}
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
				break;
		}
		startActivityForResult(i, id);
	}
}