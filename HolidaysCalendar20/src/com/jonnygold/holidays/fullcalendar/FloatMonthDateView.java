package com.jonnygold.holidays.fullcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jonnygold.holidays.fullcalendar.holiday.DayOrder;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayDate;
import com.jonnygold.holidays.fullcalendar.holiday.Month;
import com.jonnygold.holidays.fullcalendar.holiday.WeekDay;

public class FloatMonthDateView extends DateChooser{

	private static class MonthAdapter extends ArrayAdapter<Month>{
		public MonthAdapter(Context context) {
			super(context, android.R.layout.simple_spinner_item, Month.values());
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}
	}
	
	private static class WeekDayAdapter extends ArrayAdapter<WeekDay>{
		public WeekDayAdapter(Context context) {
			super(context, android.R.layout.simple_spinner_item, WeekDay.values());
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}
	}
	
	private static class OrderAdapter extends ArrayAdapter<DayOrder>{
		public OrderAdapter(Context context) {
			super(context, android.R.layout.simple_spinner_item, DayOrder.values());
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}
	}
	
	
	private Spinner monthSpinner;
	private Spinner weekDaySpinner;
	private Spinner orderSpinner;
	private TextView exampleText;
	
	public FloatMonthDateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public FloatMonthDateView(Context context) {
		super(context);
		init();
	}

	@Override
	public boolean checkData() {
		return true;
	}

	@Override
	public HolidayDate getDate() {
		HolidayDate.Builder builder = new HolidayDate.Builder();
		
		DayOrder order =  DayOrder.values()[orderSpinner.getSelectedItemPosition()];
		int offset = order.getOffset();
		int month = monthSpinner.getSelectedItemPosition();
		if(offset < 0){
			if(month == 11){
				builder.setFloaMonth(0);
			}
			else{
				builder.setFloaMonth(month+1);
			}
		}
		else{
			builder.setFloaMonth(month);
		}
		builder.setOffset(order)
				.setWeekDay(weekDaySpinner.getSelectedItemPosition());
		
		return builder.create();
	}

	private void init(){
		setPadding(20, 0, 0, 0);
		
		exampleText = new TextView(getContext());
		exampleText.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		exampleText.setTextSize(10);
		exampleText.setText("Например, Первый понедельник января...");
		
		monthSpinner = new Spinner(getContext());
		monthSpinner.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		monthSpinner.setPrompt("Укажите месяц");
		monthSpinner.setAdapter(new MonthAdapter(getContext()));
				
		weekDaySpinner = new Spinner(getContext());
		weekDaySpinner.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		weekDaySpinner.setPrompt("Укажите день недели");
		weekDaySpinner.setAdapter(new WeekDayAdapter(getContext()));
		
		orderSpinner = new Spinner(getContext());
		orderSpinner.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		orderSpinner.setPrompt("Укажите порядок");
		orderSpinner.setAdapter(new OrderAdapter(getContext()));
		
		addView(exampleText);
		addView(monthSpinner);
		addView(weekDaySpinner);
		addView(orderSpinner);
	}
	
}
