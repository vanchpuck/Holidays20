package com.jonnygold.holidays.fullcalendar.widget;

import android.content.SharedPreferences;

import com.jonnygold.holidays.fullcalendar.R;

public final class HolidaysWidget4x2 extends HolidaysWidget{
	
	public static final int MAX_ROW_COUNT = 9;
	
	private static final String KEY_WIDGET_4X2_ROWS = "key_widget_4x2_rows";
	
	@Override
	public int getLayoutId() {
		return R.layout.widget_4x2;
	}

	@Override
	public int getOnClickView() {
		return R.id.view_widget_4x2;
	}

	@Override
	public int getRowCount(SharedPreferences pref) {
		String value = pref.getString(KEY_WIDGET_4X2_ROWS, String.valueOf(getMaxRowCount()));
		return Integer.valueOf(value);
	}
	@Override
	public int getMaxRowCount() {
		return MAX_ROW_COUNT;
	}
}
