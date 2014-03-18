package com.jonnygold.holidays.fullcalendar;

import java.util.ArrayList;
import java.util.List;

import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryStateManager;
import com.jonnygold.holidays.fullcalendar.holiday.Holiday;
import com.jonnygold.holidays.fullcalendar.web.UpdateService;
import com.jonnygold.holidays.fullcalendar.web.UpdateSeviceTest;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarManagerActivity extends ActionBarActivity {

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
	
	private class CalendarLoader{
//		public final AlertDialog loadingDialog;
		public final AlertDialog inviteDialog;
		public final Country country;
		private final Context context;
		public final Intent serviceIntent;
		
		CalendarLoader(Context context, Country country){
			this.country = country;
			this.context = context;
			serviceIntent = new Intent(context, UpdateSeviceTest.class);
			serviceIntent.putExtra("countryId", country.getId());
			
			inviteDialog = new AlertDialog.Builder(context)
		        .setIcon(country.getDrawableId())
		    	.setTitle(country.getName())
		    	//.setView(getLayoutInflater().inflate(R.layout.view_invite_dialog, null) )
		    	.setMessage("Загрузить календарь(1.6 Mb)?")
//		    	.setMessage("Необходимо загрузить календарь. Это займёт не более минуты.")
		    	.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				})
		    	.setPositiveButton("Загрузить", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {						
						startDownloading();
					}
				})
		    	.create();
		}
		
		AlertDialog createErrorDialog(String message){
			return new AlertDialog.Builder(context)
						.setTitle("Ошибка установки.")
						.setPositiveButton("Ok", null)
						.setMessage(message)
						.create();
		}
		
		public void startDownloading(){
			
			startService(serviceIntent);
			
			
			Intent resultIntent = new Intent(context, UpdateSeviceTest.class);
			
			PendingIntent resultPendingIntent =
				    PendingIntent.getActivity(
				    context,
				    0,
				    resultIntent,
				    PendingIntent.FLAG_UPDATE_CURRENT
				);
			
			NotificationCompat.Builder mBuilder =
				    new NotificationCompat.Builder(context)
				    .setSmallIcon(R.drawable.ic_launcher)
				    .setProgress(0, 0, true)
				    .setContentTitle("My notification")
				    .setContentText("Hello World!")
				    .setContentIntent(resultPendingIntent);
			
			int mNotificationId = 001;
			// Gets an instance of the NotificationManager service
			NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			// Builds the notification and issues it.
			mNotifyMgr.notify(mNotificationId, mBuilder.build());
				    
			// Because clicking the notification opens a new ("special") activity, there's
			// no need to create an artificial back stack.
		}
		
	}
	
	public class OnCalendarClickListener implements OnItemClickListener{		
		private Context context;
		
		public OnCalendarClickListener (Context context){
			this.context = context;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int idx, long id) {
			Country country = (Country) parent.getAdapter().getItem(idx);
			new CalendarLoader(context, country).startDownloading();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_manager);
		
		ExpandableListView calendarsView = (ExpandableListView)findViewById(R.id.view_calendars);
		List<Country> countries = new ArrayList<Country>();
		for(Country c : Country.values()){
			if(c != Country.USER)
				countries.add(c);
		}
		CalendarsListAdapter adapter = new CalendarsListAdapter(this, countries);
		calendarsView.setAdapter(adapter);
		
		calendarsView.setOnItemClickListener(new OnCalendarClickListener(this));
		
		for (int i = 0; i < adapter.getGroupCount(); i++)
			calendarsView.expandGroup(i);
	}
	
	
	
}
