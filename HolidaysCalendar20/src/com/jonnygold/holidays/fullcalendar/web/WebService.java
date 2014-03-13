package com.jonnygold.holidays.fullcalendar.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Base64;

import com.jonnygold.holidays.fullcalendar.HolidaysBaseHelper;
import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.holiday.DayOrder;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayDate;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayPack;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayRaw;
import com.jonnygold.holidays.fullcalendar.holiday.IsPicture;
import com.jonnygold.holidays.fullcalendar.holiday.HolidayDate.Builder;
import com.jonnygold.holidays.fullcalendar.holiday.Picture;

public class WebService {
	
	private static String NAMESPACE = "http://updatetest.holidays.jonnygold.com";
    
	private static String URL = "http://appserver.stratoslive.wso2.com/services/t/jonnygold.com/HolidaysService_v1?wsdl";
    
	private static String METHOD_NAME = "getHolidays";
	
	private static String SOAP_ACTION = NAMESPACE+"/"+METHOD_NAME;
	
	
	private static WebService instance;
	
	private WebService(){}
	
	public static WebService getInstance(){
		if(instance == null){
			instance = new WebService();
		}
		return instance;
	}
	
	public List<HolidayRaw> getHolidays(Country country) throws HttpResponseException, IOException, XmlPullParserException {
		String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        // Property which holds input parameters
        PropertyInfo sayHelloPI = new PropertyInfo();
        // Set Name
        sayHelloPI.setName("countryId");
        // Set Value
        sayHelloPI.setValue(country.getId());
        // Set dataType
        sayHelloPI.setType(int.class);
        // Add the property to request object
        request.addProperty(sayHelloPI);
//        request.addPropertyIfValue("name", "name");
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        List<HeaderProperty> headers=new ArrayList<HeaderProperty>();
        HeaderProperty headerProperty=new HeaderProperty("Accept-Encoding", "none");
        headers.add(headerProperty);
                
        // Invoke web service
        androidHttpTransport.call(SOAP_ACTION, envelope, headers);
        
        // Get the response
        @SuppressWarnings("unchecked")
		Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
        
        List<HolidayRaw> holidays = new ArrayList<HolidayRaw>();
        
        Set<Country> countrySet = new HashSet<Country>();
        countrySet.add(country);
        for(SoapObject soapObject : response){
        	holidays.add(createHoliday(soapObject, countrySet));
        }
        return holidays;      
	}
	
	private HolidayRaw createHoliday(SoapObject soapHoliday, Set<Country> countrySet){
		
		SoapObject soapDate = (SoapObject) soapHoliday.getProperty("date");
		HolidayDate.Builder builder = new HolidayDate.Builder();
		
		Object actualDay = soapDate.getProperty("actualDay");
		if(actualDay != null) 
			builder.setActualDay(Integer.valueOf(actualDay.toString()));
		
		Object actualMonth = soapDate.getPrimitiveProperty("actualMonth");
		if(actualMonth != null) 
			builder.setActualMonth(Integer.valueOf(actualMonth.toString()));
		
		Object floatMonth = soapDate.getPrimitiveProperty("floatMonth");
		if(floatMonth != null) 
			builder.setFloaMonth(Integer.valueOf(floatMonth.toString()));
		
		Object weekDay = soapDate.getPrimitiveProperty("weekDay");
		if(weekDay != null) 
			builder.setWeekDay(Integer.valueOf(weekDay.toString()));
		
		/* DOTO ��������� ������ Offset!!!! */
		Object offset = soapDate.getPrimitiveProperty("offset");
		if(offset != null) 
			builder.setOffset(DayOrder.getDayOrder(Integer.valueOf(offset.toString())) );
		
		Object yearDay = soapDate.getPrimitiveProperty("yearDay");
		if(yearDay != null) 
			builder.setYearDay(Integer.valueOf(yearDay.toString()));
		
		
		HolidayDate date = builder.create();
		
		int idHoliday = Integer.valueOf(soapHoliday.getProperty("id").toString());
		
		String desc = soapHoliday.getProperty("description").toString();
		
		SoapObject soapImage = (SoapObject)soapHoliday.getProperty("picture");
		IsPicture picture = new Picture(
				Integer.valueOf(soapImage.getProperty("id").toString()), 
				soapImage.getProperty("title").toString(), 
				Base64.decode(soapImage.getProperty("data").toString(), Base64.DEFAULT)
				);
		
		Integer type = Integer.valueOf(soapHoliday.getProperty("type").toString());
		
		String dateStr = soapHoliday.getProperty("dateString").toString();
		
		String title = soapHoliday.getProperty("title").toString();
		
		HolidayRaw raw = new HolidayRaw(
				idHoliday, 
				title, 
				dateStr, 
				type, 
				picture, 
				desc, 
				countrySet, 
				date
				);
		
		return raw;
	}

}
