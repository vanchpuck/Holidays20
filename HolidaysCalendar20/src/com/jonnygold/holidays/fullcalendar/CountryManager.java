package com.jonnygold.holidays.fullcalendar;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;

public class CountryManager {
	
	private static HolidaysDataSource holidaysBase;
	
	private static Map<Integer, Country> countries = Collections.emptyMap();
	
	private static Context contextInstance;
	
//	private CountryManager(Context context){
//		this.context = context;
//		holidaysBase = HolidaysDataSource.newInstance(context);
//		refresh();
//	}
	
	
	public static void init(Context context){
		if(holidaysBase == null || contextInstance == null){
			contextInstance = context;
			holidaysBase = HolidaysDataSource.newInstance(context);
			refresh();
		}
	}
	
//	public static CountryManager getInstance(Context context){
//		if(instance == null){
//			instance = new CountryManager(context);
//		}
//		return instance;
//	}
	
	@SuppressLint("UseSparseArrays")
	public static void refresh(){
		holidaysBase.openForReading();
		countries = new HashMap<Integer, Country>();
		countries.put(CountryWorld.ID, new CountryWorld(contextInstance));
		countries.put(CountryRussia.ID, new CountryRussia(contextInstance));
		countries.put(CountryBelorussia.ID, new CountryBelorussia(contextInstance));
		countries.put(CountryUkrane.ID, new CountryUkrane(contextInstance));
		countries.put(CountryUser.ID, new CountryUser(contextInstance));
//		countries. addAll(holidaysBase.getExtraCountries());
//		for(Country c : holidaysBase.getExtraCountries()){
//			countries.put(c.getId(), c);
//		}
		holidaysBase.close();
	}
	
	public static Country getCountry(int id){
		return countries.get(id);
	}
	
	public static Collection<Country> getCountries(){
		return countries.values();
	}
	
	
//	public static Country getCountry(int id){
//		switch(id){
//		case CountryWorld.ID : 		return new CountryWorld();
//		case CountryRussia.ID : 	return new CountryRussia();
//		case CountryBelorussia.ID : return new CountryBelorussia();
//		case CountryUkrane.ID : 	return new CountryUkrane();
//		case CountryUser.ID : 		return new CountryUser();
//		default : 					return null;
//		}
//	}
	
//	public static Class[] getCountries(){
//		return new Class[]{
//				CountryWorld.class, 
//				CountryRussia.class, 
//				CountryBelorussia.class, 
//				CountryUkrane.class}
//		;
//	}
	
}
