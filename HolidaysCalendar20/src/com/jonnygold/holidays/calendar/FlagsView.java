package com.jonnygold.holidays.calendar;

import com.jonnygold.holidays.calendar.holiday.Country;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FlagsView extends LinearLayout {

	private static class FlagView extends ImageView{
		public FlagView(Context context, Country country) {
			super(context);
			setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			setImageResource(country.getDrawableId());
		}
	}
	
	public FlagsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void addFlag(Country country){
		LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		switch(getChildCount()){
		case 0 :
			layoutParams.setMargins(0, 0, 0, 0); break;
		case 4 :
			layoutParams.setMargins(0, -20, 0, 0);
			for(int i=1; i<getChildCount(); i++){
			getChildAt(i).setLayoutParams(layoutParams);
			}
			break;
		default :
			layoutParams.setMargins(0, -14, 0, 0); break;
		}
		
		addView(new FlagView(getContext(), country), layoutParams);
		
	}
	
}
