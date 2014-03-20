package com.jonnygold.holidays.fullcalendar;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.DefaultPicture;
import com.jonnygold.holidays.fullcalendar.holiday.Holiday;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayDate;
import com.jonnygold.holidays.fullcalendar.widget.HolidaysWidget4x1;
import com.jonnygold.holidays.fullcalendar.widget.HolidaysWidget4x2;

public class HolidaysActivity extends ActionBarActivity implements OnQueryTextListener {
	
	private class NewHolidayDialog extends AlertDialog{
				
		private class OnClickListener implements DialogInterface.OnClickListener{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				HolidayDateChooser dateChooser = (HolidayDateChooser) findViewById(R.id.view_date_chooser);
				EditText title = (EditText) findViewById(R.id.view_txt_new_title); 
				EditText description = (EditText) findViewById(R.id.view_txt_new_description); 
				
				if(title.length() == 0){
					Toast.makeText(getContext(), R.string.msg_no_title, Toast.LENGTH_LONG).show();
					return;
				}
				
				HolidayDate date = dateChooser.getDate();
				if(date == null)
					return;

				Set<Country> country = new HashSet<Country>();
				country.add(Country.USER);
				
				Holiday holiday = new Holiday(
						-1, 
						title.getText().toString().toLowerCase(), 
						date.toString(), 
						Holiday.Type.USER_HOLIDAY, 
						DefaultPicture.getInstance(), 
						description.getText().toString(), 
						country, 
						date
				);
				
				
				try{
					holidaysBase.saveHoliday(holiday);
					
					updatePager();
					
					Toast.makeText(getContext(), R.string.msg_new_rec, Toast.LENGTH_SHORT).show();
				}
				catch(SQLiteException exc){
					Toast.makeText(getContext(), R.string.msg_del_err, Toast.LENGTH_LONG).show();
				}
			}
		}
			
		public NewHolidayDialog(Context context) {
			super(context);
			this.setView(View.inflate(getContext(), R.layout.view_new_holiday, null));
			this.setButton(BUTTON_POSITIVE, getString(R.string.str_save), new OnClickListener());
			this.setTitle(R.string.str_new_holiday);
		}	

	}
	
	private class UpdatePagerTask extends AsyncTask<Calendar, Calendar, Void>{

		private HolidaysDataSource ds;
		private View progressBarView = findViewById(R.id.view_progress_bar);
		private View pagerView = findViewById(R.id.view_pager);
		
		UpdatePagerTask(Context context){
			ds = HolidaysDataSource.newInstance(context);
		}
		
		@Override
		protected Void doInBackground(Calendar... params) {
			for(Calendar calendar : params){
				ds.openForReading();
				ds.updateFloatHolidays(calendar.get(Calendar.YEAR));
				
				publishProgress(calendar);
			}			
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			progressBarView.setVisibility(View.VISIBLE);
			pagerView.setVisibility(View.GONE);
		}
		
		@Override
		protected void onPostExecute(Void result) {
			progressBarView.setVisibility(View.GONE);
			pagerView.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onProgressUpdate(Calendar... values) {
			for(Calendar calendar : values){
				updatePager(calendar);
			}
		}
	}
	
	private static final int DATE_PICKER_DIALOG = 1;
	
	private HolidaysDataSource holidaysBase;
	
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holidays_pager);
		
		holidaysBase = HolidaysDataSource.newInstance(this);
		if(holidaysBase == null ){
			showMountError();
			return;
		}
		
		getSupportActionBar().setTitle(R.string.action_bar_tite);
	    
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		NotificationManager m = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		m.cancel(001);
		
		if(holidaysBase == null)
			return;
		
		holidaysBase.openForReading();
		
		updateWidgets();
		
		setPager(Calendar.getInstance());
		
		new UpdatePagerTask(this).execute(Calendar.getInstance());
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		holidaysBase.close();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		holidaysBase.close();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
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
		
		switch(item.getItemId()){
		case R.id.action_add_to_calendar : 
			exportToCalendar(holiday);
			return true;
		case R.id.action_del_holiday :
			if(holiday.isDeletable()){
				holidaysBase.deleteHoliday(holiday);
				updatePager();
			}
			else{
				Toast.makeText(this, R.string.msg_src_del_err, Toast.LENGTH_LONG).show();
			}
			return true;
		}
		return super.onContextItemSelected(item);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.holidays, menu);

	  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	  
	  
	  MenuItem searchItem = menu.findItem(R.id.action_search);
	  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	  	  
	  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	  searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
	  searchView.setQueryHint(getString(R.string.str_find_holiday));

	  return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_preferences :
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            return true; 
	        case R.id.action_calendars :
	            Intent calendIntent = new Intent(this, CalendarManagerActivity.class);
	            startActivity(calendIntent);
	            return true; 
	        case R.id.action_add_holiday :
	        	NewHolidayDialog addDialog = new NewHolidayDialog(this);
	        	addDialog.show();
	            return true; 
	        case R.id.action_go_to_date :
	        	showDialog(DATE_PICKER_DIALOG);
	        	return true;
	        case R.id.action_go_to_current_date :
//	        	new UpdatePagerTask().execute(Calendar.getInstance());
//	        	updatePager(Calendar.getInstance());
	        	new UpdatePagerTask(this).execute(Calendar.getInstance());
//	        	setPager(Calendar.getInstance());
	        	return true;
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
			OnDateSetListener myCallBack = new OnDateSetListener() {
	    		@Override
	    	    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    			Calendar calendar = Calendar.getInstance();
	    			calendar.clear();
	    			calendar.set(year, monthOfYear, dayOfMonth);
	    			
	    			new UpdatePagerTask(view.getContext()).execute(calendar);
//	    			updatePager(calendar);
	    	    }
        	};
        	Calendar calend = Calendar.getInstance();
			DatePickerDialog dpd = new DatePickerDialog(this, myCallBack, calend.get(Calendar.YEAR), calend.get(Calendar.MONTH), calend.get(Calendar.DAY_OF_MONTH));
			return dpd;
		}
		return super.onCreateDialog(id);
	}

	private void updatePager(){
        LinearLayout v = (LinearLayout)viewPager.findViewWithTag(viewPager.getCurrentItem());
        View vv = v.getChildAt(0);
        HolidaysListView hList = (HolidaysListView) vv;
        
        new UpdatePagerTask(this).execute(hList.getCalendar());
//        updatePager(hList.getCalendar());
	}

	private void updatePager(Calendar onDate){
//        holidaysBase.updateFloatHolidays(onDate.get(Calendar.YEAR));
	        
		DaysPagerAdapter pagerAdapter = new DaysPagerAdapter(this, holidaysBase, onDate);
        viewPager = (ViewPager) findViewById(R.id.view_pager); 
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(DaysPagerAdapter.START_POSITION);
	}

	private void setPager(Calendar onDate){        
        DaysPagerAdapter pagerAdapter = new DaysPagerAdapter(this, holidaysBase, onDate);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSaveEnabled(false);
        viewPager.setCurrentItem(DaysPagerAdapter.START_POSITION);     
        //Log.w("Limit", viewPager.getOffscreenPageLimit()+"");
		
//		updatePager(onDate);

        PagerTitleStrip strip = new PagerTitleStrip(this);
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.TOP;
        viewPager.removeAllViews();
        viewPager.addView(strip, layoutParams);

        //setContentView(viewPager);  
	}
	
	private void exportToCalendar(Holiday holiday){
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MONTH, holiday.getDate().getActualMonth());
		date.set(Calendar.DAY_OF_MONTH, holiday.getDate().getActualDay()+1);
		Intent intent = new Intent(Intent.ACTION_EDIT)
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
		builder.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		});
		builder.setTitle(R.string.db_err_title).setMessage(R.string.db_err_message).show();
	}

}
