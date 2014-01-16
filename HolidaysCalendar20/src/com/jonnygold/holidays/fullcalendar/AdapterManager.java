package com.jonnygold.holidays.fullcalendar;

import java.util.List;

import android.widget.SimpleAdapter;

public interface AdapterManager<T> {

	public SimpleAdapter getAdapter(List<T> items);
	
	public SimpleAdapter.ViewBinder getBinder();
	
}
