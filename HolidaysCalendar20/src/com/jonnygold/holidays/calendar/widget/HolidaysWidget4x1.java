package com.jonnygold.holidays.calendar.widget;

import android.content.SharedPreferences;
import com.jonnygold.holidays.calendar.R;

public final class HolidaysWidget4x1 extends HolidaysWidget{

	public static final int MAX_ROW_COUNT = 4;
	
	private static final String KEY_WIDGET_4X1_ROWS = "key_widget_4x1_rows";
	
	@Override
	public int getLayoutId() {
		return R.layout.widget_4x1;
	}

	@Override
	public int getOnClickView() {
		return R.id.view_widget_4x1;
	}

	@Override
	public int getRowCount(SharedPreferences pref) {
		String value = pref.getString(KEY_WIDGET_4X1_ROWS, String.valueOf(getMaxRowCount()));
		return Integer.valueOf(value);
	}
	
	@Override
	public int getMaxRowCount() {
		return MAX_ROW_COUNT;
	}
}
