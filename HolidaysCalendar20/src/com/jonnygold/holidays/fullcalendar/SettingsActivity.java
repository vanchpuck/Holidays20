package com.jonnygold.holidays.fullcalendar;

import java.util.HashSet;
import java.util.Set;

import com.jonnygold.holidays.fullcalendar.R;
import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.web.UpdateService;

import android.app.AlertDialog;
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
import android.support.v4.content.LocalBroadcastManager;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
		
	public final static String KEY_WORLD_HOLIDAYS = "key_world_holidays";
	public final static String KEY_RUSSIAN_HOLIDAYS = "key_russian_holidays";
	public final static String KEY_BELORUSSIAN_HOLIDAYS = "key_belorussian_holidays";
	public final static String KEY_UKRANE_HOLIDAYS = "key_ukrane_holidays";
	public final static String KEY_KAZACHSTAN_HOLIDAYS = "key_kazachstan_holidays";
	public final static String KEY_USER_HOLIDAYS = "key_user_holidays";
	
	public final static String KEY_WIDGET_4X1_ROWS = "key_widget_4x1_rows";
	public final static String KEY_WIDGET_4X2_ROWS = "key_widget_4x2_rows";
		
	private boolean isDownloaded = false;
	
	private HolidaysDataSource holidaysBase;
	
	private Set<Country> installed;
	
	private CalendarLoader loader;
	
	private class CalendarLoader{
		public final AlertDialog loadingDialog;
		public final AlertDialog inviteDialog;
		public final Country country;
		private final Context context;
		private final Intent serviceIntent;
		CalendarLoader(Context context, final Country country){
			this.country = country;
			this.context = context;
			serviceIntent = new Intent(context, UpdateService.class);
			serviceIntent.putExtra("countryId", country.getId());
			
			inviteDialog = new AlertDialog.Builder(context)
		        .setIcon(R.drawable.flag_kaz)
		    	.setTitle("Праздники Казахстана")
		    	.setMessage("Базу нужно загрузить")
		    	.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						((CheckBoxPreference)findPreference(country.getKey())).setChecked(false);
					}
				})
		    	.setPositiveButton("Загрузить", new DialogInterface.OnClickListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onClick(DialogInterface dialog, int which) {						
						startDownloading((CheckBoxPreference)findPreference(country.getKey()));
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onCancel(DialogInterface arg0) {
						((CheckBoxPreference)findPreference(country.getKey())).setChecked(false);
					}
				})
		    	.create();
			
			loadingDialog = new AlertDialog.Builder(context)
				.setTitle("Внимание!!")
				.setOnCancelListener(new DialogInterface.OnCancelListener() {					
					@SuppressWarnings("deprecation")
					@Override
					public void onCancel(DialogInterface dialog) {
						stopService(serviceIntent);
						if(!isDownloaded){
							((CheckBoxPreference)findPreference(country.getKey())).setChecked(false);
						} else{
							((CheckBoxPreference)findPreference(country.getKey())).setChecked(true);
						}
					}
				})
				.create();
		}
		
		AlertDialog createErrorDialog(String message){
			return new AlertDialog.Builder(context)
						.setTitle("Ошибка установки")
						.setPositiveButton("Ok", null)
						.setMessage(message)
						.create();
		}
		
		public void startDownloading(final CheckBoxPreference pref){
			if(loader != null){
				loader.loadingDialog.show();
				startService(loader.serviceIntent);
			}
		}
		
	}
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        holidaysBase = HolidaysDataSource.newInstance(this);
        
        BroadcastReceiver receiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context arg0, Intent intent) {
				
				UpdateService.UpdateState response = (UpdateService.UpdateState) intent.getExtras().getSerializable("Result");
				switch (response) {
				case SUCCESS :
					CheckBoxPreference pref = (CheckBoxPreference)findPreference(loader.country.getKey());
					isDownloaded = true;
					holidaysBase.openForReading();  
					installed = new HashSet<Country>(holidaysBase.getCountries());
					pref.setChecked(true);
					break;
				case BAD_RESPONSE :
					loader.createErrorDialog(response.message).show();
					break;
				case NOT_CONNECTED :
					loader.createErrorDialog(response.message).show();
					break;
				case OTHER :
					loader.createErrorDialog(response.message).show();
					break;
				default:
					break;
				}
				loader.loadingDialog.cancel();
			}
        	
        };
        
        IntentFilter filter = new IntentFilter("Broadcast");
        
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, filter);
        
        
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
    	holidaysBase.openForReading();    	
    	installed = new HashSet<Country>(holidaysBase.getCountries());
    }
        
    @Override
	protected void onStop() {
		super.onStop();
		holidaysBase.close(); //   
	}
    
    @SuppressWarnings("deprecation")
	@Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    protected void onPause() {
    	super.onPause();
    	getPreferenceScreen().getSharedPreferences()
        		.unregisterOnSharedPreferenceChangeListener(this);
    }
    
	@Override
	public void onSharedPreferenceChanged(SharedPreferences pref, final String key) {
		Country country = CountryManager.getInstance().getCountry(key);
		if(pref.getBoolean(key, false) && !installed.contains(country)){
			loader = new CalendarLoader(this, country);
			if(!this.isFinishing()){
				loader.inviteDialog.show();
			}
		}		
	}
	
}