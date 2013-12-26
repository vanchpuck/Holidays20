package com.jonnygold.holidays.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.jonnygold.holidays.calendar.HolidaysDataSource.QueryRestriction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public final class DaysPagerAdapter extends PagerAdapter{
	
	public static final int PAGE_COUNT = Integer.MAX_VALUE;
	public static final int START_POSITION = 10000;
	
	
	private Calendar calendar;
	
	private int previousPosition;
	private int currentPosition;
	
	private HolidaysDataSource holidaysBase;
	
	private SharedPreferences sharedPref;
	
	private Activity activity;
	
	private Date initialDate;

	public DaysPagerAdapter(Activity activity, HolidaysDataSource holidaysBase, Calendar calendar){
		this.previousPosition = START_POSITION;
		this.calendar = calendar;
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
	
	@Override
	public Object instantiateItem(View collection, int position){
	
		// Set appropriate date to calendar
		calendar.setTime(initialDate);
		calendar.add(Calendar.DAY_OF_YEAR, position - START_POSITION);
		
		// Inflate view
		View page = activity.getLayoutInflater().inflate(R.layout.activity_holidays, null);
				
		// Get holidays from database
		final List<Holiday> holidays = getHolidays();
		
		// Configure holidays ListView
		HolidaysListView holidaysView = (HolidaysListView)page.findViewById(R.id.view_holidays);
//		holidaysView.setAdapter(listAdapter);
		
		holidaysView.setHolidays(holidays);
		holidaysView.setOnItemClickListener(new HolidaysListView.OnHolidayClickListener());
		
		activity.registerForContextMenu(holidaysView);
        
        ((ViewPager) collection).addView(page, 0);
        return page;
	}
	
	@Override
    public void destroyItem(View collection, int position, Object view){
        ((ViewPager) collection).removeView((View) view);
    }
	
	public Calendar getCalendar(){
		return calendar;
	}
	
	public void setDate(Date date){
		calendar.setTime(date);
	}
	
	private List<Holiday> getHolidays(){
		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
		restriction.setDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		
		
		List<Integer> countryIdList = new ArrayList<Integer>(4);		
		if(sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)){
			Log.w("WorldCountry", sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)+"");
			countryIdList.add(CountryWorld.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)){
			Log.w("RusCountry", sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)+"");
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
		
		holidaysBase.openForReading();
		List<Holiday> holidays = holidaysBase.getHolidays(restriction);
		return holidays;
	}

	
//	private final View getHolidayDetail(Holiday holiday){
//		Log.w("Test", "0");
//		View holidayDetail = activity.getLayoutInflater().inflate(R.layout.dialog_detail, null);
//				
//		((TextView) holidayDetail.findViewById(R.id.view_txt_holiday_date)).setText(holiday.getDateString());
//		((TextView) holidayDetail.findViewById(R.id.view_txt_holiday_title)).setText(holiday.getTitle().toUpperCase(Locale.getDefault()));
//		((ImageView) holidayDetail.findViewById(R.id.view_img_holiday_picture)).setImageDrawable(holiday.getDrawable());
//		((TextView) holidayDetail.findViewById(R.id.view_txt_holiday_description)).setText(holiday.getDescription());
//		
//		int[] flagViews = new int[]{R.id.view_img_info_flag_1, R.id.view_img_info_flag_2, R.id.view_img_info_flag_3, R.id.view_img_info_flag_4};
//		
//		int i=0;
//		ImageView flagView = null;
//		for(Country country : holiday.getCountries()){
//			flagView = (ImageView)holidayDetail.findViewById(flagViews[i]);
//			flagView.setImageResource(country.getDrawableId());
//			i++;
//		}
//		
//		return holidayDetail;
//	}

}
