package com.lggflex.thigpen;

import java.util.ArrayList;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.lggflex.thigpen.backend.DAO;
import com.lggflex.thigpen.backend.SharedPrefsDAO;
import com.lggflex.thigpen.ui.BubblePicker;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{

    private AppCompatDelegate mDelegate;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setPadding();
        setSupportActionBar(toolbar);
        addPreferencesFromResource(R.xml.preferences);
        setDescriptions();
        setOnClickListener(R.string.pref_user_color, new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				BubblePicker picker = new BubblePicker(getResources().getStringArray(R.array.user_bubble_colors));
				picker.show(getFragmentManager());
			  return true;
			}
    		
    	});
    	setOnClickListener(R.string.pref_location, new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				showLocationPicker();
				return true;
			}
    		
    	});
    	setOnClickListener(R.string.pref_facebook, new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				clear();
				return true;
			}
    	});
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    
	private void clear(){
		SharedPrefsDAO.set("logged in", false);
		Intent home = new Intent(this.getApplicationContext(), LoginActivity.class);
		this.startActivity(home);
	}

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
    
    private void setOnClickListener(int id, OnPreferenceClickListener listener){
    	Preference pref = (Preference) findPreference(getString(id));
    	pref.setOnPreferenceClickListener(listener);
    }
    
    private void filterDrawable(int id, int drawableID){
    	Preference pref = (Preference) findPreference(getString(id));
    	int iColor = getResources().getColor(R.color.accent);
        int red = (iColor & 0xFF0000) / 0xFFFF;
        int green = (iColor & 0xFF00) / 0xFF;
        int blue = iColor & 0xFF;

        float[] matrix = { 0, 0, 0, 0, red
                         , 0, 0, 0, 0, green
                         , 0, 0, 0, 0, blue
                         , 0, 0, 0, 1, 0 };

        ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
    	Drawable draw = getResources().getDrawable(drawableID);
    	draw.setColorFilter(colorFilter);
    	pref.setIcon(draw);
    }
    
    private void filterDrawable(int id, int drawableID, int color){
    	Preference pref = (Preference) findPreference(getString(id));
        int red = (color & 0xFF0000) / 0xFFFF;
        int green = (color & 0xFF00) / 0xFF;
        int blue = color & 0xFF;

        float[] matrix = { 0, 0, 0, 0, red
                         , 0, 0, 0, 0, green
                         , 0, 0, 0, 0, blue
                         , 0, 0, 0, 1, 0 };

        ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
    	Drawable draw = getResources().getDrawable(drawableID);
    	draw.setColorFilter(colorFilter);
    	pref.setIcon(draw);
    }
    
    private void showLocationPicker(){
    	AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Select a Location");
		final ArrayList<String> cities = DAO.getStringsForID("locations");
		String[] locs = new String[cities.size()];
		for(int i = 0; i < cities.size(); i++){
			locs[i] = cities.get(i);
		}
		builder.setItems(locs, new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DAO.setLocation(cities.get(which));
				dialog.dismiss();
			}
			
		});
		builder.show();
    }
    
    private void setDescriptions(){
    	setSummary(R.string.pref_build, genBuildNumber());
    	filterDrawable(R.string.pref_build, R.drawable.ic_perm_device_information_black_36dp);
    	filterDrawable(R.string.pref_location, R.drawable.ic_language_black_36dp);
    	filterDrawable(R.string.pref_facebook, R.drawable.ic_exit_to_app_black_36dp);
    	filterDrawable(R.string.pref_multicolored, R.drawable.ic_invert_colors_black_36dp);
    	filterDrawable(R.string.pref_pallete, R.drawable.ic_dashboard_black_36dp);
    	filterDrawable(R.string.pref_rate, R.drawable.ic_shop_black_36dp);
    	filterDrawable(R.string.pref_about, R.drawable.ic_info_black_36dp);
    	filterDrawable(R.string.pref_username, R.drawable.ic_account_circle_black_36dp);
    	filterDrawable(R.string.pref_user_color, R.drawable.circle, SharedPrefsDAO.get("bubbleColor", Color.CYAN));
        setSummary(R.string.pref_username, "Current Username: " + DAO.getUsername());
        setSummary(R.string.pref_location, "Current Location: " + DAO.getLocation());
        setTrueFalseSummary(R.string.pref_multicolored, R.string.pref_multicolored_des_pos, R.string.pref_multicolored_des_neg, true);
        setTrueFalseSummary(R.string.pref_trans_nav, R.string.pref_trans_nav_des_pos, R.string.pref_trans_nav_des_neg, false);
    }
    
    private void setTrueFalseSummary(int id, int pos, int neg, boolean d){
    	boolean value = DAO.get(id, d);
    	if(value)
    		setSummary(id, getString(pos));
    	else
    		setSummary(id, getString(neg));
    }
    
    private void setSummary(int id, String summary){
    	//filterDrawable(id);
    	Preference pref = (Preference) findPreference(getString(id));
        pref.setSummary(summary);
    }
    
    private String genBuildNumber(){
    	try {
			return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			return "1.0";
		}
    }
    
	private void setPadding(){
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
	}
	
	protected int getStatusBarHeight(){
		int result = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resId > 0)
			result = getResources().getDimensionPixelSize(resId);
		return result;
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		setDescriptions();
	}
}