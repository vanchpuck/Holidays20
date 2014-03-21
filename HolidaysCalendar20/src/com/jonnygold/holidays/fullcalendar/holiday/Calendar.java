package com.jonnygold.holidays.fullcalendar.holiday;

import java.util.HashMap;
import java.util.Map;

public enum Calendar {
	WORLD (Country.WORLD, "Содержит праздники Мира"),
	RUSSIA (Country.RUSSIA, "Содержит праздники России"),
	BELORUSSIA (Country.BELORUSSIA, "Содержит праздники Белоруссии"),
	KAZACHSTAN (Country.KAZACHSTAN, "Содержит праздники Казахстана"),
	UKRANE (Country.UKRANE, "Содержит праздники Украины");
	
	private static final Map<Country, Calendar> map;
	static{
		map = new HashMap<Country, Calendar>();
		for(Calendar calendar : Calendar.values()){
			map.put(calendar.country, calendar);
		}
	}
	
	public final Country country;
	
	public String description;
	
	private Calendar(Country country, String description){
		this.country = country;
		this.description = description;
	}
	
	public static Calendar getCalendar(Country country){
		return map.get(country);
	}
	
}
