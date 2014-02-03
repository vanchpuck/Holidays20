package com.jonnygold.holidays.fullcalendar;

import com.jonnygold.holidays.fullcalendar.R;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

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
        
        //fetch the item where you wish to insert the CheckBoxPreference, in this case a PreferenceCategory with key "targetCategory"
        PreferenceScreen targetCategory = (PreferenceScreen)findPreference("key_holidays_list");

        CountryManager.init(this);
        CountryManager.refresh();
        
        CheckBoxPreference checkBoxPreference = null;
        for(Country country : CountryManager.getCountries()){
        	checkBoxPreference = new CheckBoxPreference(this);
            //make sure each key is unique  
            checkBoxPreference.setKey(String.valueOf(country.getId()));
            checkBoxPreference.setTitle(country.getTitle()+"_test");
            checkBoxPreference.setChecked(true);
            
            targetCategory.addPreference(checkBoxPreference);
        }
//        //create one check box for each setting you need
//        CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
//        //make sure each key is unique  
//        checkBoxPreference.setKey("key_test");
//        checkBoxPreference.setTitle("key_test_title");
//        checkBoxPreference.setChecked(true);
//
//        targetCategory.addPreference(checkBoxPreference);
        
        
    }
    
    
}