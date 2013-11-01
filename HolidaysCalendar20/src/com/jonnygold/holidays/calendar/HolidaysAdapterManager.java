package com.jonnygold.holidays.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class HolidaysAdapterManager implements AdapterManager<Holiday> {

	public static class HolidayBinder implements SimpleAdapter.ViewBinder{

		@Override
		public boolean setViewValue(View view, Object data, String textRepresentation) {
			switch(view.getId()){
			case VIEW_PICTURE : ((ImageView)view).setImageDrawable((Drawable)data);return true;
			}
			return false;
		}
		
	}
	
	private static final int TEMP_MAP_CAPACITY = 20;
	
	private static HolidaysAdapterManager MANAGER;
	
	
	protected static final String ATTR_TITLE 		= "title";
	protected static final String ATTR_PICTURE 		= "picture";
	protected static final String ATTR_WORLD		= CountryWorld.class.getSimpleName();
	protected static final String ATTR_RUSSIA		= CountryRussia.class.getSimpleName();
	protected static final String ATTR_BELORUSSIA	= CountryBelorussia.class.getSimpleName();
	protected static final String ATTR_UKRANE		= CountryUkrane.class.getSimpleName();
	
	protected static final int VIEW_LIST_ITEM	= R.layout.item;
	
	protected static final int VIEW_TITLE 		= R.id.view_title;
	protected static final int VIEW_PICTURE 	= R.id.view_picture;
	protected static final int VIEW_WORLD 		= R.id.view_flag_1;
	protected static final int VIEW_RUSSIA 		= R.id.view_flag_2;
	protected static final int VIEW_BELORUSSIA 	= R.id.view_flag_3;
	protected static final int VIEW_UKRANE 		= R.id.view_flag_4;

	
	private Context context;
	
	
	private HolidaysAdapterManager(Context context){
		this.context = context;
	}
	
	public static HolidaysAdapterManager getInstance(Context context){
		if(MANAGER == null){
			return new HolidaysAdapterManager(context);
		}
		return MANAGER;
	}

	@Override
	public SimpleAdapter getAdapter(List<Holiday> items) {
				
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(items.size());
		Map<String, Object> temp = null;
		for(Holiday h : items){
			temp = new HashMap<String, Object>(TEMP_MAP_CAPACITY);
			temp.put(ATTR_TITLE, h.getTitle());
			temp.put(ATTR_PICTURE, h.getDrawable());
			
//			temp.put(ATTR_WORLD, R.drawable.earth_s_1);
//			temp.put(ATTR_RUSSIA, R.drawable.russia_circle_s_1);
//			temp.put(ATTR_BELORUSSIA, R.drawable.bel_circle_s_1);
//			temp.put(ATTR_UKRANE, R.drawable.ukrane_circle_s_1);
			
			for(Country c : h.getCountries()){
//				Log.w("MANAGER", c.getClass().getSimpleName());
//				Log.w("MANAGER", c.getDrawableId()+"");
				temp.put(c.getClass().getSimpleName(), c.getDrawableId());
			}
			
			data.add(temp);

		}
		
		String[] from = new String[]{ATTR_TITLE, ATTR_PICTURE, ATTR_WORLD, ATTR_RUSSIA, ATTR_BELORUSSIA, ATTR_UKRANE};
		int[] to = new int[]{VIEW_TITLE, VIEW_PICTURE, VIEW_WORLD, VIEW_RUSSIA, VIEW_BELORUSSIA, VIEW_UKRANE};
		
		return new SimpleAdapter(context, data, VIEW_LIST_ITEM, from, to);
	}
	
	@Override
	public HolidayBinder getBinder(){
		return new HolidayBinder();
	}


}
