package com.jonnygold.holidays.calendar;

public enum WeekDay {
	SUNDAY("воскресенье"),
	MONDAY("понедельник"),
	TUESDAY("вторник"),
	WEDNESDAY("среда"),
	THURSDAY("четверг"),
	FRIDAY("пятница"),
	SATURDAY("суббота");
	
	private String title;
	
	private WeekDay(String title){
		this.title = title;
	}
	
	@Override
	public String toString() {
		return this.title;
	}
	
}
