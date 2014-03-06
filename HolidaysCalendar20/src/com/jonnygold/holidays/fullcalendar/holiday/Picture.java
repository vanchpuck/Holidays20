package com.jonnygold.holidays.fullcalendar.holiday;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Picture extends Graphics implements IsPicture {

//	private Drawable drawable;
	
	public Picture(int id, String title, byte[] data) {
		super(id, title, data);		
//		this.drawable = toDrawable(data);
	}
	
//	public Drawable getDrawable(){
//		return drawable;
//	}
	
//	@Override
//	public byte[] getData() {		
//		Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//		return stream.toByteArray();
//	}
//	
//	@SuppressWarnings("deprecation")
//	private Drawable toDrawable(byte[] data){
//		ByteArrayInputStream inStream = new ByteArrayInputStream(data);
//		return new BitmapDrawable(BitmapFactory.decodeStream(inStream)); 
//	}

}
