package com.jonnygold.holidays.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class HolidayListItem extends View{

	private Holiday holiday;
	
	public HolidayListItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public HolidayListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HolidayListItem(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(getContext());
	}

	

}
