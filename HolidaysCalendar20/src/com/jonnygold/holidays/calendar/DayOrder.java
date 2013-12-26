package com.jonnygold.holidays.calendar;

enum DayOrder{
	FIRST("������(�)", 0),
	SECOND("������(�)", 7),
	THIRD("������(�)", 14),
	FOURTH("���������(�)", 21),
	LAST("���������(�)", -7);
	
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
