package com.jonnygold.holidays.calendar;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleAdapter;

public class HolidaysAdapter extends SimpleAdapter{

	protected static final String ATTR_TITLE 		= "title";
	protected static final String ATTR_DATE 		= "date";
	protected static final String ATTR_DESCRIPTION 	= "description";
	protected static final String ATTR_PICTURE 		= "picture";
	protected static final String ATTR_WORLD		= CountryWorld.class.getName();
	protected static final String ATTR_RUSSIA		= CountryRussia.class.getName();
	protected static final String ATTR_BELORUSSIA	= CountryBelorussia.class.getName();
	protected static final String ATTR_UKRANE		= CountryUkrane.class.getName();
	
	/* ПОМЕНЯТЬ НА НОРМАЛЬНЫЙ */
	protected static final int RESOURCE	= R.layout.item;
	
	protected static final int VIEW_TITLE = R.id.view_title;
	protected static final int VIEW_PICTURE = R.id.view_picture;
	
	protected static String[] from = new String[]{ATTR_TITLE, ATTR_PICTURE};
	
	public HolidaysAdapter(
			Context context,	
			List<Holiday> data,
			int resource, 
			String[] from, 
			int[] to) {
		
		super(context, null, resource, from, to); /* REMOVE NULL */
				
	}
	
}
