package com.jonnygold.holidays.calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

public final class NewHolidayActivity extends ActionBarActivity {

	private HolidaysDataSource holidaysBase;
	
	private HolidayDateChooser dateChooser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_holiday);
		
		dateChooser = (HolidayDateChooser) findViewById(R.id.view_date_chooser);
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	public void saveHoliday(View view){
		holidaysBase = HolidaysDataSource.getInstance(this);
		
		holidaysBase.openForWriting();
		
		if(dateChooser.getDateInfo().get("month") != null)
			Log.w("MONTH", dateChooser.getDateInfo().get("month").toString());
		else
			Log.w("ERROR", "ERROR");
//		Log.w("DAY", dateChooser.getDateInfo().get("day").toString());
		
		holidaysBase.close();
    }

}
