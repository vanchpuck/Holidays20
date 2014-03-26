package com.jonnygold.holidays.calendar.holiday;

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
