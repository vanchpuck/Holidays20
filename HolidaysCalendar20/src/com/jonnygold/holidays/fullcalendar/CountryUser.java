package com.jonnygold.holidays.fullcalendar;

import com.jonnygold.holidays.fullcalendar.R;

public class CountryUser extends Country{

public static final int ID = 5;
	
	@Override
	public int getDrawableId() {
		return R.drawable.flag_user;
	}

	@Override
	public String getName() {
		return "User";
	}
	
	@Override
	public int getId() {
		return ID;
	}

}
