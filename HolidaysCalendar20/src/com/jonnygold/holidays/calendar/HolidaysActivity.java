package com.jonnygold.holidays.calendar;


import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.jonnygold.holidays.calendar.widget.HolidaysWidget4x1;
import com.jonnygold.holidays.calendar.widget.HolidaysWidget4x2;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class HolidaysActivity extends ActionBarActivity implements OnQueryTextListener {

//	public static SharedPreferences prefere;// = PreferenceManager.getDefaultSharedPreferences(collection.getContext());
	
//	private static class NewHolidayView extends LinearLayout{
//		public NewHolidayView(Context context, AttributeSet attrs) {
//			super(context, attrs);
//		}
//	}
	
	private class NewHolidayDialog extends AlertDialog{
				
		private class OnClickListener implements DialogInterface.OnClickListener{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				HolidayDateChooser dateChooser = (HolidayDateChooser) findViewById(R.id.view_date_chooser);
				EditText title = (EditText) findViewById(R.id.view_txt_new_title); 
				EditText description = (EditText) findViewById(R.id.view_txt_new_description); 
				
				if(title.length() == 0){
					Toast.makeText(getContext(), "Не указано название праздника.", Toast.LENGTH_LONG).show();
					return;
				}
				
				HolidayDate date = dateChooser.getDate();
				if(date == null)
					return;
				
				holidaysBase = HolidaysDataSource.getInstance(getContext());
				
				holidaysBase.openForWriting();
				
				Set<Country> country = new HashSet<Country>();
				country.add(new CountryUser());
				
				Holiday holiday = new Holiday(
						-1, 
						title.getText().toString(), 
						date.toString(), 
						Holiday.Type.USER_HOLIDAY, 
						getContext().getResources().getDrawable(R.drawable.ic_launcher), 
						description.getText().toString(), 
						country, 
						date
				);
				
				
				try{
					holidaysBase.saveHoliday(holiday);
										
					holidaysBase.updateFloatHolidays(Calendar.getInstance().get(Calendar.YEAR));
					
//					Calendar calend = Calendar.getInstance();
//					calend.set(Calendar.YEAR, dateTag.getYear());
								
					DaysPagerAdapter adapter = (DaysPagerAdapter)viewPager.getAdapter();
					setPager(adapter.getCurrentView());
//					viewPager.setCurrentItem(viewPager.getCurrentItem());
					
					Toast.makeText(getContext(), "Запись добавлена.", Toast.LENGTH_SHORT).show();
				}
				catch(SQLiteException exc){
					Toast.makeText(getContext(), "Не удалось сохранить запись.", Toast.LENGTH_LONG).show();
				}
			}
		}
		
		private HolidaysDataSource holidaysBase;
		
		public NewHolidayDialog(Context context) {
			super(context);
			this.setView(View.inflate(getContext(), R.layout.view_new_holiday, null));
			this.setButton(BUTTON_POSITIVE, "Сохранить", new OnClickListener());
			this.setTitle("Новый праздник");
		}	


	}
	
	
	
	private static final int DATE_PICKER_DIALOG = 1;
	
	private HolidaysDataSource holidaysBase;
	
//	private Calendar calendar;
	
//	private DaysPagerAdapter pagerAdapter;
	
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holidays);
		
		holidaysBase = HolidaysDataSource.getInstance(this);
		if(holidaysBase == null){
			showMountError();
			return;
		}
		
		holidaysBase.openForReading();
		
		getSupportActionBar().setTitle("Праздники");
	    
//	    registerForContextMenu(findViewById(R.id.view_holidays));
	    
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if(holidaysBase == null)
			return;
			
		Calendar calendar = Calendar.getInstance();
		
		holidaysBase.openForReading();
		
		holidaysBase.updateFloatHolidays(calendar.get(Calendar.YEAR));
				
		setPager(calendar);
        
        updateWidgets();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		holidaysBase.close();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		holidaysBase.openForReading();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, R.id.action_add_to_calendar, Menu.NONE, R.string.action_add_to_calendar);
		menu.add(Menu.NONE, R.id.action_del_holiday, Menu.NONE, R.string.action_del_holiday);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
		
		Holiday holiday = (Holiday) ((HolidaysListView)acmi.targetView.getParent()).getAdapter().getItem(acmi.position);
		
		holidaysBase.openForReading();
		
		switch(item.getItemId()){
		case R.id.action_add_to_calendar : 
			exportToCalendar(holiday);
			return true;
		case R.id.action_del_holiday :
			if(holiday.isDeletable()){
				holidaysBase.deleteHoliday(holiday);
				setPager(Calendar.getInstance());
			}
			else{
				Toast.makeText(this, "Только праздники пользователя доступны для удаления.", Toast.LENGTH_LONG).show();
			}
			return true;
		}
		return super.onContextItemSelected(item);
//		
//		
//		if (item.getItemId() == R.id.action_add_to_calendar) {
//			
//			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
//			
//			Holiday holiday = (Holiday) ((HolidaysListView)acmi.targetView.getParent()).getAdapter().getItem(acmi.position);
//						
//			Calendar date = Calendar.getInstance();
//			date.set(Calendar.MONTH, holiday.getDate().getActualMonth());
//			date.set(Calendar.DAY_OF_MONTH, holiday.getDate().getActualDay());
//			Intent intent = new Intent(Intent.ACTION_EDIT)
////			        .setData(Uri.parse("content://com.android.calendar/events"))
//			        .setType("vnd.android.cursor.item/event")
//			        .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
//			        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTimeInMillis())
//			        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.getTimeInMillis())
//			        .putExtra(Events.TITLE, holiday.getTitle())
//			        .putExtra(Events.DESCRIPTION, holiday.getDescription());
//			startActivity(intent);
//			
//			return true;
//		}
//		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.holidays, menu);

	  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	  
	  
	  MenuItem searchItem = menu.findItem(R.id.action_search);
	  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//	  if (searchView != null) {
//	     searchView.setOnQueryTextListener(this);
//	  }
	  
	  SearchableActivity act = new SearchableActivity();
	  
	  if(searchManager.getSearchableInfo(getComponentName()) == null){
		  Log.w("NULL1", "NULL11");
	  }
	  if(searchManager.getSearchableInfo(act.getComponentName()) == null){
		  Log.w("NULL1", "NULL11");
	  }
	  
//	  Log.w("1", getComponentName().getClassName());
//	  Log.w("1", getComponentName().getShortClassName());
//	  Log.w("1", getComponentName().getPackageName());
	  
	  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	  searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
	  searchView.setQueryHint("Поиск праздника");

	  return super.onCreateOptionsMenu(menu);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.holidays, menu);
//		
//		Log.w("test1", "1");
//		
//		// Get the SearchView and set the searchable configuration
//	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//	    
//	    Log.w("test1", "2");
//	    
//	    MenuItem searchItem = menu.findItem(R.id.action_search);
//	    
//	    
////	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
////        if (searchView != null) {
////            SearchViewCompat.setOnQueryTextListener(searchView, mOnQueryTextListener);
////            searchView.setIconifiedByDefault(false);
////            Log.d("Test","SearchView not null");
////        } else
////            Log.d("Test", "SearchView is null");
//	    
//	    
//
//	    // Assumes current activity is the searchable activity
//	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	    
//	    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
//
//	    Log.w("test1", "6");
//		
//		return true;
//	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_preferences :
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            return true; 
	        case R.id.action_add_holiday :
	        	NewHolidayDialog addDialog = new NewHolidayDialog(this);
//	        	addDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//					@Override
//					public void onDismiss(DialogInterface dialog) {
//						Log.w("D", "D");
//						setPager(Calendar.getInstance());
//					}
//				});
	        	addDialog.show();
	            return true; 
	        case R.id.action_go_to_date :
	        	showDialog(DATE_PICKER_DIALOG);
	        case R.id.action_go_to_current_date :
	        	setPager(Calendar.getInstance());
	        default :
	        	return true;
	    }
	    
	}
	
	public HolidaysDataSource getHolidaysBase(){
		return holidaysBase;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}
	
	protected Dialog onCreateDialog(int id) {
		if (id == DATE_PICKER_DIALOG) {
			/*
			 * При открытии диалога сбрасывается дата
			 */
			OnDateSetListener myCallBack = new OnDateSetListener() {
	    		@Override
	    	    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    			Calendar calendar = Calendar.getInstance();
	    			calendar.clear();
	    			calendar.set(year, monthOfYear, dayOfMonth);
	    			
	    			setPager(calendar);
	    	    }
        	};
        	Calendar calend = Calendar.getInstance();
			DatePickerDialog dpd = new DatePickerDialog(this, myCallBack, calend.get(Calendar.YEAR), calend.get(Calendar.MONTH), calend.get(Calendar.DAY_OF_MONTH));
			return dpd;
		}
		return super.onCreateDialog(id);
	}
	
//	private void setPager(Holiday holiday){
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.MONTH, holiday.getDate().getActualMonth());
//		calendar.set(Calendar.DAY_OF_MONTH, holiday.getDate().getActualDay());
//		setPager(calendar);
//	}
	
	private void setPager(Calendar onDate){
		
		holidaysBase.updateFloatHolidays(onDate.get(Calendar.YEAR));
//		holidaysBase.updateFloatHolidays(Calendar.getInstance().get(Calendar.YEAR));
		
		DaysPagerAdapter pagerAdapter = new DaysPagerAdapter(this, holidaysBase, onDate);
		viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSaveEnabled(false);
        viewPager.setCurrentItem(DaysPagerAdapter.START_POSITION);     
        Log.w("Limit", viewPager.getOffscreenPageLimit()+"");

        PagerTitleStrip strip = new PagerTitleStrip(this);
		ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
		layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
		layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
		layoutParams.gravity = Gravity.TOP;
        viewPager.addView(strip, layoutParams);
        
        setContentView(viewPager);  
	}
	
	private void exportToCalendar(Holiday holiday){
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MONTH, holiday.getDate().getActualMonth());
		date.set(Calendar.DAY_OF_MONTH, holiday.getDate().getActualDay());
		Intent intent = new Intent(Intent.ACTION_EDIT)
//		        .setData(Uri.parse("content://com.android.calendar/events"))
		        .setType("vnd.android.cursor.item/event")
		        .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
		        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTimeInMillis())
		        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.getTimeInMillis())
		        .putExtra(Events.TITLE, holiday.getTitle())
		        .putExtra(Events.DESCRIPTION, holiday.getDescription());
		startActivity(intent);
	}
	
	private void updateWidgets(){
        int[] ids1 = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), HolidaysWidget4x1.class));
        int[] ids2 = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), HolidaysWidget4x2.class));
        
        Intent intent = new Intent(this, HolidaysWidget4x1.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids1);
        sendBroadcast(intent); 
        
        intent = new Intent(this, HolidaysWidget4x2.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids2);
        sendBroadcast(intent); 
    }
	
	private void showMountError(){
		Builder builder = new AlertDialog.Builder(this); 
		builder.setPositiveButton("Ок", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		});
		builder.setTitle(R.string.db_err_title).setMessage(R.string.db_err_message).show();
	}

}
