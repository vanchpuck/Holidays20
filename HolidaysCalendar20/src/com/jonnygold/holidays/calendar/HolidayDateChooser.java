package com.jonnygold.holidays.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jonnygold.holidays.calendar.holiday.HolidayDate;
import com.jonnygold.holidays.calendar.R;

public final class HolidayDateChooser extends LinearLayout{

	private Spinner spinner;
	private DateChooser chooser;
	
	public HolidayDateChooser(Context context) {
		super(context);
		init();
	}
	
	public HolidayDateChooser(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(getContext(), R.array.holiday_date_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner = new Spinner(getContext());
		spinner.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos)
				DateChooser dateChooser = null;
				if("Фиксированная дата".equals(parent.getItemAtPosition(pos).toString())){
					dateChooser = (StableDateView) inflate(getContext(), R.layout.view_stable_date, null);
				}
				else if("Плавающая дата (месяц)".equals(parent.getItemAtPosition(pos).toString())){
					dateChooser = (FloatMonthDateView) inflate(getContext(), R.layout.view_float_month_date, null);
				}
				else if("Плавающая дата (год)".equals(parent.getItemAtPosition(pos).toString())){
					dateChooser = (FloatYearDateView) inflate(getContext(), R.layout.view_float_year_date, null);
				}
				setDateChooser(dateChooser);
		    }

			@Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
				
		    }
			
		});
		this.addView(spinner);

	}
	
	public HolidayDate getDate(){
		if(chooser.checkData()){
			return chooser.getDate();
		}
		return null;
	}
	
	
	private void setDateChooser(DateChooser dateChooser){
		clearDateView();
		chooser = dateChooser;
		addView(chooser);
	}
	
	private void clearDateView(){
		if(getChildAt(1) != null){
			removeViewAt(1);
		}
	}
	
}
