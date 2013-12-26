package com.jonnygold.holidays.calendar;

import java.util.Collection;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public final class HolidaysListView extends ListView{

	public static class OnHolidayClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int idx,long id) {
			Holiday holiday = (Holiday) parent.getAdapter().getItem(idx);
			
			
			ContextThemeWrapper wrapper = new ContextThemeWrapper(view.getContext(), R.style.DialogBaseTheme);

			HolidayDetailView detailView = (HolidayDetailView)LayoutInflater.from(wrapper).inflate(R.layout.dialog_detail, null);
			detailView.setHoliday(holiday);
			
			Builder builder = new AlertDialog.Builder(wrapper);				
			builder.setPositiveButton("Ok", null)
				.setView(detailView) 
				.show();
		}
	}
	
	
	
//	public static class HolidayDetailView extends View{
//
//		public HolidayDetailView(Context context, AttributeSet attrs) {
//			super(context, attrs);
//		}
//		
//		set
//		
//		public void setHoliday(Holiday holiday){
//			
//		}
//		
//	}
	
	public HolidaysListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setHolidays(List<Holiday> holidays){
		HolidaysAdapter listAdapter = new HolidaysAdapter(getContext(), holidays);
		setAdapter(listAdapter);
	}
	
}
