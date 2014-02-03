package com.jonnygold.holidays.fullcalendar;

import android.content.Context;

import com.jonnygold.holidays.fullcalendar.R;

public class CountryUser extends Country{

	public static final int ID = 5;
	
	public CountryUser(Context context){
		super(ID, "Праздники Пользователя", context.getResources().getDrawable(R.drawable.flag_user));
	}
	
}
