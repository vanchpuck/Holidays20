package com.jonnygold.holidays.calendar;


import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
		holidaysBase.openForReading();
		
		calendar = Calendar.getInstance();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		holidaysBase.updateFloatHolidays(calendar.get(Calendar.YEAR));
		
		DaysPagerAdapter pagerAdapter = new DaysPagerAdapter(this, holidaysBase);
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
	public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.holidays, menu);

	  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	  
	  
	  MenuItem searchItem = menu.findItem(R.id.action_search);
	  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//	  if (searchView != null) {
//	     searchView.setOnQueryTextListener(this);
//	  }
	  
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
	        case R.id.action_preferences:
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            return true; 
//	        case R.id.action_search :
//	        	Intent intent = new Intent(this, SearchActivity.class);
//	            startActivity(intent);
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
		
		Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
		return false;
	}
	


}
