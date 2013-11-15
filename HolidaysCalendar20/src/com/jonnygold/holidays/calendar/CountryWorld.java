package com.jonnygold.holidays.calendar;

public class CountryWorld extends Country {

	public static final int ID = 1;
	
	@Override
	int getDrawableId() {
		return R.drawable.earth_s_1;
	}

	@Override
	public String getName() {
		return "World";
	}
	
	@Override
	int getId() {
		return ID;
	}
}
