package com.jonnygold.holidays.fullcalendar.holiday;

import java.util.Arrays;

public class Graphics {

private final int id;
	
	private final String title;
	
	private final byte[] data;
	
	public Graphics(int id){
		this.id = id;
		this.title = null;
		this.data = null;
	}
	
	public Graphics(int id, String title, byte[] data){
		this.id = id;
		this.title = title;
		this.data = data;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public byte[] getData() {
		return Arrays.copyOf(data, data.length);
	}
		
	@Override
	public int hashCode() {
		return (31 * (11 + id));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Graphics))
			return false;
		return id == ((Graphics)obj).getId();
	}

}
