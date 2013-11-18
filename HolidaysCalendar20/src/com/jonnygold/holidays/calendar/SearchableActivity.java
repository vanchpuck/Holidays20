package com.jonnygold.holidays.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jonnygold.holidays.calendar.HolidaysDataSource.QueryRestriction;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchableActivity extends ActionBarActivity{

	private HolidaysDataSource holidaysBase;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_holidays);

	    holidaysBase = HolidaysDataSource.getInstance(this);
	    holidaysBase.openForReading();
	    
	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	    	
//	    	List<Holiday> holidays = getHolidays(query);
	    	HolidaysListView holidaysView = (HolidaysListView)findViewById(R.id.view_holidays);
	    	holidaysView.setHolidays(getHolidays("�������"));
	    	holidaysView.setOnItemClickListener(new HolidaysListView.OnHolidayClickListener());
	      
	    	// Configure holidays ListView
//			HolidaysAdapter listAdapter = new HolidaysAdapter(this, getHolidays());
//			ListView holidaysView = (ListView)findViewById(R.id.view_holidays);
//			holidaysView.setAdapter(listAdapter);
			
//			holidaysView.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int idx,	long id) {
//					Holiday holiday = holidays.get(idx);
//					
//					Builder builder = new AlertDialog.Builder(activity);				
//					builder.setPositiveButton("Ok", null)
//						.setView(getHolidayDetail(holiday)) 
//						.show();
//				}
//			});
			
	    }
	    holidaysBase.close();
	}
	
	private List<Holiday> getHolidays(String query){
		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
    	restriction.setTitle(query);
    	return holidaysBase.getHolidays(restriction);
	}
		
//	private List<Holiday> getHolidays(){
//		
//		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//		
//		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
//		restriction.setDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//		
//		
//		List<Integer> countryIdList = new ArrayList<Integer>(4);		
//		if(sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)){
//			Log.w("WorldCountry", sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)+"");
//			countryIdList.add(CountryWorld.ID);
//		}
//		if(sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)){
//			Log.w("RusCountry", sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)+"");
//			countryIdList.add(CountryRussia.ID);
//		}
//		if(sharedPref.getBoolean(SettingsActivity.KEY_BELORUSSIAN_HOLIDAYS, true)){
//			countryIdList.add(CountryBelorussia.ID);
//		}
//		if(sharedPref.getBoolean(SettingsActivity.KEY_UKRANE_HOLIDAYS, true)){
//			countryIdList.add(CountryUkrane.ID);
//		}
//		restriction.setCountryes(countryIdList);
//		
//		
//		List<Holiday> holidays = holidaysBase.getHolidays(restriction);
//		return holidays;
//	}
	
}
