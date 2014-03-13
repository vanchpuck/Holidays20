package com.jonnygold.holidays.fullcalendar.holiday;

public enum DayOrder{
	FIRST("Первый(я)", 0),
	SECOND("Второй(я)", 7),
	THIRD("Третий(я)", 14),
	FOURTH("Четвертый(я)", 21),
	LAST("Последний(я)", -7);
	
	private int offset;
	private String orderStr;
	
	private DayOrder(String orderStr, int offset){
		this.offset = offset;
		this.orderStr = orderStr;
	}
	
	public static DayOrder getDayOrder(int offset){
		switch(offset){
		case 0 :
			return DayOrder.FIRST;
		case 7 :
			return DayOrder.SECOND;
		case 14 :
			return DayOrder.THIRD;
		case 21 :
			return DayOrder.FOURTH;
		case -7 :
			return DayOrder.LAST;
		default :
			throw new IllegalArgumentException("Недопустимое смещение плавающего праздника.");
		}
	}
	
	public int getOffset(){
		return offset;
	}
	
	@Override
	public String toString() {
		return orderStr;
	}
}
