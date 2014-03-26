package com.jonnygold.holidays.calendar;

import com.jonnygold.holidays.calendar.holiday.Country;
import com.jonnygold.holidays.calendar.holiday.CountryManager;
import com.jonnygold.holidays.calendar.holiday.CountryStateManager;
import com.jonnygold.holidays.calendar.holiday.CountryStateManager.State;
import com.jonnygold.holidays.calendar.R;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class SettingsActivity extends PreferenceActivity {

	
	@SuppressWarnings("unused")
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
    }

    @Override
    protected void onStart() {
    	super.onStart();
    }
        
    @Override
	protected void onStop() {
		super.onStop();
	}
    
}