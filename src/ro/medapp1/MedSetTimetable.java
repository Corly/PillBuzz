package ro.medapp1;

import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MedSetTimetable extends Fragment{
	Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final View v=inflater.inflate(R.layout.program, container, false);
		
		Calendar cal = Calendar.getInstance();
		
		final TextView startTimeAll = (TextView)v.findViewById(R.id.starttime);
		startTimeAll.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + 
				cal.get(Calendar.MINUTE));
		
		final TextView startTimeDate = (TextView)v.findViewById(R.id.startdate);
		startTimeDate.setText("" + cal.getTime().getDate());
		
		final TextView endTimeDate = (TextView)v.findViewById(R.id.enddate);
		endTimeDate.setText("" + cal.getTime().getDate());
		
		Button changeStartTime = (Button)v.findViewById(R.id.changetime);
		changeStartTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new TimePickerFragment();
				((TimePickerFragment) newFragment).setTextView(startTimeAll);
			    newFragment.show(getChildFragmentManager(), "timePicker");
			}
		});
		
		Button changeStartDate = (Button)v.findViewById(R.id.changestartdate);
		changeStartDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				((DatePickerFragment) newFragment).setTextView(startTimeDate);
			    newFragment.show(getChildFragmentManager(), "datePicker");
			}
		});
		
		Button changeEndDate = (Button)v.findViewById(R.id.changeenddate);
		changeEndDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				((DatePickerFragment) newFragment).setTextView(endTimeDate);
			    newFragment.show(getChildFragmentManager(), "datePicker");
			}
		});
		
		Switch finalDateSwitch = (Switch)v.findViewById(R.id.switch_time_schedule);
		finalDateSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				LinearLayout ll = (LinearLayout)v.findViewById(R.id.linear_layout_end_date);
				if (!isChecked) {
					ll.setVisibility(View.GONE);
				}
				else {
					ll.setVisibility(View.VISIBLE);
				}
			}
		});
		
		final ViewGroup c = container;
		Button buttonSave = (Button)v.findViewById(R.id.button5);
		buttonSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
			}
		});
		
		return v;
	}

}
