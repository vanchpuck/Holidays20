package com.jonnygold.holidays.fullcalendar.holiday;

import android.annotation.SuppressLint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class CountryManager {
	
	private static CountryManager instance;
	
	private Map<Integer, Country> idMap;
	
	private Map<String, Country> keyMap;
	
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
	
//	public static Country getCountry(int id){
//		switch(id){
//		case CountryWorld.ID : 		return new CountryWorld();
//		case CountryRussia.ID : 	return new CountryRussia();
//		case CountryBelorussia.ID : return new CountryBelorussia();
//		case CountryUkrane.ID : 	return new CountryUkrane();
//		case CountryKazakhstan.ID : return new CountryKazakhstan();
//		case CountryUser.ID : 		return new CountryUser();
//		default : 					return null;
//		}
//		Country.
//	}
	
	

//	public static Class<Country>[] getCountries(){
//		return new Class<Country>[]{
//				CountryWorld.class, 
//				CountryRussia.class, 
//				CountryBelorussia.class, 
//				CountryUkrane.class,
//				CountryKazakhstan.class,
//				CountryUser.class}
//		;
//	}
	
}
