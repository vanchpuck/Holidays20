package com.jonnygold.holidays.calendar;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {
	
	public final static String KEY_WORLD_HOLIDAYS = "key_world_holidays";
	public final static String KEY_RUSSIAN_HOLIDAYS = "key_russian_holidays";
	public final static String KEY_BELORUSSIAN_HOLIDAYS = "key_belorussian_holidays";
	public final static String KEY_UKRANE_HOLIDAYS = "key_ukrane_holidays";
	public final static String KEY_USER_HOLIDAYS = "key_user_holidays";
	
	public final static String KEY_WIDGET_4X1_ROWS = "key_widget_4x1_rows";
	public final static String KEY_WIDGET_4X2_ROWS = "key_widget_4x2_rows";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//        getListView().setBackgroundColor(Color.TRANSPARENT);
//
//        getListView().setCacheColorHint(Color.TRANSPARENT);
        
        
        
    }
    
    
}