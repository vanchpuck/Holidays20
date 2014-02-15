package com.jonnygold.holidays.fullcalendar;

public class CountryKazakhstan extends Country {

	public static final int ID = 6;
	
	@Override
	public int getDrawableId() {
		return R.drawable.flag_rus;
	}
	
	@Override
	public String getName() {
		return "Kazachstan";
	}

	@Override
	public int getId() {
		return ID;
	}

}
