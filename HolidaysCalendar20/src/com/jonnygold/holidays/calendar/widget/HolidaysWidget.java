package com.jonnygold.holidays.calendar.widget;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jonnygold.holidays.calendar.Country;
import com.jonnygold.holidays.calendar.CountryBelorussia;
import com.jonnygold.holidays.calendar.CountryRussia;
import com.jonnygold.holidays.calendar.CountryUkrane;
import com.jonnygold.holidays.calendar.CountryUser;
import com.jonnygold.holidays.calendar.CountryWorld;
import com.jonnygold.holidays.calendar.Holiday;
import com.jonnygold.holidays.calendar.HolidayDate;
import com.jonnygold.holidays.calendar.HolidaysActivity;
import com.jonnygold.holidays.calendar.HolidaysDataSource;
import com.jonnygold.holidays.calendar.Month;
import com.jonnygold.holidays.calendar.SettingsActivity;
import com.jonnygold.holidays.calendar.WeekDay;
import com.jonnygold.holidays.calendar.HolidaysDataSource.QueryRestriction;
import com.jonnygold.holidays.calendar.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

public abstract class HolidaysWidget extends AppWidgetProvider{
		
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
		
		// ������������� ������� ����
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
            
            // ������� ������
            cleanWidget(views);
            
            // ������� ����
            views.setTextViewText(R.id.view_txt_curr_date, currDateStr);
            
            int rowCount = getRowCount(sharedPref);
            
            Holiday holiday = null;
            DataRow[] dataRows = DataRow.values();
            HolidayDate date = null;
            String strDate = null;
            String title = null;
            for(int idx=0; idx<rowCount && idx<holidays.size(); idx++){
            	holiday = holidays.get(idx);
            	date = holidays.get(idx).getDate();
            	
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
		
		Log.w("Step", 2+"");
		
		QueryRestriction restriction = new HolidaysDataSource.QueryRestriction();
		restriction.setDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
			.includeAfter();
		
		Log.w("Step", 3+"");
		
		List<Integer> countryIdList = new ArrayList<Integer>(4);		
		if(sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)){
			Log.w("WorldCountry", sharedPref.getBoolean(SettingsActivity.KEY_WORLD_HOLIDAYS, true)+"");
			countryIdList.add(CountryWorld.ID);
		}
		if(sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)){
			Log.w("RusCountry", sharedPref.getBoolean(SettingsActivity.KEY_RUSSIAN_HOLIDAYS, true)+"");
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
		
//		db.openForReading();
		List<Holiday> holidays = db.getHolidays(restriction);
//		db.close();
		Log.w("###WIDGET UPDATE###", "GOOD!!!!");
		return holidays;
	}

}
