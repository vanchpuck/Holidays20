package com.jonnygold.holidays.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class StableDateView extends DateChooser{

	public enum Month{
		JANUARY("Январь", 31),
		FEBRARY("Февраль", 29),
		MARCH("Март", 31),
		APRIL("Апрель", 30),
		MAY("Май", 31),
		JUNE("Июнь", 30),
		JULY("Июль", 31),
		AUGUST("Август", 31),
		SEPTEMBER("Сентябрь", 30),
		OCTOBER("Октябрь", 31),
		NOVEMBER("Ноябрь", 30),
		DECEMBER("Декабрь", 31);
		
		private String name;
		private int dayCount;
		
		private Month(String name, int dayCount){
			this.name = name;
			this.dayCount = dayCount;
		}
		
		public String[] getDays(){
			String[] days = new String[dayCount];
			for(int i=0; i<dayCount; i++){
				days[i] = String.valueOf(i+1);
			}
			return days;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
	}
	
	public static class MonthAdapter extends ArrayAdapter<Month>{

		public MonthAdapter(Context context) {
			super(context, android.R.layout.simple_spinner_item, Month.values());
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}
		
		
	}
	
	public static class DaysAdapter extends ArrayAdapter<String>{

		public DaysAdapter(Context context, String[] days) {
			super(context, android.R.layout.simple_spinner_item, days);
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}
		
		
	}
	
	
	private Spinner monthSpinner;
	private Spinner daySpinner;
	private TextView exampleText;
	
	public StableDateView(Context context) {
		super(context);
		init();
	}
	
	public StableDateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		setPadding(20, 0, 0, 0);
		
		exampleText = new TextView(getContext());
		exampleText.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		exampleText.setTextSize(10);
		exampleText.setText("Например, 1 января...");
		
//		monthSpinner = (Spinner) findViewById(R.id.spinner_stable_month);
		monthSpinner = new Spinner(getContext());
		monthSpinner.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		monthSpinner.setPrompt("Укажите месяц");
		
		daySpinner = new Spinner(getContext());
		daySpinner.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		daySpinner.setPrompt("Укажите день");
		
		
		monthSpinner.setAdapter(new MonthAdapter(getContext()));
		
		monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos)
				String[] days = ((Month)parent.getItemAtPosition(pos)).getDays();
				daySpinner.setAdapter(new DaysAdapter(getContext(), days));
				
		    }

			@Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
			
		});
		
		addView(exampleText);
		addView(monthSpinner);
		addView(daySpinner);
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ContentValues getValues() {
		ContentValues values = new ContentValues(2);
		values.put("stableMonth", monthSpinner.getSelectedItemPosition());
		values.put("stableDay", daySpinner.getSelectedItemPosition());
		return values;
	}

	@Override
	public String getType() {
		return null;
	}
	
}
