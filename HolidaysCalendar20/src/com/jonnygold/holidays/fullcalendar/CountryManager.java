package com.jonnygold.holidays.fullcalendar;

public class CountryManager {
	
	public static Country getCountry(int id){
		switch(id){
		case CountryWorld.ID : 		return new CountryWorld();
		case CountryRussia.ID : 	return new CountryRussia();
		case CountryBelorussia.ID : return new CountryBelorussia();
		case CountryUkrane.ID : 	return new CountryUkrane();
		case CountryKazakhstan.ID : return new CountryKazakhstan();
		case CountryUser.ID : 		return new CountryUser();
		default : 					return null;
		}
	}
	
//	public static Class[] getCountries(){
//		return new Class[]{
//				CountryWorld.class, 
//				CountryRussia.class, 
//				CountryBelorussia.class, 
//				CountryUkrane.class}
//		;
//	}
	
}
