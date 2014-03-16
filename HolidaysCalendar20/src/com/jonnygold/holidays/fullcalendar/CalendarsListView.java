package com.jonnygold.holidays.fullcalendar;

import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.CountryManager;
import com.jonnygold.holidays.fullcalendar.holiday.CountryStateManager;
import com.jonnygold.holidays.fullcalendar.holiday.CountryStateManager.State;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CalendarsListView extends ListView {
	
	private static class CalendarsAdapter extends ArrayAdapter<Country>{
		
		private CountryStateManager stateManager;
		
		private LayoutInflater inflater;
		
		private Context context;
		
		private Country[] countries;
		
		public CalendarsAdapter(Context context) {
			super(context, R.layout.item, Country.values());
			this.countries = Country.values();
			this.inflater = LayoutInflater.from(context);
			this.context = context;
			this.stateManager = new CountryStateManager(context);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View listItem = inflater.inflate(R.layout.view_calend_item, null);
			
			TextView titeView = (TextView)listItem.findViewById(R.id.view_calend_title);
			titeView.setText(countries[position].getName());
			
			Drawable flag = null;
			switch (stateManager.getState(countries[position])) {
			case NOT_INSTALLED:
				flag = context.getResources().getDrawable(R.drawable.ic_launcher);
				break;
			default:
				flag = context.getResources().getDrawable(countries[position].getDrawableId());
				break;
			}
			titeView.setCompoundDrawablesWithIntrinsicBounds(null, null, flag, null);
						
			return listItem;
		}
	}
	
	
	
	public CalendarsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAdapter(new CalendarsAdapter(context));
	}

}
