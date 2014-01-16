package com.jonnygold.holidays.fullcalendar;

public abstract class Country {
	
	protected Country(){};
	
	public abstract int getDrawableId();
	
	public abstract String getName();
	
	public abstract int getId();
	
	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		if(!(o instanceof Country)){
			return false;
		}
		if(this.getId() == ((Country)o).getId()){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getId();
	}
	
}
