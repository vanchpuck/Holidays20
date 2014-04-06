package com.jonnygold.holidays.fullcalendar.holiday;

public enum Month{
	JANUARY("Январь", "января", 31),
	FEBRARY("Февраль", "февраля", 29),
	MARCH("Март", "марта", 31),
	APRIL("Апрель", "апреля", 30),
	MAY("Май", "мая", 31),
	JUNE("Июнь", "июня", 30),
	JULY("Июль", "июля", 31),
	AUGUST("Август", "августа", 31),
	SEPTEMBER("Сентябрь", "сентября", 30),
	OCTOBER("Октябрь", "октября", 31),
	NOVEMBER("Ноябрь", "ноября", 30),
	DECEMBER("Декабрь", "декабря", 31);
	
	private String name;
	private String genitive;
	private int dayCount;
	
	private Month(String name, String genitive, int dayCount){
		this.name = name;
		this.genitive = genitive;
		this.dayCount = dayCount;
	}
	
	public String[] getDays(){
		String[] days = new String[dayCount];
		for(int i=0; i<dayCount; i++){
			days[i] = String.valueOf(i+1);
		}
		return days;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getGenitive() {
		return genitive;
	}
	
}