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
import android.database.sqlite.SQLiteException;
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
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(!Thread.interrupted()){
						holidaysBase.setTransactionSuccessful();
						result = UpdateState.SUCCESS;
					}
				}
				finally{
					holidaysBase.endTransaction();
					holidaysBase.close();
				}
//				if(!Thread.interrupted()){
//					result = UpdateState.SUCCESS;
//				}				
			} 
			catch (HttpResponseException e) {
				result = UpdateState.BAD_RESPONSE;
			}
			catch (IOException e) {
				result = UpdateState.NOT_CONNECTED;
			}
			catch (XmlPullParserException e) {
				result = UpdateState.FAULT;
			}	
			catch (SQLiteException e) {
				result = UpdateState.FAULT;
			}
			finally{
				stopSelf();
			}
		}
		
	}
	
	public enum UpdateState {
		SUCCESS (0, "Загрузка завершена.", "Календарь успешно установлен."),
		FAULT (1, "Ошибка установки.", "Не удалось установить календарь. Повторите попытку позже."),
		BAD_RESPONSE (2, "Ошибка установки.", "Ошибка связи с серветом. Повторите попытку позже."),
		NOT_CONNECTED (3, "Ошибка установки.", "Нет связи с сервером. Проверьте интернет-соединение.");
		
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
