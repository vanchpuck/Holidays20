package com.jonnygold.holidays.fullcalendar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Holiday implements Parcelable{

	public static final class Type{
		public static final int INTERNATIONAL_HOLIDAY = 1;
		public static final int PROFESSIONAL_HOLIDAY = 2;
		public static final int FIELD_DAY = 3;
		public static final int OTHER_HOLIDAY = 4;
		public static final int NATIONAL_HOLIDAY = 5;
		public static final int MEMORIAL_DAY = 6;
		public static final int GLORY_DAY = 7;
		public static final int USER_HOLIDAY = 9;
	}
	
	public static final Parcelable.Creator<Holiday> CREATOR = new Parcelable.Creator<Holiday>() {
		public Holiday createFromParcel(Parcel in) {
		    return new Holiday(in);
		}
		
		public Holiday[] newArray(int size) {
		    return new Holiday[size];
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
	
//	private int actualMonth;
//	
//	private int actualDay;
	
	
	public Holiday(Parcel in){
		this.id = in.readInt();
		this.title = in.readString();
		this.dateSrt = in.readString();
		this.type = in.readInt();
		this.description = in.readString();
		
		HolidayDate.Builder builder = new HolidayDate.Builder();
		this.date = builder.setActualMonth(in.readInt()).setActualDay(in.readInt()).create();
		this.picture = new Picture(in.readInt(), in.readString(), in.createByteArray());
		
//		byte[] pictureData = in.createByteArray();
//		
//		Log.w("SIZE", pictureData.toString()+"");
////		
//		ByteArrayInputStream is = new ByteArrayInputStream(pictureData);
//		
//		this.picture = new BitmapDrawable(null, is);
	}
	
	public Holiday(
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
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(title);
		out.writeString(dateSrt);
		out.writeInt(type);
		out.writeString(description);
		out.writeInt(date.getActualMonth());
		out.writeInt(date.getActualDay());
		out.writeInt(picture.getId());
		// Write picture
//		Bitmap bitmap = ((BitmapDrawable)picture).getBitmap();
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		out.writeByteArray(picture.getData());
		
		Log.w("WRITE", "WRITE");
				
//		out.writeList(new ArrayList<Country>(countries));
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

	public boolean isDeletable(){
		for(Country c : getCountries()){
			if(CountryUser.class.equals(c.getClass())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		if(!(o instanceof Holiday)){
			return false;
		}
		if(this.getId() == ((Holiday)o).getId()){
			return true;
		}
		return false;
	}	
	
	@Override
	public int hashCode() {
		return this.getId();
	}
}
