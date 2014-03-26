package com.jonnygold.holidays.fullcalendar.holiday;

import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;

public class HolidayRaw implements Parcelable {

	public static final Parcelable.Creator<HolidayRaw> CREATOR = new Parcelable.Creator<HolidayRaw>() {
		public HolidayRaw createFromParcel(Parcel in) {
		    return new HolidayRaw(in);
		}
		
		public HolidayRaw[] newArray(int size) {
		    return new HolidayRaw[size];
		}
	};

	private int id;
	
	private String title;
	
	private String dateSrt;
	
	private int type;
	
	private IsPicture picture;
	
	private String description;
	
	private Set<Country> countries;
	
	private HolidayDate date;
	
	public HolidayRaw(Parcel in){
		this.setId(in.readInt());
		this.setTitle(in.readString());
		this.setDateSrt(in.readString());
		this.setType(in.readInt());
		this.setDescription(in.readString());
		
		HolidayDate.Builder builder = new HolidayDate.Builder();
		this.setDate(builder.setActualMonth(in.readInt()).setActualDay(in.readInt()).create() );
		this.setPicture(new Picture(in.readInt(), in.readString(), in.createByteArray()) );
	}
	
	public HolidayRaw(
			int id,
			String tittle, 
			String dateStr, 
			int type, 
			IsPicture picture, 
			String description, 
			Set<Country> countries,
			HolidayDate date){
		
		this.id = id;
		this.title = tittle;
		this.dateSrt = dateStr;
		this.type = type;
		this.picture = picture;
		this.description = description;
		this.countries = countries;
		this.date = date;
	}
	
	public int getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDateString(){
		return dateSrt;
	}
	
	public int getType(){
		return type;
	}
	
	public IsPicture getPicture(){
		return picture;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Set<Country> getCountries(){
		return countries;
	}
	
	public HolidayDate getDate(){
		return date;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	protected void setTitle(String title) {
		this.title = title;
	}
	
	protected void setDateSrt(String dateSrt) {
		this.dateSrt = dateSrt;
	}
	
	protected void setType(int type) {
		this.type = type;
	}
	
	protected void setPicture(IsPicture picture) {
		this.picture = picture;
	}
	
	protected void setDescription(String description) {
		this.description = description;
	}
	
	protected void setDate(HolidayDate date) {
		this.date = date;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(getId());
		out.writeString(getTitle());
		out.writeString(getDateString());
		out.writeInt(getType());
		out.writeString(getDescription());
		out.writeInt(getDate().getActualMonth());
		out.writeInt(getDate().getActualDay());
		out.writeInt(getPicture().getId());
		out.writeByteArray(getPicture().getData());
	}

}
