package com.jonnygold.holidays.fullcalendar;

import java.util.ArrayList;
import java.util.List;

import com.jonnygold.holidays.fullcalendar.holiday.Calendar;
import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.holiday.CountryStateManager;
import com.jonnygold.holidays.fullcalendar.web.UpdateService;
import com.jonnygold.holidays.fullcalendar.web.UpdateService.UpdateState;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class CalendarManagerActivity extends ActionBarActivity {

	private static class Notifier{
		
		public static final int NOTIFICATION_ID = 1;
		
		private Context context;
		
		private NotificationManager notificationMgr;
		
		public Notifier(Context context) {
			this.context = context;
			this.notificationMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		}
		
		public NotificationManager getNotificationManager(){
			return notificationMgr;
		}
		
		public void showLoadingNotification(Country country){			
			PendingIntent intent = PendingIntent.getActivity(
					context,
					0,
					new Intent(/*context, CalendarManagerActivity.class*/),
			    	PendingIntent.FLAG_UPDATE_CURRENT
			);
			
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				    .setSmallIcon(country.getDrawableId())
				    .setProgress(0, 0, true)
				    .setContentTitle(country.getName())
				    .setContentText(context.getResources().getString(R.string.msg_downloading))
				    .setContentIntent(intent);
			
			// Builds the notification and issues it.
			notificationMgr.notify(NOTIFICATION_ID, mBuilder.build());
		}
		
		public void showDoneNotification(Country country, UpdateState state){
			PendingIntent intent = PendingIntent.getActivity(
					context,
					0,
					new Intent(context, HolidaysActivity.class),
			    	PendingIntent.FLAG_UPDATE_CURRENT
			);
			
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				    .setSmallIcon(country.getDrawableId())
				    .setContentTitle(state.title)
				    .setContentText(state.message)
				    .setContentIntent(intent)
				    .setAutoCancel(true);
			
			// Builds the notification and issues it.
			notificationMgr.notify(NOTIFICATION_ID, mBuilder.build());
		}
		
	}
	
	private class DeleteTask extends AsyncTask<Country, Country, Country[]>{

		private Context context;
		
		private HolidaysDataSource db;
		
		private AlertDialog dialog;
		
		public DeleteTask(Context context){
			this.context = context;
			this.db = HolidaysDataSource.newInstance(context);
		}
		
		@Override
		protected Country[] doInBackground(Country... params) {
			db.openForWriting();
			for(Country country : params){
				db.beginTransaction();
				try{
					db.deleteCountry(country);
					db.setTransactionSuccessful();
				} catch (SQLiteException exc){
					// TODO Сообщение об ошибке
				}
				finally{
					db.endTransaction();
					db.close();
				}
			}
			publishProgress();
			return params;
		}
		
		@Override
		protected void onPreExecute() {
			ContextThemeWrapper wrapper = new ContextThemeWrapper(context, R.style.DialogBaseTheme);
			
			dialog = new AlertDialog.Builder(wrapper)
					.setTitle("Удаление календаря...")
					.setCancelable(false)
					.setView(LayoutInflater.from(wrapper).inflate(R.layout.view_loading_dialog, null))
					.setOnCancelListener(new DialogInterface.OnCancelListener() {						
						@Override
						public void onCancel(DialogInterface dialog) {
							
						}
					}).create();
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(Country[] countries) {
			for(Country country : countries){
				SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
	    		sharedPref.edit().remove(country.getKey());
			}
			dialog.cancel();
		}
		
		@Override
		protected void onProgressUpdate(Country ... values) {
			fillList();
		}
		
	}
	
	private class CalendarsListAdapter extends BaseExpandableListAdapter{

		private Context context;
		
		private LayoutInflater inflater;
		
		private CountryStateManager.State[] groups;
		
		private List<List<Country>> countries;
		
		CalendarsListAdapter (Context context, List<Calendar> calendars){
			this.context = context;
			this.inflater = LayoutInflater.from(context);
			this.countries = new ArrayList<List<Country>>();
			
			CountryStateManager stateManager = new CountryStateManager(context);
			
			groups = CountryStateManager.State.values();
			
			for(@SuppressWarnings("unused") CountryStateManager.State state : groups){
				this.countries.add(new ArrayList<Country>());
			}
			
			for(Calendar calendar : calendars){
				for(int i=0; i<groups.length; i++){ 
					if(groups[i] == stateManager.getState(calendar.country)){
						this.countries.get(i).add(calendar.country);
					}
				}
			}
		}
		
		@Override
	    public boolean areAllItemsEnabled()
	    {
	        return true;
	    }
		
		@Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return countries.get(groupPosition).get(childPosition);
	    }

	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	   
	    // Return a child view. You can load your custom layout here.
	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
	            View convertView, ViewGroup parent) {
	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.view_calend_item, null);
	        }
	        
	        Country country = countries.get(groupPosition).get(childPosition);
	        
	        TextView titeView = (TextView)convertView.findViewById(R.id.view_calend_title);
			titeView.setText(country.getName());
			titeView.setCompoundDrawablesWithIntrinsicBounds(null, null, 
					context.getResources().getDrawable(country.getDrawableId()), null);
			
	        return convertView;
	    }

	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return countries.get(groupPosition).size();
	    }

	    @Override
	    public Object getGroup(int groupPosition) {
	        return groups[groupPosition];
	    }

	    @Override
	    public int getGroupCount() {
	        return groups.length;
	    }

	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }

	    // Return a group view. You can load your custom layout here.
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
	            ViewGroup parent) {
	    	CountryStateManager.State group = (CountryStateManager.State) getGroup(groupPosition);
	        if (convertView == null) {
	            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
	        }
	        ((TextView)convertView).setText(group.toString());
	        
	        return convertView;
	    }

	    @Override
	    public boolean hasStableIds() {
	        return true;
	    }

	    @Override
	    public boolean isChildSelectable(int arg0, int arg1) {
	        return true;
	    }		
	}
	
	
	public class OnCalendarClickListener implements OnChildClickListener{		
		private Context context;
		
		private CountryStateManager stateManager;
		
		public OnCalendarClickListener (Context context){
			this.context = context;
			this.stateManager = new CountryStateManager(context);
		}
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			
			CalendarsListAdapter adapter = (CalendarsListAdapter)parent.getExpandableListAdapter();
			
			final Country country = (Country) adapter.getChild(groupPosition, childPosition);

			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
			dialogBuilder.setTitle(country.getName())
					.setIcon(country.getDrawableId())
					.setMessage(Calendar.getCalendar(country).description)
					.setNegativeButton("Отмена", null);
			
			switch (stateManager.getState(country)) {
			case INSTALLED :
				dialogBuilder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new DeleteTask(context).execute(country);
					}
				});
				break;
			case NOT_INSTALLED :
				dialogBuilder.setPositiveButton("Загрузить", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startDownloading(country);
					}
				});
				break;
			default:
				return false;
			}
			dialogBuilder.create().show();
			
			return true;
		}
	}
	
	
		
	
	private class DownloadStateReceiver extends BroadcastReceiver
	{
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	UpdateService.UpdateState response = 
	    			(UpdateService.UpdateState) intent.getExtras().getSerializable(UpdateService.UPDATE_STATUS);
	    	
	    	Country country = 
	    			CountryManager.getInstance().getCountry(intent.getExtras().getInt(UpdateService.TARGET_COUNTRY_ID));
	    	
	    	notifier.showDoneNotification(country, response);
	    	
	    	if(loadingDialog != null){
	    		loadingDialog.cancel();
	    	}
	    	
	    	if(response == UpdateState.SUCCESS){
	    		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
	    		sharedPref.edit().putBoolean(country.getKey(), true).apply();
	    		fillList();
	    	}
	    	
//	    	showProgress(false);
	    	
	    }
	}
	
	
	
	private Notifier notifier;
	
	private AlertDialog loadingDialog;
	
	private ExpandableListView calendarsView;
	
	private View progressBarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_manager);
		
		getSupportActionBar().setTitle(R.string.str_manager_action_bar_tite);
		
		notifier = new Notifier(this);
		
		IntentFilter mStatusIntentFilter = new IntentFilter(
				UpdateService.BROADCAST_ACTION);

		DownloadStateReceiver mDownloadStateReceiver =
                new DownloadStateReceiver();
		
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mDownloadStateReceiver,
                mStatusIntentFilter);
		
		
		calendarsView = (ExpandableListView)findViewById(R.id.view_calendars);
//		progressBarView = findViewById(R.id.view_loading_progress_bar);
		
		calendarsView.setOnChildClickListener(new OnCalendarClickListener(this));
		
//		showProgress(false);
//		calendarsView.setOnChildClickListener(new OnCalendarClickListener(this));
		
		fillList();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_manager, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_preferences :
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            return true; 
	        case R.id.action_full_version :
	        	openMarketLink();
	            return true; 
	        default :
	        	return true;
	    }
	    
	}
	
	private void openMarketLink(){
		try{
			Intent marketIntent = new Intent(Intent.ACTION_VIEW);
	        marketIntent.setData(Uri.parse("market://details?id=com.jonnygold.holidays.fullcalendar"));
	        startActivity(marketIntent);
		} catch (Exception exc){
			new AlertDialog.Builder(this)
					.setTitle(R.string.msg_error)
					.setMessage(R.string.msg_market_link_error)
					.setPositiveButton(R.string.msg_ok, null)
					.create()
					.show();
		}
	}
		
	public void startDownloading(Country country){
		final Intent serviceIntent = new Intent(this, UpdateService.class);
		serviceIntent.putExtra(UpdateService.TARGET_COUNTRY_ID, country.getId());
		
		startService(serviceIntent);
		notifier.showLoadingNotification(country);
		
		ContextThemeWrapper wrapper = new ContextThemeWrapper(this, R.style.DialogBaseTheme);
		
//		HolidayDetailView detailView = (HolidayDetailView)LayoutInflater.from(wrapper).inflate(R.layout.dialog_detail, null);
//		detailView.setHoliday(holiday);
		
		loadingDialog = new AlertDialog.Builder(wrapper)
				.setTitle("Установка календаря...")
				.setCancelable(false)
				.setView(LayoutInflater.from(wrapper).inflate(R.layout.view_loading_dialog, null))
				.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						notifier.getNotificationManager().cancel(Notifier.NOTIFICATION_ID);
						stopService(serviceIntent);
					}
				}).create();
		loadingDialog.show();
		
//		showProgress(true);
	}
		
	private void fillList(){	
		List<Calendar> calendars = new ArrayList<Calendar>();
		for(Calendar c : Calendar.values()){
			calendars.add(c);
		}
		CalendarsListAdapter adapter = new CalendarsListAdapter(this, calendars);
		calendarsView.setAdapter(adapter);
		
		for (int i = 0; i < adapter.getGroupCount(); i++)
			calendarsView.expandGroup(i);
	}
				
}
