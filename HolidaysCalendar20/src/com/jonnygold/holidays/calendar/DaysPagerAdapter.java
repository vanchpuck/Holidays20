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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

public class DaysPagerAdapter extends PagerAdapter{
	
	public static final int PAGE_COUNT = Integer.MAX_VALUE;
	public static final int START_POSITION = PAGE_COUNT>>1;
	
	
	private Calendar calendar;
	
	protected int previousPosition;
	protected int currentPosition;
	
	protected HolidaysDataSource holidaysBase;
	
	protected SharedPreferences sharedPref;
	
	protected Activity activity;
	
//	public DaysPagerAdapter(Calendar calendar, HolidaysDataSource holidaysBase){
//		this.calendar = Calendar.getInstance();
//		this.holidaysBase = holidaysBase;
//		this.previousPosition = START_POSITION;
////		sharedPref = PreferenceManager.getDefaultSharedPreferences(collection.getContext());
//	}
	
	public DaysPagerAdapter(Activity activity, HolidaysDataSource holidaysBase){
		this.previousPosition = START_POSITION;
		this.calendar = Calendar.getInstance();
		this.activity = activity;
		this.holidaysBase = holidaysBase;
		this.sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
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
	public Object instantiateItem(View collection, int position){
		
		Log.w("INSATANTIATE", "INSATANTIATE");
		
		currentPosition = position;
		
		// Set appropriate date to calendar
		calendar.add(Calendar.DAY_OF_YEAR, position - previousPosition);
		Log.w("Position", position+"");
		Log.w("ADD", position - previousPosition+" days");
		
		// Inflate view
		View page = activity.getLayoutInflater().inflate(R.layout.activity_holidays, null);
				
		// Get holidays from database
		final List<Holiday> holidays = getHolidays();
		
		// Retrieving ListView adapter
		HolidaysAdapter listAdapter = new HolidaysAdapter(collection.getContext(), holidays);
		
		// Configure holidays ListView
		ListView holidaysView = (ListView)page.findViewById(R.id.view_holidays);
		holidaysView.setAdapter(listAdapter);
		
		holidaysView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int idx,	long id) {
				Holiday holiday = holidays.get(idx);
				
				Builder builder = new AlertDialog.Builder(activity);				
				builder.setPositiveButton("Ок", null)
					.setView(getHolidayDetail(holiday)) 
					.show();
			}
		});
		
		previousPosition = position;
        
        ((ViewPager) collection).addView(page, 0);
        return page;
	}
	
	@Override
    public void destroyItem(View collection, int position, Object view){
    	Log.w("DEL", "DEL");
        ((ViewPager) collection).removeView((View) view);
    }
	
	public Calendar getCalendar(){
		return calendar;
	}
	
	public void setDate(Date date){
		calendar.setTime(date);
	}
//	
	public int getCurrentPosition() {
		return previousPosition;
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
		restriction.setCountryes(countryIdList);
		
		
		List<Holiday> holidays = holidaysBase.getHolidays(restriction);
		return holidays;
	}
	
	private final View getHolidayDetail(Holiday holiday){
		Log.w("Test", "0");
		View holidayDetail = activity.getLayoutInflater().inflate(R.layout.dialog_detail, null);
				
		((TextView) holidayDetail.findViewById(R.id.view_txt_holiday_date)).setText(holiday.getDateString());
		((TextView) holidayDetail.findViewById(R.id.view_txt_holiday_title)).setText(holiday.getTitle().toUpperCase(Locale.getDefault()));
		((ImageView) holidayDetail.findViewById(R.id.view_img_holiday_picture)).setImageDrawable(holiday.getDrawable());
		((TextView) holidayDetail.findViewById(R.id.view_txt_holiday_description)).setText(holiday.getDescription());
		
		int[] flagViews = new int[]{R.id.view_img_info_flag_1, R.id.view_img_info_flag_2, R.id.view_img_info_flag_3, R.id.view_img_info_flag_4};
		
		int i=0;
		ImageView flagView = null;
		for(Country country : holiday.getCountries()){
			flagView = (ImageView)holidayDetail.findViewById(flagViews[i]);
			flagView.setImageResource(country.getDrawableId());
			i++;
		}
		
		return holidayDetail;
	}

}
