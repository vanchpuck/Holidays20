package com.jonnygold.holidays.calendar;

import java.io.IOException;

import android.content.Context;


public class HolidaysBaseHelper extends CopiedBaseHelper{

	public static final int DATABASE_VERSION = 20;
	
	public HolidaysBaseHelper(Context context) throws IOException{
		super(context, "mydb20", DATABASE_VERSION);
		
	}
	
}
