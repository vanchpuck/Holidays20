package com.jonnygold.holidays.calendar;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class CopiedBaseHelper extends SQLiteOpenHelper{
	
	protected final Context context;
	
	protected final String databaseName;
	
	public CopiedBaseHelper(Context context, String name, int version){
		super(context, name, null, version);
		this.context = context;
		
		databaseName = name;
		
		// Path to database files
		String path = context.getDatabasePath(name).getPath();
		
		// Check whether the database file exists. If not - copy file from assets
		if(!isDatabaseExist(path)){
			copyDatabaseFile(path);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
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
	
	
	/**
	 * Copy database file from assets to working directory.
	 * @param path - path to working directory
	 * @param name - database name
	 * @return - whether file was copied or not
	 */
	private boolean copyDatabaseFile(String path){
		boolean copied = true;
				
		// Create database file
		SQLiteDatabase tempDb = getReadableDatabase();
		tempDb.close();
		
		try{
			//Open your local db as the input stream
	    	InputStream input = context.getAssets().open(databaseName);
	 
	    	//Open the empty db as the output stream
	    	OutputStream output = new FileOutputStream(path);
	    	
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = input.read(buffer)) != -1){
	    		output.write(buffer, 0, length);
	    	}
	    	
	    	//Close the streams
	    	output.flush();
	    	output.close();
	    	input.close();
	    	
		} catch (Exception exc){
			copied = false;
		}
    	
		return copied;
	}

}
