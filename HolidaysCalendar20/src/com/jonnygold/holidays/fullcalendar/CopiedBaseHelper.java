package com.jonnygold.holidays.fullcalendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CopiedBaseHelper extends SQLiteOpenHelper{
	
	protected final Context context;
	
	protected final String databaseName;
	
	private File f; //Лень делать прилично
		
	public CopiedBaseHelper(Context context, String name, int version) throws IOException{
		super(context, name, null, version);
		this.context = context;
		
		databaseName = name;
		
		// Path to database files
		String path = context.getDatabasePath(name).getPath();
		
		f = context.getDatabasePath(name).getParentFile();
		if (f.isDirectory()){
			for(File ff : f.listFiles()){
				if(!"mydb20".equals(ff.getName())){
					ff.delete();
				}
			}
		}
		
		Log.w("Create", path);
		
		// Check whether the database file exists. If not - copy file from assets
		if(!isDatabaseExist(path)){
			copyDatabaseFile(path);
//			throw new IOException();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		Log.w("OnCreate", "ON_CREATE");
//		try {
//			updateDataBaseFile(db);
//		} catch (IOException e) {
//			Log.w("OnCreate", "EXCEPTION");
//			e.printStackTrace();
//		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("UpdateDB", "ON_UPDATE");
//		if(newVersion > oldVersion){
//			try {
//				Log.w("UpdateDB", "UPDATE");
//				Log.w("UpdateDB", "OldVersion = "+oldVersion);
//				Log.w("UpdateDB", "NewVersion = "+newVersion);
//				updateDataBaseFile(db);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	/**
	 * Return the name of the database. 
	 */
	public String getDbName(){
		return databaseName;
	}
	
	/**
	 * Check existence of database file by given path.
	 * @param path - path to database file
	 * @param name - database name
	 * @return database file existence
	 */
	private boolean isDatabaseExist(String path){
		boolean exists = true;
		try{
			SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLiteException exc){
			exists = false;
		}
		return exists;
	}
	
	private boolean updateDataBaseFile(SQLiteDatabase db) throws IOException{
//		db.close();
		Log.w("CopyDB", "1");
		InputStream input = context.getAssets().open(databaseName);
		Log.w("CopyDB", "2");
    	//Open the empty db as the output stream
    	OutputStream output = new FileOutputStream(db.getPath());
    	Log.w("CopyDB", "3");  	
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = input.read(buffer)) != -1){
//    		Log.w("CopyDB", "COPY");
    		output.write(buffer, 0, length);
    	}
    	Log.w("CopyDB", "4");
    	//Close the streams
    	output.flush();
    	output.close();
    	input.close();
    	
    	return true;
	}
	
	/**
	 * Copy database file from assets to working directory.
	 * @param path - path to working directory
	 * @param name - database name
	 * @return - whether file was copied or not
	 * @throws IOException 
	 */
	private boolean copyDatabaseFile(String path) throws IOException{
		Log.w("COPY_DB", "COPY");
		boolean copied = true;
				
		// Create database file
		SQLiteDatabase tempDb = getReadableDatabase();
		tempDb.close();
		
		updateDataBaseFile(tempDb);
    	
		return copied;
	}

}
