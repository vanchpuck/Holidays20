package com.jonnygold.holidays.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jonnygold.holidays.calendar.HolidaysDataSource.QueryRestriction;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class SearchableActivity extends ActionBarActivity{

	private static final int MAX_ROW_COUNT = 20;
	
	private HolidaysDataSource holidaysBase;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_holidays);

	    holidaysBase = HolidaysDataSource.newInstance(this);
	    holidaysBase.openForReading();
	    
	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	    	
//	    	List<Holiday> holidays = getHolidays(query);
	    	HolidaysListView holidaysView = (HolidaysListView)findViewById(R.id.view_holidays);
	    	holidaysView.setHolidays(getHolidays(query));
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.action_add_to_calendar, Menu.NONE, R.string.action_add_to_calendar);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_add_to_calendar) {
			
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
			
			Holiday holiday = (Holiday) ((HolidaysListView)acmi.targetView.getParent()).getAdapter().getItem(acmi.position);
						
			Calendar date = Calendar.getInstance();
			date.set(Calendar.MONTH, holiday.getDate().getActualMonth());
			date.set(Calendar.DAY_OF_MONTH, holiday.getDate().getActualDay());
			Intent intent = new Intent(Intent.ACTION_EDIT)
//			        .setData(Uri.parse("content://com.android.calendar/events"))
			        .setType("vnd.android.cursor.item/event")
			        .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
			        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTimeInMillis())
			        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.getTimeInMillis())
			        .putExtra(Events.TITLE, holiday.getTitle())
			        .putExtra(Events.DESCRIPTION, holiday.getDescription());
			startActivity(intent);
			
			return true;
		}
		return super.onContextItemSelected(item);
	}
		
	private List<Holiday> getHolidays(String query){
		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
		
    	restriction.setTitle(query);
    	restriction.setLimit(MAX_ROW_COUNT);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		List<Integer> countryIdList = new ArrayList<Integer>(4);		
		if(sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)){
			countryIdList.add(CountryWorld.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)){
			countryIdList.add(CountryRussia.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_BELORUSSIAN_HOLIDAYS, true)){
			countryIdList.add(CountryBelorussia.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_UKRANE_HOLIDAYS, true)){
			countryIdList.add(CountryUkrane.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_USER_HOLIDAYS, true)){
			countryIdList.add(CountryUser.ID);
		}
		restriction.setCountryes(countryIdList);
		
		//holidaysBase.openForReading();
		List<Holiday> holidays = holidaysBase.getHolidays(restriction);
		return holidays;
	}
	
}
