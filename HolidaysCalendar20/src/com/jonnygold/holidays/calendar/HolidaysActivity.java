package com.jonnygold.holidays.calendar;


import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HolidaysActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holidays);
		
		HolidaysDataSource holidaysBase = HolidaysDataSource.getInstance(this);
//		holidaysBase.openForReading();
//		
//		
//		List<Holiday> holidays = holidaysBase.getHolidays(11, 31);
//		
//		holidaysBase.close();
//		
//		HolidaysAdapterManager adapterManager = HolidaysAdapterManager.getInstance(this);
//		
//		ListView holidaysView = (ListView)findViewById(R.id.view_holidays);
//		
//		SimpleAdapter adapter = adapterManager.getAdapter(holidays);
//		adapter.setViewBinder(adapterManager.getBinder());
//		
//		holidaysView.setAdapter(adapter);	
		
		Calendar c = Calendar.getInstance();
		c.set(2013, 9, 15);
		
		DaysPagerAdapter pagerAdapter = new DaysPagerAdapter(LayoutInflater.from(this), holidaysBase);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSaveEnabled(false);
        viewPager. setCurrentItem(DaysPagerAdapter.START_POSITION);     
        
        setContentView(viewPager);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.holidays, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            return true;
	        default :
	        	return true;
	    }
	    
	}

}
