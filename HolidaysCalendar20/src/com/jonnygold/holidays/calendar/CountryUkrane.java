package com.jonnygold.holidays.calendar;

public class CountryUkrane extends Country {

	public static final int ID = 4;
	
	@Override
	int getDrawableId() {
		return R.drawable.ukrane_circle_s_1;
	}

	@Override
	public String getName() {
		return "Ukrane";
	}
	
}
