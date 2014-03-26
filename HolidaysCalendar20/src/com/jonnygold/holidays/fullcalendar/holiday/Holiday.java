package com.jonnygold.holidays.fullcalendar.holiday;

import java.io.ByteArrayInputStream;
import java.util.Set;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Holiday extends HolidayRaw implements Parcelable{

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
	
	
	public Holiday(Parcel in){
		super(-1, null, null, -1, null, null, null, null);
		this.setId(in.readInt());
		this.setTitle(in.readString());
		this.setDateSrt(in.readString());
		this.setType(in.readInt());
		this.setDescription(in.readString());
		
		HolidayDate.Builder builder = new HolidayDate.Builder();
		this.setDate(builder.setActualMonth(in.readInt()).setActualDay(in.readInt()).create() );
		this.setPicture(new Picture(in.readInt(), in.readString(), in.createByteArray()) );
		
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
		super(id, tittle, dateStr, type, picture, description, countries, date);
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
		// Write picture
//		Bitmap bitmap = ((BitmapDrawable)picture).getBitmap();
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		out.writeByteArray(getPicture().getData());
		
		Log.w("WRITE", "WRITE");
				
//		out.writeList(new ArrayList<Country>(countries));
	}
	
	
	

	public boolean isDeletable(){
		for(Country c : getCountries()){
			if(Country.USER == c){
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

	@SuppressWarnings("deprecation")
	public Drawable getDrawable(){
		ByteArrayInputStream inStream = new ByteArrayInputStream(getPicture().getData());
		return new BitmapDrawable(BitmapFactory.decodeStream(inStream)); 
	}
	
}
