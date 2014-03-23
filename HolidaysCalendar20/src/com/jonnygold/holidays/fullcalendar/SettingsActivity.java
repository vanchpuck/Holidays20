package com.jonnygold.holidays.fullcalendar;

import java.util.HashSet;
import java.util.Set;

import com.jonnygold.holidays.fullcalendar.R;
import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.holiday.CountryStateManager;
import com.jonnygold.holidays.fullcalendar.holiday.CountryStateManager.State;
import com.jonnygold.holidays.fullcalendar.web.UpdateService;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {
//		
//	public final static String KEY_WORLD_HOLIDAYS = "key_world_holidays";
//	public final static String KEY_RUSSIAN_HOLIDAYS = "key_russian_holidays";
//	public final static String KEY_BELORUSSIAN_HOLIDAYS = "key_belorussian_holidays";
//	public final static String KEY_UKRANE_HOLIDAYS = "key_ukrane_holidays";
//	public final static String KEY_KAZACHSTAN_HOLIDAYS = "key_kazachstan_holidays";
//	public final static String KEY_USER_HOLIDAYS = "key_user_holidays";
//	
//	public final static String KEY_WIDGET_4X1_ROWS = "key_widget_4x1_rows";
//	public final static String KEY_WIDGET_4X2_ROWS = "key_widget_4x2_rows";
	
	private HolidaysDataSource holidaysBase;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        holidaysBase = HolidaysDataSource.newInstance(this);
        
        CountryStateManager stateManager = new CountryStateManager(this);
        
        PreferenceScreen targetCategory = (PreferenceScreen)findPreference("key_holidays_list");
        targetCategory.removeAll();
        for(Country c : Country.values()){
        	if(stateManager.getState(c) == State.INSTALLED){
        		CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
        		checkBoxPreference.setKey(c.getKey());
        		checkBoxPreference.setTitle(CountryManager.getInstance().getCountry(c.getKey()).getName());
        		checkBoxPreference.setChecked(true);
        		targetCategory.addPreference(checkBoxPreference);
        	}
        }
//        for(Country c : Country.values()){
//        	if(targetCategory.findPreference(c.getKey()) == null){
//        		CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
//        		checkBoxPreference.setKey(c.getKey());
//        		checkBoxPreference.setTitle(CountryManager.getInstance().getCountry(c.getKey()).getName());
//        		checkBoxPreference.setChecked(true);
//        		targetCategory.addPreference(checkBoxPreference);
//        	}
//        	else if(stateManager.getState(c) == State.NOT_INSTALLED){
//        		targetCategory.removePreference(findPreference(c.getKey()));
//        	}
//        }
        
//        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
//        manager.registerReceiver(receiver, filter);
        
        
//		PreferenceScreen targetCategory = (PreferenceScreen)findPreference("key_holidays_list");
        
//       	CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
//        //make sure each key is unique  
//        checkBoxPreference.setKey("test_key");
//        checkBoxPreference.setTitle("Test!!!!");
//        checkBoxPreference.setChecked(true);
//        
//        targetCategory.addPreference(checkBoxPreference);
//        targetCategory.removePreference(checkBoxPreference);
        
        
    }

    @Override
    protected void onStart() {
    	super.onStart();
    }
        
    @Override
	protected void onStop() {
		super.onStop();
	}
    
//    @SuppressWarnings("deprecation")
//	@Override
//    protected void onResume() {
//        super.onResume();
//        getPreferenceScreen().getSharedPreferences()
//                .registerOnSharedPreferenceChangeListener(this);
//    }
    
//    @SuppressWarnings("deprecation")
//	@Override
//    protected void onPause() {
//    	super.onPause();
//    	getPreferenceScreen().getSharedPreferences()
//        		.unregisterOnSharedPreferenceChangeListener(this);
//    }
    
//	@Override
//	public void onSharedPreferenceChanged(SharedPreferences pref, final String key) {
//		Country country = CountryManager.getInstance().getCountry(key);
//		if(pref.getBoolean(key, false) && !installed.contains(country)){
//			loader = new CalendarLoader(this, country);
//			if(!this.isFinishing()){
//				loader.inviteDialog.show();
//			}
//		}		
//	}
	
}