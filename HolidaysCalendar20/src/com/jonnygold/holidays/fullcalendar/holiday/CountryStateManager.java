package com.jonnygold.holidays.fullcalendar.holiday;

import com.jonnygold.holidays.fullcalendar.HolidaysDataSource;
import com.jonnygold.holidays.fullcalendar.holiday.Country;

import android.content.Context;

public class CountryStateManager {

	public enum State{
		INSTALLED ("Установленные календари"),
		NOT_INSTALLED ("Доступные календари");
		
		private String title;
		
		State(String title){
			this.title = title;
		}
		
		@Override
		public String toString() {
			return this.title;
		}
	}
	
	private HolidaysDataSource holidaysBase;
	
	@SuppressWarnings("unused")
	private Context context;
	
	public CountryStateManager(Context context){
		this.holidaysBase = HolidaysDataSource.newInstance(context);
		this.context = context;
	}
	
	public State getState(Country country){
		State result;
		holidaysBase.openForReading();
		if(holidaysBase.isExists(country))
			result = State.INSTALLED;
		else
			result = State.NOT_INSTALLED;
		holidaysBase.close();
		return result;
	}
	
//	private boolean checkService(Country country){
//		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//	        if (UpdateSeviceTest.class.getName().equals(service.service.getClassName())) {
//	            return true;
//	        }
//	    }
//	    return false;
//	}
	
}
