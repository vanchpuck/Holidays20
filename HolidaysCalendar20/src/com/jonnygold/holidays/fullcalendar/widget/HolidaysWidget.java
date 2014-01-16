package com.jonnygold.holidays.fullcalendar.widget;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jonnygold.holidays.fullcalendar.Country;
import com.jonnygold.holidays.fullcalendar.CountryBelorussia;
import com.jonnygold.holidays.fullcalendar.CountryRussia;
import com.jonnygold.holidays.fullcalendar.CountryUkrane;
import com.jonnygold.holidays.fullcalendar.CountryUser;
import com.jonnygold.holidays.fullcalendar.CountryWorld;
import com.jonnygold.holidays.fullcalendar.Holiday;
import com.jonnygold.holidays.fullcalendar.HolidayDate;
import com.jonnygold.holidays.fullcalendar.HolidaysActivity;
import com.jonnygold.holidays.fullcalendar.HolidaysDataSource;
import com.jonnygold.holidays.fullcalendar.Month;
import com.jonnygold.holidays.fullcalendar.R;
import com.jonnygold.holidays.fullcalendar.SettingsActivity;
import com.jonnygold.holidays.fullcalendar.WeekDay;
import com.jonnygold.holidays.fullcalendar.HolidaysDataSource.QueryRestriction;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public abstract class HolidaysWidget extends AppWidgetProvider{
	
	private static final int TODAYS_COLOR = 0xFFB7F0FF;
	
	private static final int OTHERS_COLOR = 0xBBB7F0FF;
	
//	public static final int MAX_TITLE_LENGTH = 29;
	
	private HolidaysDataSource db;
	
	private Calendar calendar;
	
	private SharedPreferences sharedPref;
	
	public abstract int getMaxRowCount();
	
	public abstract int getRowCount(SharedPreferences pref);
	
	public abstract int getLayoutId();
	
	public abstract int getOnClickView();
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		
		db = HolidaysDataSource.newInstance(context);
		db.openForReading();
		
		sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		
		DecimalFormat formatter = new DecimalFormat("00");
		
		Calendar calendar = Calendar.getInstance();
        String currDateStr = " "
        		+calendar.get(Calendar.DAY_OF_MONTH)+" "
        		+Month.values()[calendar.get(Calendar.MONTH)].getGenitive()+" - "
        		+WeekDay.values()[calendar.get(Calendar.DAY_OF_WEEK)-1]+" ";
		
		for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, HolidaysActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), getLayoutId());
            views.setOnClickPendingIntent(getOnClickView(), pendingIntent);

            List<Holiday> holidays = getHolidays();
            
            cleanWidget(views);
            
            views.setTextViewText(R.id.view_txt_curr_date, currDateStr);
            views.setTextColor(R.id.view_txt_curr_date, TODAYS_COLOR);
            
            int rowCount = getRowCount(sharedPref);
            
            Holiday holiday = null;
            DataRow[] dataRows = DataRow.values();
            HolidayDate date = null;
            String strDate = null;
            String title = null;
            for(int idx=0; idx<rowCount && idx<holidays.size(); idx++){
            	holiday = holidays.get(idx);
            	date = holidays.get(idx).getDate();
            	
            	// Set text color
            	if(date.getActualDay() == calendar.get(Calendar.DAY_OF_MONTH) && 
            			date.getActualMonth() == calendar.get(Calendar.MONTH)){
            		setRowColor(views, dataRows[idx], TODAYS_COLOR);
            	}
            	else{
            		setRowColor(views, dataRows[idx], OTHERS_COLOR);
            	}
            	
            	strDate = formatter.format(date.getActualDay())+"."+(formatter.format(date.getActualMonth()+1))+" - ";
            	views.setTextViewText(dataRows[idx].getDateView(), strDate);
                
                int c = 0;
//                int titleLen = MAX_TITLE_LENGTH;
                for(Country cntr : holiday.getCountries()){
                	views.setImageViewResource(dataRows[idx].getFlagViews()[c], cntr.getDrawableId());
                	if(c >= rowCount)
                		break;
                	c++;
                }
                
                title = holidays.get(idx).getTitle().toUpperCase();
                views.setTextViewText(dataRows[idx].getTitleView(), title);
            }
            
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
		db.close();
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	private void setRowColor(RemoteViews views, DataRow row, int color){
		views.setTextColor(row.getDateView(), color);
		views.setTextColor(row.getTitleView(), color);
	}
	
	private void cleanWidget(RemoteViews views){
		DataRow[] dataRows = DataRow.values();
		for(int idx=0; idx<getMaxRowCount(); idx++){
			views.setTextViewText(dataRows[idx].getDateView(), null);
			views.setTextViewText(dataRows[idx].getTitleView(), null);
			for(int flagView: dataRows[idx].getFlagViews()){
				views.setImageViewResource(flagView, R.drawable.hole);
			}
		}
	}
	
	private List<Holiday> getHolidays(){
		calendar = Calendar.getInstance();
		
		
		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
		restriction.setDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
			.includeAfter();
		
		
		List<Integer> countryIdList = new ArrayList<Integer>(4);		
		if(sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)){
			countryIdList.add(CountryWorld.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)){
			countryIdList.add(CountryRussia.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_BELORUSSIAN_HOLIDAYS, true)){
			countryIdList.add(CountryBelorussia.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_UKRANE_HOLIDAYS, true)){
			countryIdList.add(CountryUkrane.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_USER_HOLIDAYS, true)){
			countryIdList.add(CountryUser.ID);
		}
		restriction.setCountryes(countryIdList)
			.setLimit(getRowCount(sharedPref));
		
		
		List<Holiday> holidays = db.getHolidays(restriction);
		return holidays;
	}

}
