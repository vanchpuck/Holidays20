package com.jonnygold.holidays.fullcalendar.web;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.provider.SyncStateContract.Constants;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class UpdateService extends Service {

//	public UpdateService() {
//		super("UpdateService");
//		// TODO Auto-generated constructor stub
//	}

	private boolean flag = true;;
	
	private int result = -1;
	
	private class JobThread extends Thread{
		
		@Override
		public void run() {
			try {
				sleep(4000);
				result = 1;
				stopSelf();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
//	@Override
//	protected void onHandleIntent(Intent intent) {
//		JobThread job = new JobThread();
//		job.start();
//	}
	
//	@Override
//	public void onDestroy() {
//		Log.w("Service", "Destroy me!!!");
//		flag = false;
//	}

	JobThread job;
	
	@Override
	public void onCreate() {
		job = new JobThread();
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.w("Service", "I've got start command");
		job.start();
		return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		job.interrupt();
		Intent intent = new Intent("Broadcast");
		intent.putExtra("Result", result);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	

}
