//package com.jonnygold.holidays.fullcalendar.web;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.List;
//
//import org.ksoap2.transport.HttpResponseException;
//import org.xmlpull.v1.XmlPullParserException;
//
//import com.jonnygold.holidays.fullcalendar.HolidaysDataSource;
//import com.jonnygold.holidays.fullcalendar.holiday.Country;
//import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
//import com.jonnygold.holidays.fullcalendar.holiday.HolidayRaw;
//
//import android.app.ActivityManager.RunningServiceInfo;
//import android.app.IntentService;
//import android.content.Intent;
//import android.database.sqlite.SQLiteException;
//import android.support.v4.content.LocalBroadcastManager;
//
//public class UpdateServiceTest extends IntentService {
//
//	public static final String BROADCAST_ACTION = "com.jonnygold.holidays.fullcalendar.web.BROADCAST";
//	
//	public static final String UPDATE_STATUS = "com.jonnygold.holidays.fullcalendar.web.STATUS";
//	
//	public static final String TARGET_COUNTRY_ID = "com.jonnygold.holidays.fullcalendar.web.TARGET_COUNTRY";
//	
//	
//	public enum UpdateState {
//		SUCCESS (0, "Загрузка завершена.", "Календарь успешно установлен."),
//		FAULT (1, "Ошибка установки.", "Не удалось установить календарь. Повторите попытку позже."),
//		BAD_RESPONSE (2, "Ошибка установки.", "Ошибка связи с серветом. Повторите попытку позже."),
//		NOT_CONNECTED (3, "Ошибка установки.", "Нет связи с сервером. Проверьте интернет-соединение.");
//		
//		public final int code;
//		public String message;
//		public String title;
//		
//		private UpdateState(int code, String title, String message) {
//			this.code = code;
//			this.title = title;
//			this.message = message;
//		}
//		
//	}
//	
//	public UpdateServiceTest() {
//		super("Update");
//	}
//
//	@Override
//	protected void onHandleIntent(Intent intent) {
//		HolidaysDataSource holidaysBase = HolidaysDataSource.newInstance(this);
//		
//		// Получаем страну по id
//		Country country = 
//				CountryManager.getInstance().getCountry(intent.getExtras().getInt(TARGET_COUNTRY_ID));
//		
//		// Установка календаря
//		try {
//			holidaysBase.openForWriting();
//			
//			
//			// Если календарь уже установлен - выходим
//			if(holidaysBase.isExists(country)){
//				report(country, UpdateState.SUCCESS);
//				return;
//			}
//			
////			holidaysBase.beginTransaction();
//			
//			// Грузим данные с сервера
//			List<HolidayRaw> holidays = null;
//			try {
//				holidays = WebService.getInstance().getHolidays(country);
//			} catch (HttpResponseException e) {
//				report(country, UpdateState.BAD_RESPONSE);
//				return;
//			} catch (IOException e) {
//				report(country, UpdateState.NOT_CONNECTED);
//				return;
//			} catch (XmlPullParserException e) {
//				report(country, UpdateState.FAULT);
//				return;
//			} catch (Exception e) {
//				report(country, UpdateState.FAULT);
//				return;
//			}
//			
//			// Сохраняем в базу страну
//			holidaysBase.saveCountry(country);
//			
//			// Сохраняем праздники
//			for(HolidayRaw h : holidays){
//				holidaysBase.saveHoliday(h);
//			}
//			
////			holidaysBase.setTransactionSuccessful();
//			report(country, UpdateState.SUCCESS);
//			
//		} catch (SQLiteException e) {
//			holidaysBase.deleteCountry(country);
//			report(country, UpdateState.FAULT);
//			return;
//		} catch (Exception e) {
//			holidaysBase.deleteCountry(country);
//			report(country, UpdateState.FAULT);
//			return;
//		}
//		finally{
////			holidaysBase.endTransaction();
//			holidaysBase.close();
//		}
////		report(country, UpdateState.SUCCESS);
//	}
//	
//	private void report(Country country, UpdateState state){
//		Intent localIntent = new Intent(BROADCAST_ACTION)
//	            .putExtra(UPDATE_STATUS, state)
//		 		.putExtra(TARGET_COUNTRY_ID, country.getId());
//		LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
//	}
//
//}
