package com.jonnygold.holidays.fullcalendar.holiday;

import com.jonnygold.holidays.fullcalendar.R;

public class CountryWorld extends Country {

	public static final int ID = 1;
	
	@Override
	public int getDrawableId() {
		return R.drawable.flag_wrld;
	}

	@Override
	public String getName() {
		return "World";
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
