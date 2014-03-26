package com.jonnygold.holidays.calendar.holiday;

import android.graphics.drawable.Drawable;

public final class DefaultPicture extends Graphics implements IsPicture {

	private static DefaultPicture instance;
	
	private DefaultPicture() {
		super(161, "user", null);
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
