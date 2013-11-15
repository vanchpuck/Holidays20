package com.jonnygold.holidays.calendar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Holiday implements Parcelable{

//	public static final class Type{
//		public static final int INTERNATIONAL_HOLIDAY = 1;
//		public static final int PROFESSIONAL_HOLIDAY = 2;
//		public static final int FIELD_DAY = 3;
//		public static final int OTHER_HOLIDAY = 4;
//		public static final int NATIONAL_HOLIDAY = 5;
//		public static final int MEMORIAL_DAY = 6;
//		public static final int GLORY_DAY = 7;
//	}
	
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
	
	private Drawable picture;
	
	private String description;
	
	private Set<Country> countries;
	
	private int actualMonth;
	
	private int actualDay;
	
	
	public Holiday(Parcel in){
		this.id = in.readInt();
		this.title = in.readString();
		this.dateSrt = in.readString();
		this.type = in.readInt();
		this.description = in.readString();
		this.actualMonth = in.readInt();
		this.actualDay = in.readInt();
		
		
		
		byte[] pictureData = in.createByteArray();
		
		Log.w("SIZE", pictureData.toString()+"");
//		
		ByteArrayInputStream is = new ByteArrayInputStream(pictureData);
//		
		this.picture = new BitmapDrawable(null, is);
	}
	
	public Holiday(
			int id,
			String tittle, 
			String dateStr, 
			int type, 
			Drawable picture, 
			String description, 
			Set<Country> countries,
			int actualMonth,
			int actualDay){
		
		this.id = id;
		this.title = tittle;
		this.dateSrt = dateStr;
		this.type = type;
		this.picture = picture;
		this.description = description;
		this.countries = countries;
		this.actualMonth = actualMonth;
		this.actualDay = actualDay;
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
		out.writeInt(actualMonth);
		out.writeInt(actualDay);
		
		// Write picture
		Bitmap bitmap = ((BitmapDrawable)picture).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		out.writeByteArray(stream.toByteArray());
		
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
	
	public Drawable getDrawable(){
		return picture;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Set<Country> getCountries(){
		return countries;
	}
	
	public int getActualMonth(){
		return actualMonth;
	}
	
	public int getActualDay(){
		return actualDay;
	}

			
}
