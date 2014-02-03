package com.jonnygold.holidays.fullcalendar;

import android.content.Context;

import com.jonnygold.holidays.fullcalendar.R;

public class CountryBelorussia extends Country {

	public static final int ID = 3;
	
	public CountryBelorussia(Context context){
		super(ID, "Праздники Белоруссии", context.getResources().getDrawable(R.drawable.flag_bel));
	}
	
}
