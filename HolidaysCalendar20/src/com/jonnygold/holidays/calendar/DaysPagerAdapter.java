package com.jonnygold.holidays.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jonnygold.holidays.calendar.HolidaysDataSource.QueryRestriction;
import com.jonnygold.holidays.calendar.holiday.Country;
import com.jonnygold.holidays.calendar.holiday.CountryManager;
import com.jonnygold.holidays.calendar.holiday.Holiday;
import com.jonnygold.holidays.calendar.holiday.Month;
import com.jonnygold.holidays.calendar.R;

public final class DaysPagerAdapter extends PagerAdapter{
	
	public static final int PAGE_COUNT = Integer.MAX_VALUE;
	public static final int START_POSITION = 10000;
	
	
	private Calendar calendar;
	
	private HolidaysDataSource holidaysBase;
	
	private SharedPreferences sharedPref;
	
	private Activity activity;
	
	private Date initialDate;
	
	private int year;

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
		if(position < 5){
			return null;
		}
		calendar.setTime(initialDate);
		calendar.add(Calendar.DAY_OF_YEAR, position - START_POSITION);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String month = Month.values()[calendar.get(Calendar.MONTH)].getGenitive();
		return day+" "+month;
	}
			
	@Override
	public Object instantiateItem(View collection, int position){
		// Добавим проверку, чтобы избежать инициализации двух первых страниц
		if(position < 5){
			return null;
		}
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
		final List<Holiday> holidays = /*Collections.emptyList();*/getHolidays();
		
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

	public void setDate(Date date){
		calendar.setTime(date);
	}
	
	private List<Holiday> getHolidays(){
		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
		restriction.setDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		
		
		List<Integer> countryIdList = new ArrayList<Integer>(8);		
		for(Country country : CountryManager.getInstance().getCountries()){
			if(sharedPref.getBoolean(country.getKey(), true)){
				countryIdList.add(country.getId());
			}
		}
		restriction.setCountryes(countryIdList);
		
//		if(!holidaysBase.isOpen()){
//			holidaysBase.openForReading();
//		}
		if(holidaysBase.isOpen()){
			return holidaysBase.getHolidays(restriction);
		}
		return Collections.emptyList();
	}

}
