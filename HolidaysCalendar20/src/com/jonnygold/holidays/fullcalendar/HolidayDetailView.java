package com.jonnygold.holidays.fullcalendar;

import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jonnygold.holidays.fullcalendar.holiday.Country;
import com.jonnygold.holidays.fullcalendar.holiday.Holiday;

public class HolidayDetailView extends LinearLayout{

	public HolidayDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setHoliday(Holiday holiday){
		((TextView) this.findViewById(R.id.view_txt_holiday_date)).setText(holiday.getDateString());
		((TextView) this.findViewById(R.id.view_txt_holiday_title)).setText(holiday.getTitle().toUpperCase(Locale.getDefault()));
		((ImageView) this.findViewById(R.id.view_img_holiday_picture)).setImageDrawable(holiday.getDrawable());
		((TextView) this.findViewById(R.id.view_txt_holiday_description)).setText(holiday.getDescription());
		
		int[] flagViews = new int[]{R.id.view_img_info_flag_1, R.id.view_img_info_flag_2, R.id.view_img_info_flag_3, R.id.view_img_info_flag_4};
		
		int i=0;
		ImageView flagView = null;
		for(Country country : holiday.getCountries()){
			flagView = (ImageView)this.findViewById(flagViews[i]);
			flagView.setImageResource(country.getDrawableId());
			i++;
		}
	}
	
}
