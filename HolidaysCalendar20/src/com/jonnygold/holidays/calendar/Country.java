package com.jonnygold.holidays.calendar;

abstract class Country {
	
	protected Country(){};
	
	abstract int getDrawableId();
	
	abstract String getName();
	
	abstract int getId();
	
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
