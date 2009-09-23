package com.latinsud.android.slidetypekeyboard;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.latinsud.android.slidetypekeyboard.R;

// This is very simple. Android does it all from prefs.xml.
public class SettingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		addPreferencesFromResource(R.xml.prefs);
	}
}
