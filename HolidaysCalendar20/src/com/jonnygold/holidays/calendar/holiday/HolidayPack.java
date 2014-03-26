package com.jonnygold.holidays.calendar.holiday;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayPack {

	private final int countryId;
	
	private Map<Integer, Picture> pictures;
	
	private List<HolidayRaw> holidays;
	
	@SuppressLint("UseSparseArrays")
	public HolidayPack(int countryId){
		this.countryId = countryId;
		this.holidays = new ArrayList<HolidayRaw>(100);
		this.pictures = new HashMap<Integer, Picture>(50);
	}
	
	public int getCountryId() {
		return countryId;
	}
	
	public void addPicture(Picture picture){
		pictures.put(picture.getId(), picture);
	}
	
	public void addHoliday(HolidayRaw holiday){
		holidays.add(holiday);
	}
	
	public Collection<Picture> getPictures(){
		return pictures.values();
	}
	
	public Collection<HolidayRaw> getHolidays(){
		for(HolidayRaw holiday : holidays){
			holiday.setPicture(pictures.get(holiday.getPicture().getId()));
		}
		return holidays;
	}
	
}
