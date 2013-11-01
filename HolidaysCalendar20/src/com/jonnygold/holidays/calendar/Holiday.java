package com.jonnygold.holidays.calendar;

import java.util.List;
import java.util.Map;
import java.util.Set;

import android.graphics.drawable.Drawable;

public class Holiday {

	public static final class Type{
		public static final int INTERNATIONAL_HOLIDAY = 1;
		public static final int PROFESSIONAL_HOLIDAY = 2;
		public static final int FIELD_DAY = 3;
		public static final int OTHER_HOLIDAY = 4;
		public static final int NATIONAL_HOLIDAY = 5;
		public static final int MEMORIAL_DAY = 6;
		public static final int GLORY_DAY = 7;
	}
	
	private int id;
	
	private String title;
	
	private String dateSrt;
	
	private int type;
	
	private Drawable picture;
	
	private String description;
	
	private List<Country> countries;
	
	private Map<Country, Boolean> cTest;
	
	public Holiday(
			int id,
			String tittle, 
			String dateStr, 
			int type, 
			Drawable picture, 
			String description, 
			List<Country> countries){
		
		this.id = id;
		this.title = tittle;
		this.dateSrt = dateStr;
		this.type = type;
		this.picture = picture;
		this.description = description;
		this.countries = countries;
	}
	
	public int getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDateString(){
		return dateSrt;
	}
	
	public int getType(){
		return type;
	}
	
	public Drawable getDrawable(){
		return picture;
	}
	
	public String getDescription(){
		return description;
	}
	
	public List<Country> getCountries(){
		return countries;
	}
	
	public Map<Country, Boolean> getCTest(){
		return cTest;
	}
		
}
