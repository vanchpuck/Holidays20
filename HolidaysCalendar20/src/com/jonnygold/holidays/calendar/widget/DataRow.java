package com.jonnygold.holidays.calendar.widget;

import com.jonnygold.holidays.calendar.R;

public enum DataRow{
	ROW_1(R.id.date_1, R.id.text_1, new int[]{R.id.flag_11, R.id.flag_12, R.id.flag_13}),
	ROW_2(R.id.date_2, R.id.text_2, new int[]{R.id.flag_21, R.id.flag_22, R.id.flag_23}),
	ROW_3(R.id.date_3, R.id.text_3, new int[]{R.id.flag_31, R.id.flag_32, R.id.flag_33}),
	ROW_4(R.id.date_4, R.id.text_4, new int[]{R.id.flag_41, R.id.flag_42, R.id.flag_43}),
	ROW_5(R.id.date_5, R.id.text_5, new int[]{R.id.flag_51, R.id.flag_52, R.id.flag_53}),
	ROW_6(R.id.date_6, R.id.text_6, new int[]{R.id.flag_61, R.id.flag_62, R.id.flag_63}),
	ROW_7(R.id.date_7, R.id.text_7, new int[]{R.id.flag_71, R.id.flag_72, R.id.flag_73}),
	ROW_8(R.id.date_8, R.id.text_8, new int[]{R.id.flag_81, R.id.flag_82, R.id.flag_83}),
	ROW_9(R.id.date_9, R.id.text_9, new int[]{R.id.flag_91, R.id.flag_92, R.id.flag_93});
	
	private int dateView;
	private int titleView;
	private int[] flagViews;
	
	private DataRow(int dateView, int titleView, int[] flagViews){
		this.dateView = dateView;
		this.titleView = titleView;
		this.flagViews = flagViews;
	}
	
	public int getDateView(){
		return dateView;
	}
	
	public int getTitleView(){
		return titleView;
	}
	
	public int[] getFlagViews(){
		return flagViews;
	}
}
