package com.jonnygold.holidays.fullcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jonnygold.holidays.fullcalendar.HolidaysDataSource.QueryRestriction;
import com.jonnygold.holidays.fullcalendar.holiday.CountryBelorussia;
import com.jonnygold.holidays.fullcalendar.holiday.CountryRussia;
import com.jonnygold.holidays.fullcalendar.holiday.CountryUkrane;
import com.jonnygold.holidays.fullcalendar.holiday.CountryUser;
import com.jonnygold.holidays.fullcalendar.holiday.CountryWorld;
import com.jonnygold.holidays.fullcalendar.holiday.Holiday;
import com.jonnygold.holidays.fullcalendar.holiday.Month;

public final class DaysPagerAdapter extends PagerAdapter{
	
	public static final int PAGE_COUNT = Integer.MAX_VALUE;
	public static final int START_POSITION = 10000;
	
	
	private Calendar calendar;
	
	private HolidaysDataSource holidaysBase;
	
	private SharedPreferences sharedPref;
	
	private Activity activity;
	
	private Date initialDate;
	
	private int year;
	
	private Calendar currItem;

	public DaysPagerAdapter(Activity activity, HolidaysDataSource holidaysBase, Calendar calendar){
		this.calendar = calendar;
		this.year = calendar.get(Calendar.YEAR);
		this.activity = activity;
		this.holidaysBase = holidaysBase;
		this.sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
		this.initialDate = calendar.getTime();
	}
	
	@Override
	public int getCount() {
		return PAGE_COUNT;
	}
	
	@Override
    public boolean isViewFromObject(View view, Object object){
        return view.equals(object);
    }

	@Override
	public CharSequence getPageTitle(int position) {
		calendar.setTime(initialDate);
		calendar.add(Calendar.DAY_OF_YEAR, position - START_POSITION);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String month = Month.values()[calendar.get(Calendar.MONTH)].getGenitive();
		return day+" "+month;
	}
	
//	@Override
//	public void setPrimaryItem(ViewGroup container, int position, Object object) {
//		currItem = Calendar.getInstance();
//		currItem.setTime(calendar.getTime());
//		//super.setPrimaryItem(container, position, object);
//	}
		
	@Override
	public Object instantiateItem(View collection, int position){
	
		// Set appropriate date to calendar
		calendar.setTime(initialDate);
		calendar.add(Calendar.DAY_OF_YEAR, position - START_POSITION);
		
		int currYear = calendar.get(Calendar.YEAR);
		if(currYear != year){
			holidaysBase.updateFloatHolidays(currYear);
		}
		year = currYear;
		
		// Inflate view
		View page = activity.getLayoutInflater().inflate(R.layout.view_holidays_list, null);
		page.setTag(position);
		
		// Get holidays from database
		final List<Holiday> holidays = getHolidays();
		
		// Configure holidays ListView
		HolidaysListView holidaysView = (HolidaysListView)page.findViewById(R.id.view_holidays);
//		holidaysView.setAdapter(listAdapter);
		
		holidaysView.setHolidays(holidays);
		holidaysView.setCalendar(calendar);
		holidaysView.setOnItemClickListener(new HolidaysListView.OnHolidayClickListener());
		
		activity.registerForContextMenu(holidaysView);
        
        ((ViewPager) collection).addView(page, 0);
        return page;
	}
	
	@Override
    public void destroyItem(View collection, int position, Object view){
        ((ViewPager) collection).removeView((View) view);
    }
//	
	public Calendar getCurrentView(){
		return currItem;
	}
	
//	public Calendar getCalendar(){
//		return calendar;
//	}
	
	public void setDate(Date date){
		calendar.setTime(date);
	}
	
	private List<Holiday> getHolidays(){
		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
		restriction.setDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		
		
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
		sharedPref.edit().putBoolean("key_world_holidays", false).apply();
		restriction.setCountryes(countryIdList);
		
		List<Holiday> holidays = holidaysBase.getHolidays(restriction);
		return holidays;
	}

}
