package com.jonnygold.holidays.fullcalendar;

import android.content.Context;

import com.jonnygold.holidays.fullcalendar.R;

public class CountryUkrane extends Country {

	public static final int ID = 4;
	
	public CountryUkrane(Context context){
		super(ID, "Праздники Украины", context.getResources().getDrawable(R.drawable.flag_ukr));
	}
	
}
