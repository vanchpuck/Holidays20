package com.jonnygold.holidays.calendar.holiday;

import com.jonnygold.holidays.calendar.R;

public enum Country {
	USER (5, "key_user_holidays", "Праздники пользователя", R.drawable.flag_user),
	WORLD (1, "key_world_holidays", "Мировые праздники", R.drawable.flag_wrld),
	RUSSIA (2, "key_russian_holidays", "Праздники России", R.drawable.flag_rus),
	BELORUSSIA (3, "key_belorussian_holidays", "Праздники Белоруссии", R.drawable.flag_bel),
	UKRANE (4, "key_ukrane_holidays", "Праздники Украины", R.drawable.flag_ukr),
	KAZACHSTAN (6, "key_kazachstan_holidays", "Праздники Казахстана", R.drawable.flag_kaz),
	USSR (7, "key_ussr_holidays", "Праздники СССР", R.drawable.flag_ussr);
	
	private final int id;
	private final String key;
	private final String name;
	private final int drawableId;
	
	Country(int id, String key, String name, int drawableId){
		this.id = id;
		this.key = key;
		this.name = name;
		this.drawableId = drawableId;
	}
	
	public int getDrawableId() {
		return drawableId;
	}
	
	public int getId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getName() {
		return name;
	}
	
//	protected Country(){};
//	
//	public abstract int getDrawableId();
//	
//	public abstract String getName();
//	
//	public abstract String getKey();
//	
//	public abstract int getId();
//	
//	@Override
//	public boolean equals(Object o) {
//		if(this == o){
//			return true;
//		}
//		if(!(o instanceof Country)){
//			return false;
//		}
//		if(this.getId() == ((Country)o).getId()){
//			return true;
//		}
//		return false;
//	}
//	
//	@Override
//	public int hashCode() {
//		return getId();
//	}
	
}
