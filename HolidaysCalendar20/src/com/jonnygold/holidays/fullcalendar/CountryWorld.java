package com.jonnygold.holidays.fullcalendar;

import android.content.Context;

import com.jonnygold.holidays.fullcalendar.R;

public class CountryWorld extends Country {

	public static final int ID = 1;
	
	public CountryWorld(Context context){
		super(ID, "Мировые праздники", context.getResources().getDrawable(R.drawable.flag_wrld));
	}
	
}
