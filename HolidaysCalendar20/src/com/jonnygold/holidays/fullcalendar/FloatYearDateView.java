package com.jonnygold.holidays.fullcalendar;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jonnygold.holidays.fullcalendar.holiday.HolidayDate;

public class FloatYearDateView extends DateChooser {

	@Override
	public boolean checkData() {
		Integer day = Integer.valueOf(dayNumber.getText().toString());
		if(day == null || day > 365 || day < 1){
			Toast.makeText(getContext(), "Введите число от 1 до 365", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	public HolidayDate getDate() {
		HolidayDate.Builder builder = new HolidayDate.Builder();
		builder.setYearDay(Integer.valueOf(dayNumber.getText().toString())-1);
		
		return builder.create();
	}

	
	private TextView exampleText;
	private EditText dayNumber;
	
	public FloatYearDateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public FloatYearDateView(Context context) {
		super(context);
		init();
	}

	private void init(){
		exampleText = new TextView(getContext());
		exampleText.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		exampleText.setTextSize(10);
		exampleText.setText("Например, 1-й день года");
		
		dayNumber = new EditText(getContext());
//		dayNumber = View.inflate(this, R.layout.et_input_number, null);
		dayNumber.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		dayNumber.setSingleLine();
		dayNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
		dayNumber.setHint("День года");
		dayNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);
		
		addView(exampleText);
		addView(dayNumber);
	}
	
}
