package com.jonnygold.holidays.calendar;

public class HolidayDate {

	public static class Builder{
		
		private HolidayDate date = new HolidayDate();
		
		public Builder setActualMonth(int month){
			date.setActualMonth(month);
			return this;
		}
		
		public Builder setActualDay(int day){
			date.setActualDay(day);
			return this;
		}
		
		public Builder setFloaMonth(int month){
			date.setFloatMonth(month);
			return this;
		}
		
		public Builder setWeekDay(int weekDay){
			date.setWeekDay(weekDay);
			return this;
		}
		
		public Builder setOffset(DayOrder offset){
			date.setOffset(offset);
			return this;
		}
		
		public Builder setYearDay(int day){
			date.setYearDay(day);
			return this;
		}
		
		public HolidayDate create(){
			return date;
		}
	}
	
	private Integer actualMonth;
	private Integer actualDay;
	
	private Integer floatMonth;
	private Integer weekDay;
	private DayOrder offset;
	
	private Integer yearDay;
	
	private HolidayDate(){}
	
	public void setActualMonth(int month){
		if(month > 11 || month < 0){
			throw new IllegalStateException("Недопустимое значение месяца.");
		}
		actualMonth = month;
	}
	
	public Integer getActualMonth(){
		return actualMonth;
	}
	
	public void setActualDay(int day){
		if(day > 31 || day < 0){
			throw new IllegalStateException("Недопустимое значение дня месяца.");
		}
		actualDay = day;
	}
	
	public Integer getActualDay(){
		return actualDay;
	}
	
	public Integer getFloatMonth(){
		return floatMonth;
	}
	
	public void setFloatMonth(int floatMonth){
		if(floatMonth > 11 || floatMonth < 0){
			throw new IllegalStateException("Недопустимое значение месяца.");
		}
		this.floatMonth = floatMonth;
	}
	
	public Integer getWeekDay(){
		return weekDay;
	}
	
	public void setWeekDay(int weekDay){
		this.weekDay = weekDay;
	}
	
	public Integer getOffset(){
		return offset.getOffset();
	}
	
	public void setOffset(DayOrder offset){
		this.offset = offset;
	}
	
	public void setYearDay(int day){
		if(day > 366 || day < 0){
			throw new IllegalStateException("Недопустимое значение дня года.");
		}
		yearDay = day;
	}
	
	public Integer getYearDay(){
		return yearDay;
	}
	
	public boolean isStable(){
		if(actualDay == null || actualMonth == null){
			return false;
		}
		return true;
	}
	
	public boolean isMonthFloat(){
		if(floatMonth == null || offset == null || weekDay == null){
			return false;
		}
		return true;
	}
	
	public boolean isYearFloat(){
		if(yearDay == null){
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String result = "";
		if(isStable()){
			if(actualDay != null)
				result+=String.valueOf(actualDay)+" ";
			if(actualMonth != null)
				result+=Month.values()[actualMonth].getGenitive()+" ";
		}
		else if(isMonthFloat()){
			if(offset != null){
				result+=offset.toString()+" ";
			}
			if(weekDay != null){
				result+=WeekDay.values()[weekDay].toString()+" ";
			}
			if(floatMonth != null){
				if(offset == DayOrder.LAST){
					if(floatMonth == 0){
						result+=Month.values()[11].getGenitive()+" ";
					}
					else{
						result+=Month.values()[floatMonth-1].getGenitive()+" ";
					}
				}
				else{
					result+=Month.values()[floatMonth].getGenitive()+" ";
				}
			}
		}
		else if(isYearFloat()){
			result+=(yearDay+1)+"-й день года";
		}
		return result;
		
	}

}
