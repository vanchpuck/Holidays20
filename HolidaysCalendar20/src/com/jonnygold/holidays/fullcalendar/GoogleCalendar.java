package com.jonnygold.holidays.fullcalendar;

import java.util.Calendar;

import com.jonnygold.holidays.fullcalendar.holiday.Holiday;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

public class GoogleCalendar {

	private static GoogleCalendar instance;
	
	private GoogleCalendar(){}
	
	public static GoogleCalendar getInstance(){
		if(instance == null){
			instance = new GoogleCalendar();
		}
		return instance;
	}
	
	public void addHoliday(Context context, Holiday holiday) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MONTH, holiday.getDate().getActualMonth());
		date.set(Calendar.DAY_OF_MONTH, holiday.getDate().getActualDay()+1);
		Intent intent = new Intent(Intent.ACTION_EDIT)
	        .setType("vnd.android.cursor.item/event")
	        .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
	        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTimeInMillis())
	        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.getTimeInMillis())
	        .putExtra(Events.TITLE, holiday.getTitle())
	        .putExtra(Events.DESCRIPTION, holiday.getDescription());
		context.startActivity(intent);
	}
	
}
