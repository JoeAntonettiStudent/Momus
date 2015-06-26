package com.antonetti.floatingactionbutton;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		setOnClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				startService(new Intent(getBaseContext(), FABService.class));
				return false;
			}
			
		}, "start_service");
	}
	
	private void setOnClickListener(OnPreferenceClickListener listener, String key){
		Preference pref = (Preference) findPreference(key);
		pref.setOnPreferenceClickListener(listener);
	}

}