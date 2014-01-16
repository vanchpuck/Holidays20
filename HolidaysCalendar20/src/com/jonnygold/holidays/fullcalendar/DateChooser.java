package com.jonnygold.holidays.fullcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class DateChooser extends LinearLayout{

	public DateChooser(Context context) {
		super(context);
	}
	
	public DateChooser(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public abstract boolean checkData();

	public abstract HolidayDate getDate();
}
