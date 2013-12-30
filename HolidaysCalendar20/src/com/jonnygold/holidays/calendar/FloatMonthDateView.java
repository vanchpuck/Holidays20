package com.jonnygold.holidays.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class FloatMonthDateView extends DateChooser{

	private static class MonthAdapter extends ArrayAdapter<Month>{
		public MonthAdapter(Context context) {
			super(context, android.R.layout.simple_spinner_item, Month.values());
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}
	}
	
	private static class WeekDayAdapter extends ArrayAdapter<WeekDay>{

//		private static final String[] WEEK = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
		
		public WeekDayAdapter(Context context) {
			super(context, android.R.layout.simple_spinner_item, WeekDay.values());
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}
		
//		public static final String[] getWeekDays(){
//			return WEEK;
//		}
	}
	
//	private enum DayOrder{
//		FIRST("Первый(я)", 0),
//		SECOND("Второй(я)", 7),
//		THIRD("Третий(я)", 14),
//		FOURTH("Четвертый(я)", 21),
//		LAST("Последний(я)", -7);
//		
//		private int offset;
//		private String orderStr;
//		
//		private DayOrder(String orderStr, int offset){
//			this.offset = offset;
//			this.orderStr = orderStr;
//		}
//		
//		public int getOffset(){
//			return offset;
//		}
//		
//		@Override
//		public String toString() {
//			return orderStr;
//		}
//	}
	
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
