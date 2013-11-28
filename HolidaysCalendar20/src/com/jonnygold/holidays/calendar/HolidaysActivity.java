package com.jonnygold.holidays.calendar;


import java.util.Calendar;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class HolidaysActivity extends ActionBarActivity implements OnQueryTextListener {

//	public static SharedPreferences prefere;// = PreferenceManager.getDefaultSharedPreferences(collection.getContext());
	
	private HolidaysDataSource holidaysBase;
	
	private Calendar calendar;
	
	private DaysPagerAdapter pagerAdapter;
	ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holidays);
		
		holidaysBase = HolidaysDataSource.getInstance(this);
//		calendar = Calendar.getInstance();
		
		Intent intent = getIntent();
	    if (intent != null && Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    	String query = intent.getStringExtra(SearchManager.QUERY);
	    	Log.w("Start", query);
	    }
		
	    Log.w("Start", "1");
		
//	    registerForContextMenu(findViewById(R.id.view_holidays));
	    
	}
	
	@Override
	protected void onStart() {
		Log.w("Start", "2");
		super.onStart();
		Log.w("Start", "3");
		if(holidaysBase == null){
			Log.w("Test1", "NULL");
		}
		calendar = Calendar.getInstance();
		
		holidaysBase.openForReading();
		Log.w("Start", "4");
		holidaysBase.updateFloatHolidays(calendar.get(Calendar.YEAR));
		Log.w("Start", "5");
		DaysPagerAdapter pagerAdapter = new DaysPagerAdapter(this, holidaysBase, calendar);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSaveEnabled(false);
        viewPager.setCurrentItem(DaysPagerAdapter.START_POSITION);     
        
        setContentView(viewPager);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		holidaysBase.close();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		holidaysBase.openForReading();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.action_add_holiday, Menu.NONE, R.string.action_add_to_calendar);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_add_holiday) {
			
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
			
			Holiday holiday = (Holiday) ((HolidaysListView)acmi.targetView.getParent()).getAdapter().getItem(acmi.position);
						
			Calendar date = Calendar.getInstance();
			date.set(Calendar.MONTH, holiday.getActualMonth());
			date.set(Calendar.DAY_OF_MONTH, holiday.getActualDay());
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.holidays, menu);

	  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	  
	  
	  MenuItem searchItem = menu.findItem(R.id.action_search);
	  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//	  if (searchView != null) {
//	     searchView.setOnQueryTextListener(this);
//	  }
	  
	  SearchableActivity act = new SearchableActivity();
	  
	  if(searchManager.getSearchableInfo(getComponentName()) == null){
		  Log.w("NULL1", "NULL11");
	  }
	  if(searchManager.getSearchableInfo(act.getComponentName()) == null){
		  Log.w("NULL1", "NULL11");
	  }
	  
	  Log.w("1", getComponentName().getClassName());
	  Log.w("1", getComponentName().getShortClassName());
	  Log.w("1", getComponentName().getPackageName());
	  
	  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	  searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


	  return super.onCreateOptionsMenu(menu);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.holidays, menu);
//		
//		Log.w("test1", "1");
//		
//		// Get the SearchView and set the searchable configuration
//	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//	    
//	    Log.w("test1", "2");
//	    
//	    MenuItem searchItem = menu.findItem(R.id.action_search);
//	    
//	    
////	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
////        if (searchView != null) {
////            SearchViewCompat.setOnQueryTextListener(searchView, mOnQueryTextListener);
////            searchView.setIconifiedByDefault(false);
////            Log.d("Test","SearchView not null");
////        } else
////            Log.d("Test", "SearchView is null");
//	    
//	    
//
//	    // Assumes current activity is the searchable activity
//	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	    
//	    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
//
//	    Log.w("test1", "6");
//		
//		return true;
//	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_preferences :
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            return true; 
//	        case R.id.action_search :
//	        	Intent intent = new Intent(this, SearchActivity.class);
//	            startActivity(intent);
	        case R.id.action_add_holiday :
	        	Intent newHolidayIntent = new Intent(this, NewHolidayActivity.class);
	            startActivity(newHolidayIntent);
	            return true; 
	        default :
	        	return true;
	    }
	    
	}
	
	public HolidaysDataSource getHolidaysBase(){
		return holidaysBase;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		Log.w("sdfsdfsd", "dfsdf");
		
//		Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
		return false;
	}
	


}
