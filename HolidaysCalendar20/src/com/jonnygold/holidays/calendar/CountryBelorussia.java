package com.jonnygold.holidays.calendar;

public class CountryBelorussia extends Country {

	public static final int ID = 3;
	
	@Override
	public int getDrawableId() {
		return R.drawable.flag_bel;
	}

	@Override
	public String getName() {
		return "Belorussia";
	}
	
	@Override
	public int getId() {
		return ID;
	}

}
