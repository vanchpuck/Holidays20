package com.jonnygold.holidays.calendar;

import android.content.Context;


public class HolidaysBaseHelper extends CopiedBaseHelper{

	public static final int DATABASE_VERSION = 1;
	
	public HolidaysBaseHelper(Context context){
		super(context, "mydb", DATABASE_VERSION);
		
	}
	
}
