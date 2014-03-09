package com.jonnygold.holidays.fullcalendar.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import com.jonnygold.holidays.fullcalendar.HolidaysDataSource;
import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayRaw;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class UpdateService extends Service {
	
	private UpdateState result = UpdateState.FAULT;
	
	private List<HolidayRaw> holidays;
	
	private HolidaysDataSource holidaysBase;
		
	private class JobThread extends Thread{
		private Country country;
		public JobThread(Country country){
			this.country = country;
		}
		
		@Override
		public void run() {
			try {
				holidays = WebService.getInstance().getHolidays(country);
				
				try{
					holidaysBase.openForReading();
					holidaysBase.beginTransaction();
					if(!Thread.interrupted())
						holidaysBase.saveCountry(country);
					else
						return;
					
					for(HolidayRaw h : holidays){
						if(!Thread.interrupted())
							holidaysBase.saveHoliday(h);
						else
							return;
					}
					if(!Thread.interrupted()){
						holidaysBase.setTransactionSuccessful();
					}
				}
				finally{
					holidaysBase.endTransaction();
					holidaysBase.close();
				}
				if(!Thread.interrupted()){
					result = UpdateState.SUCCESS;
				}				
			} 
			catch (HttpResponseException e) {
				result = UpdateState.BAD_RESPONSE;
			}
			catch (IOException e) {
				result = UpdateState.NOT_CONNECTED;
			}
			catch (XmlPullParserException e) {
				result = UpdateState.OTHER;
			}			
			finally{
				stopSelf();
			}
		}
		
	}
	
	public enum UpdateState implements Serializable {
		FAULT (-1, "Не установлено"),
		SUCCESS (1, "Установлено"),
		BAD_RESPONSE (2, "Ошибка связи с серветом. Повторите попытку позже."),
		NOT_CONNECTED (3, "Нет связи с сервером. Проверьте интернет-соединение."),
		OTHER (4 , "Не удалось установить календарь. Повторите попытку позже.");
		
		public final int code;
		public String message;
		
		private UpdateState(int code, String message) {
			this.code = code;
			this.message = message;
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
		holidaysBase = HolidaysDataSource.newInstance(this);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {		
		job = new JobThread(CountryManager.getInstance().getCountry(intent.getExtras().getInt("countryId")));
		job.start();
		return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		if(job != null){
			job.interrupt();
		}
		Intent intent = new Intent("Broadcast");
		intent.putExtra("Result", result);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

}
