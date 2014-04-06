package com.jonnygold.holidays.fullcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jonnygold.holidays.fullcalendar.holiday.HolidayDate;
import com.jonnygold.holidays.fullcalendar.holiday.Month;

public class StableDateView extends DateChooser{

		
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
		return true;
	}

	@Override
	public HolidayDate getDate() {
		HolidayDate.Builder builder = new HolidayDate.Builder();
		HolidayDate date = builder
				.setActualMonth(monthSpinner.getSelectedItemPosition())
				.setActualDay(daySpinner.getSelectedItemPosition()+1)
				.create()
		;
		return date;
	}
	
}
