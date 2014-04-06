package com.jonnygold.holidays.fullcalendar.web;

import java.io.IOException;
import java.util.List;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import com.jonnygold.holidays.fullcalendar.HolidaysDataSource;
import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayRaw;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class UpdateService extends Service {

	public static final String BROADCAST_ACTION = "com.jonnygold.holidays.fullcalendar.web.BROADCAST";
	
	public static final String UPDATE_STATUS = "com.jonnygold.holidays.fullcalendar.web.STATUS";
	
	public static final String TARGET_COUNTRY_ID = "com.jonnygold.holidays.fullcalendar.web.TARGET_COUNTRY";
		
			
	private HolidaysDataSource holidaysBase;
		
	private class JobThread extends Thread{
		private Country country;
		private List<HolidayRaw> holidays;
		
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
					else{
						return;
					}
					
					for(HolidayRaw h : holidays){
						if(!Thread.interrupted())
							holidaysBase.saveHoliday(h);
						else{
							return;
						}
					}
					if(!Thread.interrupted()){
						holidaysBase.setTransactionSuccessful();
						report(country, UpdateState.SUCCESS);
//						result = UpdateState.SUCCESS;
					}
					else{
						return;
					}
				}
				catch (SQLiteException e) {
					report(country, UpdateState.FAULT);
//					result = UpdateState.FAULT;
				}
				finally{
					holidaysBase.endTransaction();
					holidaysBase.close();
					stopSelf();
				}		
			} 
			catch (HttpResponseException e) {
				report(country, UpdateState.BAD_RESPONSE);
//				result = UpdateState.BAD_RESPONSE;
			}
			catch (IOException e) {
				report(country, UpdateState.NOT_CONNECTED);
//				result = UpdateState.NOT_CONNECTED;
			}
			catch (XmlPullParserException e) {
				report(country, UpdateState.FAULT);
//				result = UpdateState.FAULT;
			}
		}
		
	}
	
	public enum UpdateState {
		SUCCESS (0, "Загрузка завершена.", "Календарь успешно установлен."),
		FAULT (1, "Ошибка установки.", "Не удалось установить календарь. Повторите попытку позже."),
		BAD_RESPONSE (2, "Ошибка установки.", "Ошибка связи с серветом. Повторите попытку позже."),
		NOT_CONNECTED (3, "Ошибка установки.", "Нет связи с сервером. Проверьте интернет-соединение."),
		;//CANCELLED (4, "Установка отменена", "Установка отменена");
		
		public final int code;
		public String message;
		public String title;
		
		private UpdateState(int code, String title, String message) {
			this.code = code;
			this.title = title;
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

	private JobThread job;
	
	@Override
	public void onCreate() {
		holidaysBase = HolidaysDataSource.newInstance(this);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {		
		job = new JobThread(CountryManager.getInstance().getCountry(intent.getExtras().getInt(TARGET_COUNTRY_ID)));
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
//		Intent intent = new Intent("Broadcast");
//		intent.putExtra("Result", result);
//		
//		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
	
	private void report(Country country, UpdateState state){
		Intent localIntent = new Intent(BROADCAST_ACTION)
	            .putExtra(UPDATE_STATUS, state)
		 		.putExtra(TARGET_COUNTRY_ID, country.getId());
		LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
	}

}
