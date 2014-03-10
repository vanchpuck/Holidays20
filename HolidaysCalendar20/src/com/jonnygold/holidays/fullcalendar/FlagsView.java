package com.jonnygold.holidays.fullcalendar;

import com.jonnygold.holidays.fullcalendar.holiday.Country;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
//import android.view.ViewGroup.LayoutParams;

public class FlagsView extends LinearLayout {

	private static final int COUNT_WITHOUT_OVERLAP = 3;
	
	private static class FlagView extends ImageView{
		public FlagView(Context context, Country country) {
			super(context);
			setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			setImageResource(country.getDrawableId());
//			setImageDrawable(getResources().getDrawable(country.getDrawableId()));
//			setPadding(0, -10, 0, 0);
//			setBackgroundColor(Color.parseColor("#00000000"));
		}
	}
	
	public FlagsView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//				5, 5);
//		layoutParams.topMargin = 40;
//		setLayoutParams(layoutParams);
		
//		@SuppressWarnings("unused")
//		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.getLayoutParams();
	}
	
	public void addFlag(Country country){
		LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int t = getChildCount();
		switch(getChildCount()){
		case 0 :
			layoutParams.setMargins(0, 0, 0, 0); break;
		default :
			layoutParams.setMargins(0, -14, 0, 0); break;
		}
		
		addView(new FlagView(getContext(), country), layoutParams);
//		int childCount = getChildCount();
//		int h = new FlagView(getContext(), country).getHeight();
//		if(childCount > COUNT_WITHOUT_OVERLAP){
//			for(int i=0; i<childCount; i++){
//				getChildAt(i).setPadding(0, h/12, 0, 0);
//			}
//		}
		
	}
	
}
