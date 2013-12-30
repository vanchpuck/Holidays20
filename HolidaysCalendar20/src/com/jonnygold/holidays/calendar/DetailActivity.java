package com.jonnygold.holidays.calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class DetailActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		// Read Holiday object from Parcel
		Holiday holiday = (Holiday) getIntent().getExtras().getParcelable(Holiday.class.getName());
//		
//		Log.w("TITLE", holiday.getTitle());
		
		ImageView iv = (ImageView)findViewById(R.id.view_icon_detail);
		iv.setImageDrawable(holiday.getDrawable());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
}
