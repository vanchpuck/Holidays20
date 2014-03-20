package com.jonnygold.holidays.fullcalendar;

import java.util.ArrayList;
import java.util.List;

import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.holiday.CountryStateManager;
import com.jonnygold.holidays.fullcalendar.holiday.Holiday;
import com.jonnygold.holidays.fullcalendar.web.UpdateService;
import com.jonnygold.holidays.fullcalendar.web.UpdateServiceTest;
import com.jonnygold.holidays.fullcalendar.web.UpdateServiceTest.UpdateState;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarManagerActivity extends ActionBarActivity {

	private static class Notifier{
		
		private static final int NOTIFICATION_ID = 1;
		
		private Context context;
		
		private PendingIntent intent;
		
		private NotificationManager notificationMgr;
		
		public Notifier(Context context) {
			this.context = context;
			this.intent = PendingIntent.getActivity(
					context,
					0,
					new Intent(context, CalendarManagerActivity.class),
			    	PendingIntent.FLAG_UPDATE_CURRENT
			);
			this.notificationMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		}
		
		public void showLoadingNotification(Country country){			
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
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				    .setSmallIcon(country.getDrawableId())
				    .setContentTitle(state.title)
				    .setContentText(state.message)
				    .setContentIntent(intent)
				    .setAutoCancel(true);
			
			// Builds the notification and issues it.
			notificationMgr.notify(NOTIFICATION_ID, mBuilder.build());
		}
		
//		public void showErrorNotification(Country country){
//			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//		    .setSmallIcon(R.drawable.ic_launcher)
//		    .setContentTitle(country.getName())
//		    .setContentText(context.getResources().getString(R.string.msg_not_downloaded))
//		    .setContentIntent(intent);
//	
//			// Builds the notification and issues it.
//			notificationMgr.notify(NOTIFICATION_ID, mBuilder.build());
//		}
		
	}
	
	private class CalendarsListAdapter extends BaseExpandableListAdapter{

		private Context context;
		
		private LayoutInflater inflater;
		
		private CountryStateManager.State[] groups;
		
		private List<List<Country>> countries;
		
		CalendarsListAdapter (Context context, List<Country> countries){
			this.context = context;
			this.inflater = LayoutInflater.from(context);
			this.countries = new ArrayList<List<Country>>();
			
			CountryStateManager stateManager = new CountryStateManager(context);
			
			groups = CountryStateManager.State.values();
			
			for(@SuppressWarnings("unused") CountryStateManager.State state : groups){
				this.countries.add(new ArrayList<Country>());
			}
			
			for(Country country : countries){
				for(int i=0; i<groups.length; i++){ 
					if(groups[i] == stateManager.getState(country)){
						this.countries.get(i).add(country);
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
		
		public OnCalendarClickListener (Context context){
			this.context = context;
		}
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			
			CalendarsListAdapter adapter = (CalendarsListAdapter)parent.getExpandableListAdapter();
			
			@SuppressWarnings("unused")
			Object o = adapter.getChild(groupPosition, childPosition);
			
			Country country = (Country) adapter.getChild(groupPosition, childPosition);

			startDownloading(country);
			
			return false;
		}
	}
	
	
		
	
	private class DownloadStateReceiver extends BroadcastReceiver
	{
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	UpdateServiceTest.UpdateState response = 
	    			(UpdateServiceTest.UpdateState) intent.getExtras().getSerializable(UpdateServiceTest.UPDATE_STATUS);
	    	
	    	Country country = 
	    			CountryManager.getInstance().getCountry(intent.getExtras().getInt(UpdateServiceTest.TARGET_COUNTRY_ID));
	    	
	    	notifier.showDoneNotification(country, response);
	    }
	}
	
	
	
	private Notifier notifier;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_manager);
		
		notifier = new Notifier(this);
		
		IntentFilter mStatusIntentFilter = new IntentFilter(
                UpdateServiceTest.BROADCAST_ACTION);

		DownloadStateReceiver mDownloadStateReceiver =
                new DownloadStateReceiver();
		
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mDownloadStateReceiver,
                mStatusIntentFilter);
		
		
		ExpandableListView calendarsView = (ExpandableListView)findViewById(R.id.view_calendars);
		
		List<Country> countries = new ArrayList<Country>();
		for(Country c : Country.values()){
			if(c != Country.USER)
				countries.add(c);
		}
		CalendarsListAdapter adapter = new CalendarsListAdapter(this, countries);
		calendarsView.setAdapter(adapter);
		
		calendarsView.setOnChildClickListener(new OnCalendarClickListener(this));
		
		for (int i = 0; i < adapter.getGroupCount(); i++)
			calendarsView.expandGroup(i);
	}
	
	public void startDownloading(Country country){
		Intent serviceIntent = new Intent(this, UpdateServiceTest.class);
		serviceIntent.putExtra(UpdateServiceTest.TARGET_COUNTRY_ID, country.getId());
		
		startService(serviceIntent);
		notifier.showLoadingNotification(country);
	}
	
	
	
}
