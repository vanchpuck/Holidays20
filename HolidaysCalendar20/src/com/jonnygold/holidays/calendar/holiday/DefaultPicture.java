package com.jonnygold.holidays.calendar.holiday;

import android.graphics.drawable.Drawable;

public final class DefaultPicture extends Graphics implements IsPicture {

	private static DefaultPicture instance;
	
	private DefaultPicture() {
		super(161, "user", null, "photo-64581135_319801459");
	}
	
	public static DefaultPicture getInstance(){
		if(instance == null){
			instance = new DefaultPicture();
		}
		return instance;
	}
	
	public Drawable getDrawable() {
		return null;
	}

	@Override
	public byte[] getData() {
		return null;
	}

}