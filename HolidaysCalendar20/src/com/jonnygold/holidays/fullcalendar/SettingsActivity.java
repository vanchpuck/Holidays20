package com.jonnygold.holidays.fullcalendar;

import com.jonnygold.holidays.fullcalendar.R;
import com.jonnygold.holidays.fullcalendar.web.ResponseReceiver;
import com.jonnygold.holidays.fullcalendar.web.UpdateService;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.preference.PreferenceScreen;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public final static String KEY_WORLD_HOLIDAYS = "key_world_holidays";
	public final static String KEY_RUSSIAN_HOLIDAYS = "key_russian_holidays";
	public final static String KEY_BELORUSSIAN_HOLIDAYS = "key_belorussian_holidays";
	public final static String KEY_UKRANE_HOLIDAYS = "key_ukrane_holidays";
	public final static String KEY_KAZACHSTAN_HOLIDAYS = "key_kazachstan_holidays";
	public final static String KEY_USER_HOLIDAYS = "key_user_holidays";
	
	public final static String KEY_WIDGET_4X1_ROWS = "key_widget_4x1_rows";
	public final static String KEY_WIDGET_4X2_ROWS = "key_widget_4x2_rows";
	
	private AlertDialog loadingDialog;
	
	private boolean isDownloaded = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//        getListView().setBackgroundColor(Color.TRANSPARENT);
//
//        getListView().setCacheColorHint(Color.TRANSPARENT);
        
        BroadcastReceiver receiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context arg0, Intent intent) {
				if(intent.getExtras().getInt("Result") == 1){
					CheckBoxPreference pref = (CheckBoxPreference)findPreference("key_kazachstan_holidays");
					isDownloaded = true;
					pref.setChecked(true);
				}
				loadingDialog.cancel();
			}
        	
        };
        
        IntentFilter filter = new IntentFilter("Broadcast");
        
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, filter);
        
        
        PreferenceScreen targetCategory = (PreferenceScreen)findPreference("key_holidays_list");
        
       
    	CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
        //make sure each key is unique  
        checkBoxPreference.setKey("test_key");
        checkBoxPreference.setTitle("Test!!!!");
        checkBoxPreference.setChecked(true);
        
        targetCategory.addPreference(checkBoxPreference);
        targetCategory.removePreference(checkBoxPreference);
        
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
    
	@Override
	public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
		Toast.makeText(this, "erttr", Toast.LENGTH_SHORT);
		if(KEY_KAZACHSTAN_HOLIDAYS.equals(key)){
			if(!pref.getBoolean("key_kazachstan_holidays", false)){
				return;
			}
			AlertDialog.Builder b = new AlertDialog.Builder(this);
	        b.setIcon(R.drawable.flag_kaz)
	        	.setTitle("Праздники Казахстана")
	        	.setMessage("Базу нужно загрузить")
	        	.setNeutralButton("Полная версия", null)
	        	.setNegativeButton("Отмена", null)
	        	.setPositiveButton("Загрузить", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {						
						doSmth();
					}
				});
	        
	        
	        b.create().show();
		}
	}
	
	public void doSmth(){
		final Intent serviceIntent = new Intent(this, UpdateService.class);
		startService(serviceIntent);
		
		AlertDialog.Builder b1 = new AlertDialog.Builder(this);
		b1.setMessage("Идет загрузка...")
			.setTitle("Внимание!")
			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					CheckBoxPreference pref = (CheckBoxPreference)findPreference("key_kazachstan_holidays");
					stopService(serviceIntent);
					if(!isDownloaded){
						pref.setChecked(false);
					} else{
						pref.setChecked(true);
					}
				}
			});
//			.create().show();
		loadingDialog = b1.create();
		loadingDialog.show();
		
//		stopService(serviceIntent);
	}
    
}