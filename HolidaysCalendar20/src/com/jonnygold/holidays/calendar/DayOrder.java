package com.jonnygold.holidays.calendar;

enum DayOrder{
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
	
	public int getOffset(){
		return offset;
	}
	
	@Override
	public String toString() {
		return orderStr;
	}
}
