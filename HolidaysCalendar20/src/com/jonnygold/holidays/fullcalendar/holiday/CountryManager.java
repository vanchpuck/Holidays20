package com.jonnygold.holidays.fullcalendar.holiday;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.jonnygold.holidays.fullcalendar.HolidaysDataSource;


public class CountryManager {
	
	private class StateManager{
		
		
		
	}
	
	private static CountryManager instance;
	
	private Map<Integer, Country> idMap;
	
	private Map<String, Country> keyMap;
	
	private HolidaysDataSource holidaysBase;
	
	private CountryManager(){
		initIdMap();
	}
	
	public static CountryManager getInstance(){
		if(instance == null){
			instance = new CountryManager();
		}
		return instance;
	}
	
	public Country getCountry(int id){
		return idMap.get(id);
	}
		
	public Country getCountry(String key){
		if(keyMap == null){
			keyMap = new HashMap<String, Country>();
			initKeyMap();
		}
		return keyMap.get(key);
	}
	
	public Collection<Country> getCountries(){
		return idMap.values();
	}
	
//	public boolean isInstalled(Context context, Country country){
//		if(holidaysBase == null){
//			holidaysBase = HolidaysDataSource.newInstance(context);
//		}
//		holidaysBase.openForReading();
//		boolean result = holidaysBase.isInstalled(country);
//		holidaysBase.close();
//		return result;
//	}
	
	@SuppressLint("UseSparseArrays")
	private void initIdMap(){
		idMap = new HashMap<Integer, Country>();
		for(Country c : Country.values()){
			idMap.put(c.getId(), c);
		}
	}
	
	private void initKeyMap(){
		keyMap = new HashMap<String, Country>();
		for(Country c : Country.values()){
			keyMap.put(c.getKey(), c);
		}
	}

}
