package com.jonnygold.holidays.calendar;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HolidaysAdapter extends ArrayAdapter<Holiday>{

	protected List<Holiday> holidays;
	
	protected LayoutInflater inflater;
	
	public HolidaysAdapter(Context context, List<Holiday> holidays) {
		super(context, R.layout.item, holidays);
		
		inflater = LayoutInflater.from(context);
		
		this.holidays = holidays;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		return super.getView(position, convertView, parent);
		
		Holiday holiday = holidays.get(position);
		
		View listItem = inflater.inflate(R.layout.item, null);
		
		TextView titeView = (TextView)listItem.findViewById(R.id.view_title);
		titeView.setText(holiday.getTitle().toUpperCase(Locale.getDefault()));
		
		ImageView pictureView = (ImageView)listItem.findViewById(R.id.view_picture);
		pictureView.setImageDrawable(holiday.getDrawable());
				
		int[] flagViews = new int[]{R.id.view_flag_1, R.id.view_flag_2, R.id.view_flag_3, R.id.view_flag_4};
		
		int i=0;
		ImageView flagView = null;
		for(Iterator<Country> iterator=holiday.getCountries().iterator(); iterator.hasNext(); ){
			flagView = (ImageView)listItem.findViewById(flagViews[i]);
			flagView.setImageResource(iterator.next().getDrawableId());
			i++;
		}
		
		return listItem;
	}
	
	@Override
	public long getItemId(int position) {
		return holidays.get(position).getId();
	}
	
	@Override
	public Holiday getItem(int position) {
		return super.getItem(position);
	}
	
}
