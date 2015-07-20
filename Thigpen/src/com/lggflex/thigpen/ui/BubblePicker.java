package com.lggflex.thigpen.ui;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.backend.SharedPrefsDAO;
import android.app.FragmentManager;
import android.graphics.Color;

public class BubblePicker {
	
	private ColorPickerDialog picker;
	
	public BubblePicker(String[] strColors){
		int[] colors = new int[strColors.length];
		for(int i = 0; i < strColors.length; i++){
			colors[i] = Color.parseColor(strColors[i]);
		}
		picker = ColorPickerDialog.newInstance(R.string.pref_user_color_popup, colors, colors[0], 5, ColorPickerDialog.SIZE_SMALL);
		picker.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
			
			@Override
			public void onColorSelected(int color) {
				setBubbleColor(color);
			}
		});
	}

	public void setBubbleColor(int color){
		SharedPrefsDAO.set("bubbleColor", color);
	}
	
	public void show(FragmentManager manager){
		picker.show(manager, "cal");
	}

}
