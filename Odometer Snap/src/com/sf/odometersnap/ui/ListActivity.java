package com.sf.odometersnap.ui;

import android.os.Bundle;

import com.sf.odometersnap.R;

public class ListActivity extends BaseActivity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		createDrawer();
		createToolbar();
	}
}
