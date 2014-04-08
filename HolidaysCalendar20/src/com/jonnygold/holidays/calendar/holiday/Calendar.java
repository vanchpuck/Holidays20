package com.jonnygold.holidays.calendar.holiday;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Calendar {
	WORLD (Country.WORLD, "Включает описание 150 всемирных праздников и памятных дат.", true),
	RUSSIA (Country.RUSSIA, "Содержит описание более 200 праздников, отмечаемых на территории Российской Федерации.", true),
	BELORUSSIA (Country.BELORUSSIA, "Содержит описание 80 праздничных дат, отмечаемых в Республике Беларусь.", true),
	KAZACHSTAN (Country.KAZACHSTAN, "Содержит сведения о 50 праздничных датах Республики Казахстан.", true),
	UKRANE (Country.UKRANE, "Включает более 100 праздничных и памятных дат Украины.", true),
	USSR (Country.USSR, "Праздники СССР.", false);
	
	private static final Map<Country, Calendar> map;
	static{
		map = new HashMap<Country, Calendar>();
		for(Calendar calendar : Calendar.values()){
			map.put(calendar.country, calendar);
		}
	}
	
	public final Country country;
	
	public String description;
	
	private boolean isFree;
	
	private Calendar(Country country, String description, boolean isFree){
		this.country = country;
		this.description = description;
		this.isFree = isFree;
	}
	
	public static Calendar getCalendar(Country country){
		return map.get(country);
	}
	
	public boolean isFree(){
		return isFree;
	}
	
}
