package com.jonnygold.holidays.fullcalendar.web;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ResponseReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.w("Receiver", "I've received something");
		Log.w("Receiver", arg1.getType());
	}

	

}
