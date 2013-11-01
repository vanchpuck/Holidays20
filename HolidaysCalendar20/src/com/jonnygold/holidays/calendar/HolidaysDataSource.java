package com.jonnygold.holidays.calendar;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.BaseAdapter;

public class HolidaysDataSource {

	private final static int COL_HLIDAY_TITLE = 0;
	private final static int COL_HOLIDAY_DESCRIPTION = 1;
	private final static int COL_HOLIDAY_DATE = 2;
	private final static int COL_HOLIDAY_DAY = 3;
	private final static int COL_PRIORITY_ID = 4;
	private final static int COL_IMAGE = 5;
	private final static int COL_M_FLOAT_MONTH = 6;
	private final static int COL_M_FLOAT_WEEKDAY = 7;
	private final static int COL_M_FLOAT_DAY_OFFSET = 8;
	private final static int COL_Y_FLOAT_DAY = 9;
	private final static int COL_HOLIDAY_ID = 10;
	
	private final static String HOLIDAYS_QUERY = 
			"SELECT " 																+
			"		TH.title " 														+
			"	,	TH.description " 												+
			"	,	TH.actualDateStr " 												+
			"	,	TH.day " 														+
			"	,	TP._id "														+
			"	,	TI.image " 														+
			"	,	TMFH.month "													+
			"	, 	TMFH.weekDay "													+
			"	,	TMFH.dayOffset "												+
			"	,	TYFH.day "														+
			"	,	TH._id "														+
			"FROM " 																+
			"		T_HOLIDAYS TH " 												+
			"	LEFT JOIN T_Images TI ON TH.id_image = TI._id "						+
			"	LEFT JOIN T_Priority TP ON TH.id_priority = TP._id "				+
			"	LEFT JOIN T_MonthFloatHolidays TMFH ON TH._id = TMFH.id_holiday "	+
			"	LEFT JOIN T_YearFloatHolidays TYFH ON TH._id = TYFH.id_holiday "
	;
	
	private final static String HOLIDAYS_DATE_TERMS = 
			"WHERE " 													+
			"		TH.month = ? " 										+
			"	AND TH.day = ? " 
	;
	
	private final static String HOLIDAYS_LIMIT = 
			"LIMIT 20 " 
	;
	
	
	private SQLiteDatabase db;
	
	private static HolidaysBaseHelper dbHelper;
	
	private static HolidaysDataSource dataSource;
	
	private HolidaysDataSource(Context context){
		dbHelper = new HolidaysBaseHelper(context);
	}
	
	public static HolidaysDataSource getInstance(Context context){
		if(dbHelper == null){
			return new HolidaysDataSource(context);
		}
		return dataSource;
	}
	
	public boolean openForReading() {
		try{
			db = dbHelper.getReadableDatabase();
			return true;
		}catch(SQLiteException exc){
			return false;
		}
	}
	
	
	
	public boolean openForWriting() {
		try{	
			db = dbHelper.getWritableDatabase();
			return true;
		}catch(SQLiteException exc){
			return false;
		}
	}
	
	public void close(){
		db.close();
	}
	
	
	public List<Holiday> getHolidays(){
		// Prepare query
		String query = HOLIDAYS_QUERY + HOLIDAYS_LIMIT;
		Log.w("QUERY", query);
		
		// Execute query
		Cursor c = db.rawQuery(query, new String[]{});
		
		List<Holiday> holidays = getHolidays(c);
		
		c.close();
		
		return holidays;
	}
	
	
	public List<Holiday> getHolidays(int nonth, int day){
		// Prepare query
		String query = HOLIDAYS_QUERY + HOLIDAYS_DATE_TERMS;
		Log.w("QUERY", query);
		
		// Execute query
		Cursor c = db.rawQuery(query, new String[]{String.valueOf(nonth), String.valueOf(day)});
		
		List<Holiday> holidays = getHolidays(c);
		
		c.close();
		
		return holidays;
	}	
	
	
	private List<Holiday> getHolidays(Cursor cursor){
		
		List<Holiday> holidays = new ArrayList<Holiday>();
		
		// Prepare temporary objects
		Holiday holiday = null;
		List<Country> countries = null;
		ByteArrayInputStream inStream = null;
		Drawable icon = null;
		
		// Put holidays into the Set
		while(cursor.moveToNext()){
			// Get countries where holiday is celebrated
			countries = getCountries(cursor.getInt(COL_HOLIDAY_ID));
			
			inStream = new ByteArrayInputStream(cursor.getBlob(COL_IMAGE));
			icon = new BitmapDrawable(BitmapFactory.decodeStream(inStream)); 
			
			// Construct Holiday object
			holiday = new Holiday(
					cursor.getInt(COL_HOLIDAY_ID), 
					cursor.getString(COL_HLIDAY_TITLE), 
					cursor.getString(COL_HOLIDAY_DATE), 
					cursor.getInt(COL_PRIORITY_ID), 
					icon, 
					cursor.getString(COL_HOLIDAY_DESCRIPTION), 
					countries
			);
									
			holidays.add(holiday);
		}
		
		try{inStream.close();}catch(Exception exc){}
		
		return holidays;
	}
	
	
	private List<Country> getCountries(int idHoliday){
		List<Country> countries = new ArrayList<Country>();
		
		// Prepare query
		String query = 
				"SELECT " 													+
				"		TCH.id_country " 									+				
				"FROM " 													+
				"		T_CountryHolidays TCH " 							+
				"WHERE "													+
				"		TCH.id_holiday = ? "								+
				"ORDER BY "													+
				"		TCH.id_country "									
		;
		Cursor c = db.rawQuery(query, new String[]{String.valueOf(idHoliday)});
				
		// Fill the collection of countries 
		while(c.moveToNext()){
			countries.add(CountryManager.getCountry(c.getInt(0)));
		}
		
		c.close();
		
		return countries;
	}
	
}
