package com.jonnygold.holidays.fullcalendar.web;

import android.app.ActivityManager.RunningServiceInfo;
import android.app.IntentService;
import android.content.Intent;

public class UpdateSeviceTest extends IntentService {

	public UpdateSeviceTest() {
		super("Update");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		RunningServiceInfo i;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
