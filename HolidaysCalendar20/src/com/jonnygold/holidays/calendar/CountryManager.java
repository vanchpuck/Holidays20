package com.jonnygold.holidays.calendar;

public class CountryManager {
	
	public static Country getCountry(int id){
		switch(id){
		case CountryWorld.ID : 		return new CountryWorld();
		case CountryRussia.ID : 	return new CountryRussia();
		case CountryBelorussia.ID : return new CountryBelorussia();
		case CountryUkrane.ID : 	return new CountryUkrane();
		default : 					return null;
		}
	}
	
	public static Class[] getCountries(){
		return new Class[]{
				CountryWorld.class, 
				CountryRussia.class, 
				CountryBelorussia.class, 
				CountryUkrane.class}
		;
	}
	
}
