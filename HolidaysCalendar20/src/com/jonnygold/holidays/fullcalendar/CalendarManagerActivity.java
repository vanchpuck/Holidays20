package com.jonnygold.holidays.fullcalendar;

import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.Holiday;
import com.jonnygold.holidays.fullcalendar.web.UpdateService;
import com.jonnygold.holidays.fullcalendar.web.UpdateSeviceTest;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarManagerActivity extends ActionBarActivity {

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
		CalendarsListView calendarsView = (CalendarsListView)findViewById(R.id.view_calendars);
		calendarsView.setOnItemClickListener(new OnCalendarClickListener(this));
	}
	
	
	
}
