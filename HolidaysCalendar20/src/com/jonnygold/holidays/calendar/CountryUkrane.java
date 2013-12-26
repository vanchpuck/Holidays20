package com.jonnygold.holidays.calendar;

public class CountryUkrane extends Country {

	public static final int ID = 4;
	
	@Override
	public int getDrawableId() {
		return R.drawable.flag_ukr;
	}

	@Override
	public String getName() {
		return "Ukrane";
	}
	
	@Override
	public int getId() {
		return ID;
	}
}
