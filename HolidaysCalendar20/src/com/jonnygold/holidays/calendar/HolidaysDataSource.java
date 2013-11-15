package com.jonnygold.holidays.calendar;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
			"SELECT DISTINCT" 																+
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
			"	,	TH.month "														+
			"FROM " 																+
			"		T_HOLIDAYS TH " 												+
			"	LEFT JOIN T_Images TI ON TH.id_image = TI._id "						+
			"	LEFT JOIN T_Priority TP ON TH.id_priority = TP._id "				+
			"	LEFT JOIN T_MonthFloatHolidays TMFH ON TH._id = TMFH.id_holiday "	+
			"	LEFT JOIN T_YearFloatHolidays TYFH ON TH._id = TYFH.id_holiday "	+
			"	LEFT JOIN T_CountryHolidays TCH ON TH._id = TCH.id_holiday "
	;
	
	private final static String HOLIDAYS_DATE_TERMS = 
			"WHERE " 													+
			"		TH.month = ? " 										+
			"	AND TH.day = ? " 
	;
	
	private final static String HOLIDAYS_LIMIT = 
			"LIMIT 20 " 
	;
	
	public static class QueryRestriction{
		
		private StringBuilder restriction = new StringBuilder(10);
		private List<Integer>countries = Collections.emptyList();
		private Integer month;
		private Integer day;
		private Integer limit;
		
		public QueryRestriction(){
			restriction.append("WHERE 1=1 ");
		}
		
		public QueryRestriction setCountryes(List<Integer> countries){
			this.countries = countries;
//			for(Iterator<Country> itr=countries.iterator(); itr.hasNext(); ){
//				countryIdList.add(itr.next().getId());
//			}
			return this;
		}
		
		public QueryRestriction setDate(int month, int day){
			this.month = Integer.valueOf(month);
			this.day = Integer.valueOf(day);
			return this;
		}
		
		public QueryRestriction setLimit(int limit){
			this.limit = Integer.valueOf(limit);
			return this;
		}
		
		private String getWhereClause(){
			restriction.append("AND ( 0>1 ");
			for(Iterator<Integer> itr=countries.iterator(); itr.hasNext(); ){
				restriction.append("OR TCH.id_country = "+itr.next()+" ");
			}
			restriction.append(") ");
			
			if(month != null && day != null){
				restriction.append("AND TH.month = "+month+" AND TH.day = "+day);
			}
			
			if(limit != null){
				restriction.append("LIMIT "+limit);
			}
			
			return restriction.toString();
		}
		
	}
	
	
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
	
	public List<Holiday> getHolidays(QueryRestriction restriction){
		// Prepare query
		String query = HOLIDAYS_QUERY;
		if(restriction != null){
			query += restriction.getWhereClause();
		}
		Log.w("QUERY", query);
		
		// Execute query
		Cursor c = db.rawQuery(query, new String[]{});
		
		List<Holiday> holidays = getHolidays(c);
		
		c.close();
		
		return holidays;
	}	
	
	public void updateFloatHolidays(int year){
		updateEasterDate(year);
		updateMonthFloatDates(year);
		updateYearFloatDates(year);
	}
	
	public void updateMonthFloatDates(int year){
		String query = 
				"UPDATE " 																																			+
				"		t_holidays " 																																+
				"SET " 																																				+
				"		day = ( " 																																	+
				"				SELECT " 																															+
				"						strftime('%d', date('"+year+"-01-01','+'||month||' months','weekday '||weekDay||'', ''||dayOffset||' days')) " 				+
				"				FROM " 																																+
				"						t_MonthFloatHolidays " 																										+
				"				WHERE " 																															+
				"						id_holiday = t_holidays._id " 																								+
				"		) " 																																		+
				"	,	month = ( " 																																+
				"				SELECT " 																															+
				"						CASE WHEN dayOffset < 0 THEN month-1 "				 																		+
				"							WHEN dayOffset >= 0 THEN month" 																						+
				"						END" 																														+
				"				FROM " 																																+
				"						t_MonthFloatHolidays " 																										+
				"				WHERE " 																															+
				"						id_holiday = t_holidays._id " 																								+
				"		) " 																																		+
				"WHERE " 																																			+
				"		_id in ( " 																																	+
				"				SELECT " 																															+
				"						id_holiday " 																												+
				"				FROM " 																																+
				"						t_MonthFloatHolidays "																							 			+
				"		) "
		;	
		Cursor cursor = db.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		cursor.close();
	}
	
	public void updateYearFloatDates(int year){
		String query = 
				"UPDATE " 																				+
				"		t_holidays " 																	+
				"SET " 																					+
				"		day = ( " 																		+
				"				SELECT " 																+
				"						strftime('%d', date('"+year+"-01-01','+'||day||' days')) " 		+
				"				FROM " 																	+
				"						t_YearFloatHolidays " 											+
				"				WHERE " 																+
				"						id_holiday = t_holidays._id " 									+
				"		) " 																			+
				"WHERE " 																				+
				"		_id in ( " 																		+
				"				SELECT " 																+
				"						id_holiday " 													+
				"				FROM " 																	+
				"						t_YearFloatHolidays "											+
				"		) "
		;	
		Cursor cursor = db.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		cursor.close();
	}
	
	public void updateEasterDate(int year){
		int day, month;		
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int d = (19 * a + 15) % 30;
        int e = (2*b + 4*c + 6*d + 6) % 7;        
        int marchDay = 22 + d + e;
        int aprilDay = d + e - 9;    

        if(marchDay < 31 && marchDay > 0 ){
        	day = marchDay;
        	month = 3;
        }
        
        else{
        	day = aprilDay;
        	month = 4;
        }
        
        Log.w("easter", year+"/"+month+"/"+day);
        DecimalFormat formatter = new DecimalFormat("00");
        
        String query = 
				"UPDATE " 																	+
				"		t_holidays " 														+
				"SET " 																		+
				"		day = ( " 															+
				"				SELECT " 													+
				"						strftime('%d', date('"+year+"-"+formatter.format(month)+"-"+formatter.format(day)+"', ''||(13 + dayOffset)||' days')) "  +
				"				FROM " 														+
				"						t_easterHolidays " 									+
				"				WHERE " 													+
				"						id_holiday = t_holidays._id " 						+
				"		) " +
				"	,	month = ( " 														+
				"				SELECT " 													+
				"						strftime('%m', date('"+year+"-"+formatter.format(month)+"-"+formatter.format(day)+"', ''||(13 + dayOffset)||' days')) - 1 "  +
				"				FROM " 														+
				"						t_easterHolidays " 									+
				"				WHERE " 													+
				"						id_holiday = t_holidays._id " 						+
				"		) " 																+
				"WHERE " 																	+
				"		_id in ( " 															+
				"				SELECT " 													+
				"						id_holiday " 										+
				"				FROM " 														+
				"						t_easterHolidays "									+
				"		) "
		;
        Log.w("easter", query);

    	Cursor cursor = db.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		cursor.close();
	}
	
	private List<Holiday> getHolidays(Cursor cursor){
		
		List<Holiday> holidays = new ArrayList<Holiday>();
		
		// Prepare temporary objects
		Holiday holiday = null;
		Set<Country> countries = null;
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
					countries,
					cursor.getInt(11),
					cursor.getInt(3)
			);
									
			holidays.add(holiday);
		}
		
		try{inStream.close();}catch(Exception exc){}
		
		return holidays;
	}
	
	
	private Set<Country> getCountries(int idHoliday){
		Set<Country> countries = new HashSet<Country>();
		
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
