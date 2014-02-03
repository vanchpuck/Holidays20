package com.jonnygold.holidays.fullcalendar;

import android.graphics.drawable.Drawable;

public class Country {
	
	private int id;
	
	private String title;
	
	private Drawable icon;
	
	public Country(int id, String title, Drawable icon){
		this.id = id;
		this.title = title;
		this.icon = icon;
	}
	
	public Drawable getDrawable(){
		return icon;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getId(){
		return id;
	}
	
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
