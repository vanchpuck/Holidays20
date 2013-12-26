package com.jonnygold.holidays.calendar.widget;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jonnygold.holidays.calendar.R;
import com.jonnygold.holidays.calendar.SettingsActivity;

public final class HolidaysWidget4x1 extends HolidaysWidget{

	public static final int MAX_ROW_COUNT = 4;
	
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
		String value = pref.getString(SettingsActivity.KEY_WIDGET_4X1_ROWS, String.valueOf(getMaxRowCount()));
		return Integer.valueOf(value);
	}
	
	@Override
	public int getMaxRowCount() {
		return MAX_ROW_COUNT;
	}
}
