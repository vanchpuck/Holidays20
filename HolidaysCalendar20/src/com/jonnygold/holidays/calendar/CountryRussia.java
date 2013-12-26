package com.jonnygold.holidays.calendar;

public class CountryRussia extends Country {

	public static final int ID = 2;
		
	@Override
	public int getDrawableId() {
		return R.drawable.flag_rus;
	}
	
	@Override
	public String getName() {
		return "Russia";
	}

	@Override
	public int getId() {
		return ID;
	}

}
