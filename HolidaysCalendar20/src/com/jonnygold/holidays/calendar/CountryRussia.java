package com.jonnygold.holidays.calendar;

public class CountryRussia extends Country {

	public static final int ID = 2;
		
	@Override
	int getDrawableId() {
		return R.drawable.russia_circle_s_1;
	}
	
	@Override
	public String getName() {
		return "Russia";
	}

	@Override
	int getId() {
		return ID;
	}

}
