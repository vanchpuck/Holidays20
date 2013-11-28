package com.jonnygold.holidays.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

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

				if("������������� ����".equals(parent.getItemAtPosition(pos).toString())){
					StableDateView sdv = (StableDateView) inflate(getContext(), R.layout.view_stable_date, null);
					setDateChooser(sdv);
				}
				else if("��������� ���� (�����)".equals(parent.getItemAtPosition(pos).toString())){
					clearDateView();
				}
				else if("��������� ���� (���)".equals(parent.getItemAtPosition(pos).toString())){
					clearDateView();
				}
		    }

			@Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
				
		    }
			
		});
		this.addView(spinner);

	}
	
	public ContentValues getDateInfo(){
		return chooser.getValues();
	}
	
	public String
	
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
