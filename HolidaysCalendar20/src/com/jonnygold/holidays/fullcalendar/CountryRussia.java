package com.jonnygold.holidays.fullcalendar;

import android.content.Context;

import com.jonnygold.holidays.fullcalendar.R;

public final class CountryRussia extends Country {

	public static final int ID = 2;
	
	public CountryRussia(Context context){
		super(ID, "Праздники России", context.getResources().getDrawable(R.drawable.flag_rus));
	}

}
