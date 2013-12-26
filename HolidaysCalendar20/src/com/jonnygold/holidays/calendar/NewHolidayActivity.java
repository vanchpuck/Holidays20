//package com.jonnygold.holidays.calendar;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import android.database.sqlite.SQLiteException;
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public final class NewHolidayActivity extends ActionBarActivity {
//
//	private HolidaysDataSource holidaysBase;
//	
//	private HolidayDateChooser dateChooser;
//	
//	private EditText title;
//	
//	private EditText description;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_new_holiday);
//		
//		dateChooser = (HolidayDateChooser) findViewById(R.id.view_date_chooser);
//		
//		title = (EditText) findViewById(R.id.view_txt_new_title); 
//		
//		description = (EditText) findViewById(R.id.view_txt_new_description); 
//		
//	}
//	
//	@Override
//	protected void onStop() {
//		super.onStop();
//	}
//	
//	public void saveHoliday(View view){
//		HolidayDate date = dateChooser.getDate();
//		if(date == null)
//			return;
//		
//		holidaysBase = HolidaysDataSource.getInstance(this);
//		
//		holidaysBase.openForWriting();
//		
//		Set<Country> country = new HashSet<Country>();
//		country.add(new CountryUser());
//		
//		Holiday holiday = new Holiday(
//				-1, 
//				title.getText().toString(), 
//				date.toString(), 
//				Holiday.Type.USER_HOLIDAY, 
//				getResources().getDrawable(R.drawable.ic_launcher), 
//				description.getText().toString(), 
//				country, 
//				date
//		);
//		
//		
//		try{
//		holidaysBase.saveHoliday(holiday);
//		}
//		catch(SQLiteException exc){
//			Toast.makeText(this, "Не удалось сохранить запись.", Toast.LENGTH_SHORT).show();
//		}
//		
//		holidaysBase.close();
//    }
//
//}
