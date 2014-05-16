package com.jonnygold.holidays.fullcalendar;

import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jonnygold.holidays.fullcalendar.holiday.Holiday;

public final class HolidaysListView extends ListView{

	private static class Wrapper extends ContextThemeWrapper {
		private Holiday holiday;
		
		public Wrapper(Context context, int style, Holiday holiday){
			super(context, style);
			this.holiday = holiday;
		}
		
		public void shareToCalendar(View view){
			GoogleCalendar.getInstance().addHoliday(this, holiday);
		}
	}
	
	public static class OnHolidayClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int idx,long id) {
			Holiday holiday = (Holiday) parent.getAdapter().getItem(idx);
			
			
			Wrapper wrapper = new Wrapper(view.getContext(), R.style.DialogBaseTheme, holiday);

			HolidayDetailView detailView = (HolidayDetailView)LayoutInflater.from(wrapper).inflate(R.layout.dialog_detail, null);
			detailView.setHoliday(holiday);
			
			Builder builder = new AlertDialog.Builder(wrapper);				
			builder.setPositiveButton("Ok", null)
				.setView(detailView) 
				.show();
		}
	}
	
	private Calendar calendar;
	
	public HolidaysListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setHolidays(List<Holiday> holidays){
		HolidaysAdapter listAdapter = new HolidaysAdapter(getContext(), holidays);
		setAdapter(listAdapter);
	}
	
	public void setCalendar(Calendar calendar){
		Calendar calend = Calendar.getInstance();
		calend.setTime(calendar.getTime());
		this.calendar = calend;
	}
	
	public Calendar getCalendar(){
		return calendar;
	}
		
}
