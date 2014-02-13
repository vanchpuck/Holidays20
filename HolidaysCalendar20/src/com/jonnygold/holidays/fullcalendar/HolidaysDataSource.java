package com.jonnygold.holidays.fullcalendar;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//import android.util.Log;
import android.util.Log;

public class HolidaysDataSource {

	private final static int COL_HLIDAY_TITLE = 0;
	private final static int COL_HOLIDAY_DESCRIPTION = 1;
	private final static int COL_HOLIDAY_DATE = 2;
//	private final static int COL_HOLIDAY_DAY = 3;
	private final static int COL_PRIORITY_ID = 4;
	private final static int COL_IMAGE = 5;
//	private final static int COL_M_FLOAT_MONTH = 6;
//	private final static int COL_M_FLOAT_WEEKDAY = 7;
//	private final static int COL_M_FLOAT_DAY_OFFSET = 8;
//	private final static int COL_Y_FLOAT_DAY = 9;
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
		private String title;
		private boolean includeAfter = false;
		
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
		
		public List<Integer> getCountries(){
			return this.countries;
		}
		
		public QueryRestriction setDate(int month, int day){
			this.month = Integer.valueOf(month);
			this.day = Integer.valueOf(day);
			return this;
		}
		
		public QueryRestriction includeAfter(){
			includeAfter = true;
			return this;
		}
		
		public QueryRestriction setLimit(int limit){
			this.limit = Integer.valueOf(limit);
			return this;
		}
		
		public QueryRestriction setTitle(String title){
			this.title = title;
			return this;
		}
		
		private String getWhereClause(){
			if(!countries.isEmpty()){
				restriction.append("AND ( 0>1 ");
				for(Iterator<Integer> itr=countries.iterator(); itr.hasNext(); ){
					restriction.append("OR TCH.id_country = "+itr.next()+" ");
				}
				restriction.append(") ");
			}
			
			if(title != null){
				restriction.append("AND TH.title LIKE '%"+title+"%' ");
			}
			
			if(month != null && day != null){
				if(includeAfter){
					String condition = "AND (date('now','start of year','+'||TH.month||' months','+'||TH.day||' days') >= date('now','start of year','+"+month+" months','+"+day+" days') "+
											"OR (TH.month = 0 AND "+month+"=11) )";
					restriction.append(condition);
				}
				else{
					restriction.append("AND TH.month = "+month+" AND TH.day = "+day+" ");
				}
//				restriction.append("AND TH.month = "+month+" AND TH.day = "+day+" ");
//				if(includeAfter){
//					restriction.append("AND date('now','start of year','+'||TH.month||' months','+'||TH.day||' days') >= date('now','start of year','+"+month+" months','+"+day+" days') ");
//				}
			}
			
//			if(includeAfter){
//				restriction.append("AND date('now','start of year','+'||TH.month||' months','+'||TH.day||' days') >= date('now','start of year','+"+month+" months','+"+day+" days') ");
//			}
			
			if(limit != null){
				if(month != null && month == 11){
					restriction.append("ORDER BY TH.month DESC, TH.day ASC ");
				}
				else{
					restriction.append("ORDER BY TH.month, TH.day ");
				}
				restriction.append("LIMIT "+limit+" ");
			}
			
			return restriction.toString();
		}
		
	}
	
	
	private SQLiteDatabase db;
	
	private HolidaysBaseHelper dbHelper;
		
	private HolidaysDataSource(Context context) throws IOException{
		dbHelper = new HolidaysBaseHelper(context);
	}
	
//	public static HolidaysDataSource getInstance(Context context){
//		if(dataSource == null){
//			try {
//				dataSource = new HolidaysDataSource(context);
//			} catch (IOException e) {
//				dataSource = null;
//			}
////			dataSource = new HolidaysDataSource(context);
////			dataSource = null;
//		}
//		return dataSource;
//	}
	
	public static HolidaysDataSource newInstance(Context context){
		HolidaysDataSource ds = null;
		try {
			ds = new HolidaysDataSource(context);
		} catch (IOException e) {
			ds = null;
		}
		return ds;
	}
	
	public boolean openForReading() {
		if(db != null && db.isOpen())
			return true;
		try{
			db = dbHelper.getReadableDatabase();
//			db.execSQL("PRAGMA foreign_keys=ON;");
			return true;
			
		}catch(SQLiteException exc){
//			Log.w("!!!!!", "---------|");
			exc.printStackTrace();
//			Log.w("!!!!!", "---------|");
			return false;
		}
	}
		
	public boolean openForWriting() {
		try{	
			db = dbHelper.getWritableDatabase();
//			db.execSQL("PRAGMA foreign_keys=ON;");
			return true;
		}catch(SQLiteException exc){
			return false;
		}
	}
	
	public boolean isOpen(){
		return db.isOpen();
	}
	
	public void close(){
		db.close();
	}
	
	
//	public List<Holiday> getHolidays(){
//		// Prepare query
//		String query = HOLIDAYS_QUERY + HOLIDAYS_LIMIT;
//		Log.w("QUERY", query);
//		
//		// Execute query
//		Cursor c = db.rawQuery(query, new String[]{});
//		
//		List<Holiday> holidays = getHolidays(c);
//		
//		c.close();
//		
//		return holidays;
//	}
	
	public List<Holiday> getHolidays(QueryRestriction restriction){
		// Prepare query
		String query = HOLIDAYS_QUERY;
		if(restriction != null){
			query += restriction.getWhereClause();
		}
//		Log.w("QUERY", query);
		
		// Execute query
		Cursor c = db.rawQuery(query, new String[]{});
		
		List<Holiday> holidays = getHolidays(c, restriction);
		
		c.close();
		
		return holidays;
	}	
	
	public void updateFloatHolidays(int year){
		updateEasterDate(year);
		updateMonthFloatDates(year);
		updateYearFloatDates(year);
//		Log.w("UPDATE", "UPDATE");
	}
	
	public void updateMonthFloatDates(int year){
		String query = 
				"UPDATE " 																																			+
				"		t_holidays " 																																+
				"SET " 																																				+
				"		day = ( " 																																	+
				"				SELECT " 																															+
				"						CASE WHEN month = 0 AND dayOffset < 0 THEN " 				+
				"							strftime('%d', date('"+(year+1)+"-01-01','+'||month||' months','weekday '||weekDay||'', ''||dayOffset||' days')) " 				+
				"						ELSE " 				+
				"							strftime('%d', date('"+year+"-01-01','+'||month||' months','weekday '||weekDay||'', ''||dayOffset||' days')) " 				+
				"						END " 				+
				"				FROM " 																																+
				"						t_MonthFloatHolidays " 																										+
				"				WHERE " 																															+
				"						id_holiday = t_holidays._id " 																								+
				"		) " 																																		+
				"	,	month = ( " 																																+
				"				SELECT " 																															+
				"						CASE WHEN dayOffset < 0 AND month <> 0 THEN month-1 "				 														+
				"							WHEN dayOffset < 0 AND month = 0 THEN 11"	+	
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
				"		), " 																			+
				"		month = ( " 																		+
				"				SELECT " 																+
				"						strftime('%m', date('"+year+"-01-01','+'||day||' days'))-1 " 		+
				"				FROM " 																	+
				"						t_YearFloatHolidays " 											+
				"				WHERE " 																+
				"						id_holiday = t_holidays._id " 									+
				"		) " +
				"WHERE " 																				+
				"		_id in ( " 																		+
				"				SELECT " 																+
				"						id_holiday " 													+
				"				FROM " 																	+
				"						t_YearFloatHolidays "											+
				"		) "
		;	
//		Log.w("Year", query);
		
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
        
//        Log.w("easter", year+"/"+month+"/"+day);
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
//        Log.w("easter", query);

    	Cursor cursor = db.rawQuery(query, new String[]{});
		cursor.moveToFirst();
		cursor.close();
	}
	
	private List<Holiday> getHolidays(Cursor cursor, QueryRestriction restriction){
		
		List<Holiday> holidays = new ArrayList<Holiday>();
		
		// Prepare temporary objects
		Holiday holiday = null;
		Set<Country> countries = null;
		ByteArrayInputStream inStream = null;
		Drawable icon = null;
		
		// Put holidays into the Set
		while(cursor.moveToNext()){
			// Get countries where holiday is celebrated
			countries = getCountries(cursor.getInt(COL_HOLIDAY_ID), restriction);
			
			inStream = new ByteArrayInputStream(cursor.getBlob(COL_IMAGE));
			icon = new BitmapDrawable(BitmapFactory.decodeStream(inStream)); 
			
			HolidayDate.Builder builder = new HolidayDate.Builder();
			HolidayDate date = builder.setActualMonth(cursor.getInt(11)).setActualDay(cursor.getInt(3)).create();
			
			// Construct Holiday object
			holiday = new Holiday(
					cursor.getInt(COL_HOLIDAY_ID), 
					cursor.getString(COL_HLIDAY_TITLE), 
					cursor.getString(COL_HOLIDAY_DATE), 
					cursor.getInt(COL_PRIORITY_ID), 
					icon, 
					cursor.getString(COL_HOLIDAY_DESCRIPTION), 
					countries,
					date
			);
									
			holidays.add(holiday);
		}
		
		try{inStream.close();}catch(Exception exc){}
		
		return holidays;
	}
	
	
	private Set<Country> getCountries(int idHoliday, QueryRestriction restriction){
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
		
		Country country = null;
		// Fill the collection of countries 
		while(c.moveToNext()){
			country = CountryManager.getCountry(c.getInt(0));
			if(restriction.getCountries().contains(country.getId())){
				countries.add(CountryManager.getCountry(c.getInt(0)));
			}
		}
		
		c.close();
		
		return countries;
	}
	
	public void deleteHoliday(Holiday holiday){
		if(holiday.getId() == -1){
			return;
		}
		db.beginTransaction();
		try{
			db.delete("t_countryholidays", "id_country=?", new String[]{String.valueOf(holiday.getId())});
			if(holiday.getDate().isMonthFloat()){
				db.delete("t_MonthFloatHolidays", "id_country=?", new String[]{String.valueOf(holiday.getId())});
			}
			if(holiday.getDate().isYearFloat()){
				db.delete("t_YearFloatholidays", "id_country=?", new String[]{String.valueOf(holiday.getId())});
			}
			db.delete("t_holidays", "_id=?", new String[]{String.valueOf(holiday.getId())});
			db.setTransactionSuccessful();
		}
		catch(SQLiteException exc){
			exc.getMessage();
			throw new SQLiteException();
		}
		finally{
			db.endTransaction();
		}
	}
		
	public void saveHoliday(Holiday holiday){
		db.beginTransaction();
		
		if(holiday.getId() != -1){
			deleteHoliday(holiday);
		}
		
		ContentValues values = null;
		
		try{
			HolidayDate date = holiday.getDate();
			
//			Log.w("SAVE", "holiday");
			
			values = new ContentValues();
			Log.w("title", holiday.getTitle());
			values.put("title", holiday.getTitle());
			
			Log.w("description", holiday.getDescription());
			values.put("description", holiday.getDescription());
			
			Log.w("month", holiday.getDate().getActualMonth()+"");
			values.put("month", holiday.getDate().getActualMonth());
			
			Log.w("day", holiday.getDate().getActualDay()+"");
			values.put("day", holiday.getDate().getActualDay());
			
			Log.w("day", holiday.getDate().toString());
			values.put("actualDateStr", holiday.getDate().toString());
			
			Log.w("type", holiday.getType()+"");
			values.put("id_priority", holiday.getType());
		
			values.put("id_image", 161);
			long id = db.insertOrThrow("t_holidays", null, values);
			
			values.clear();
			for(Country country : holiday.getCountries()){
				values.put("id_country", country.getId());
				values.put("id_holiday", id);
				db.insertOrThrow("t_countryholidays", null, values);
			}
			
			
			if(date.isMonthFloat()){
//				Log.w("SAVE", "month");
				
				values.clear();
//				Log.w("id_holiday", id+"");
				values.put("id_holiday", id);
				
//				Log.w("month", holiday.getDate().getFloatMonth()+"");
				values.put("month", holiday.getDate().getFloatMonth());
				
//				Log.w("weekDay", holiday.getDate().getWeekDay()+"");
				values.put("weekDay", holiday.getDate().getWeekDay());
				
//				Log.w("dayOffset", holiday.getDate().getOffset()+"");
				values.put("dayOffset", holiday.getDate().getOffset());
				
				db.insertOrThrow("t_MonthFloatHolidays", null, values);
			}
			
			else if(date.isYearFloat()){
//				Log.w("SAVE", "year");
				
				values.clear();
//				Log.w("id_holiday", id+"");
				values.put("id_holiday", id);
				
//				Log.w("day", holiday.getDate().getYearDay()+"");
				values.put("day", holiday.getDate().getYearDay());
				
				db.insertOrThrow("t_YearFloatHolidays", null, values);
			}
						
			db.setTransactionSuccessful();
		}
		catch(SQLiteException exc){
//			Log.w("HolidaySaving", "Exception");
			exc.printStackTrace();
			throw new SQLiteException();
		}
		finally{
			db.endTransaction();
		}
		
	}
	
}
