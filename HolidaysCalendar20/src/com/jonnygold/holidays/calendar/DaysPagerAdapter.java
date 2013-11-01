package com.jonnygold.holidays.calendar;

import java.util.Calendar;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DaysPagerAdapter extends PagerAdapter{
	
	public static final int PAGE_COUNT = Integer.MAX_VALUE;
	public static final int START_POSITION = PAGE_COUNT>>1;
	
	
	private Calendar calendar;
	
	protected LayoutInflater inflater ;
	
	protected HolidaysDataSource holidaysBase;
	
	protected int previousPosition;
	
	public DaysPagerAdapter(LayoutInflater inflater, HolidaysDataSource holidaysBase){
		this.inflater = inflater;
		this.holidaysBase = holidaysBase;
		calendar = Calendar.getInstance();
		previousPosition = START_POSITION;
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
		
		// Set appropriate date to calendar
		calendar.add(Calendar.DAY_OF_YEAR, position - previousPosition);

//		Log.w("DATE", calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH));
		
		// Inflate view
		View page = inflater.inflate(R.layout.activity_holidays, null);
				
		// Retrieve adapter manager
		HolidaysAdapterManager adapterManager = HolidaysAdapterManager.getInstance(collection.getContext());
		
		// Get holidays from database
		holidaysBase.openForReading();
		List<Holiday> holidays = holidaysBase.getHolidays(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		holidaysBase.close();
		
		// Configure holidays ListView
		ListView holidaysView = (ListView)page.findViewById(R.id.view_holidays);
		SimpleAdapter adapter = adapterManager.getAdapter(holidays);
		adapter.setViewBinder(adapterManager.getBinder());
		holidaysView.setAdapter(adapter);
		
		
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

}
