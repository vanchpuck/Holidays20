//package com.jonnygold.holidays.calendar;
//
//import java.util.Calendar;
//import java.util.HashSet;
//import java.util.Set;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.database.sqlite.SQLiteException;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//public class NewHolidayDialog extends AlertDialog{
//	
//	public static class NewHolidayView extends LinearLayout{
//		public NewHolidayView(Context context, AttributeSet attrs) {
//			super(context, attrs);
//		}
//	}
//	
//	private class OnClickListener implements DialogInterface.OnClickListener{
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			
//			HolidayDateChooser dateChooser = (HolidayDateChooser) findViewById(R.id.view_date_chooser);
//			EditText title = (EditText) findViewById(R.id.view_txt_new_title); 
//			EditText description = (EditText) findViewById(R.id.view_txt_new_description); 
//			
//			HolidayDate date = dateChooser.getDate();
//			if(date == null)
//				return;
//			
//			holidaysBase = HolidaysDataSource.getInstance(getContext());
//			
//			holidaysBase.openForWriting();
//			
//			Set<Country> country = new HashSet<Country>();
//			country.add(new CountryWorld());
//			
//			Holiday holiday = new Holiday(
//					-1, 
//					title.getText().toString(), 
//					date.toString(), 
//					Holiday.Type.USER_HOLIDAY, 
//					getContext().getResources().getDrawable(R.drawable.ic_launcher), 
//					description.getText().toString(), 
//					country, 
//					date
//			);
//			
//			
//			try{
//			holidaysBase.saveHoliday(holiday);
//			}
//			catch(SQLiteException exc){
//				Toast.makeText(getContext(), "Не удалось сохранить запись.", Toast.LENGTH_SHORT).show();
//			}
//			holidaysBase.updateFloatHolidays(Calendar.getInstance().get(Calendar.YEAR));
////			holidaysBase.close();
//		}
//	}
//	
//	private HolidaysDataSource holidaysBase;
//	
//	public NewHolidayDialog(Context context) {
//		super(context);
//		this.setView(View.inflate(getContext(), R.layout.view_new_holiday, null));
//		this.setButton(BUTTON_POSITIVE, "Сохранить", new OnClickListener());
//		this.setTitle("Новый праздник");
//	}	
//
//
//}
