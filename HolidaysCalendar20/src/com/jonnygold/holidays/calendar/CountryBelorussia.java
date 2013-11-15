package com.jonnygold.holidays.calendar;

public class CountryBelorussia extends Country {

	public static final int ID = 3;
	
	@Override
	int getDrawableId() {
		return R.drawable.bel_circle_s_1;
	}

	@Override
	public String getName() {
		return "Belorussia";
	}
	
	@Override
	int getId() {
		return ID;
	}

}
