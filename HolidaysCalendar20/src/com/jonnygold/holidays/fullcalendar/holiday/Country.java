package com.jonnygold.holidays.fullcalendar.holiday;

import com.jonnygold.holidays.fullcalendar.R;

public enum Country {
	WORLD (1, "key_world_holidays", "������� ���������", R.drawable.flag_wrld),
	RUSSIA (2, "key_russian_holidays", "��������� ������", R.drawable.flag_rus),
	BELORUSSIA (3, "key_belorussian_holidays", "��������� ����������", R.drawable.flag_bel),
	UKRANE (4, "key_ukrane_holidays", "��������� �������", R.drawable.flag_ukr),
	USER (5, "key_user_holidays", "��������� ������������", R.drawable.flag_user),
	KAZACHSTAN (6, "key_kazachstan_holidays", "��������� ����������", R.drawable.flag_kaz);
	
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
