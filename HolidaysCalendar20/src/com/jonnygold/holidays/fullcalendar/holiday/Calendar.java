package com.jonnygold.holidays.fullcalendar.holiday;

import java.util.HashMap;
import java.util.Map;

public enum Calendar {
	WORLD (Country.WORLD, "Включает описание 150 всемирных праздников и памятных дат."),
	RUSSIA (Country.RUSSIA, "Содержит описание более 200 праздников, отмечаемых на территории Российской Федерации."),
	BELORUSSIA (Country.BELORUSSIA, "Содержит описание 80 праздничных дат, отмечаемых в Республике Беларусь."),
	KAZACHSTAN (Country.KAZACHSTAN, "Включает описание праздники Казахстана"),
	UKRANE (Country.UKRANE, "Включает более 100 праздничных и памятных дат Украины.");
	
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
