package com.jonnygold.holidays.fullcalendar;

import java.io.File;
import java.io.IOException;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class HolidaysBaseHelper extends SQLiteAssetHelper {

	public static final int DATABASE_VERSION = 22;
	
	public static final String DATABASE_NAME = "mydb20";
	
	private Context context;
	
	public HolidaysBaseHelper(Context context) throws IOException{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion < 8){
			try{
				File f = context.getDatabasePath(DATABASE_NAME).getParentFile();
				if (f.isDirectory()){
					for(File ff : f.listFiles()){
						if(!"mydb20".equals(ff.getName())){
							ff.delete();
						}
					}
				}
			}catch(Exception exc){}
		}
		super.onUpgrade(db, oldVersion, newVersion);
	}
	
}
