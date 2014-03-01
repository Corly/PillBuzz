package ro.medapp1;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MedSetTimetable extends Fragment{

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
		View v=inflater.inflate(R.layout.program, container, false);
		
		Calendar cal = Calendar.getInstance();
		
		TextView startTimeAll = (TextView)v.findViewById(R.id.starttime);
		startTimeAll.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + 
				cal.get(Calendar.HOUR_OF_DAY));
		
		TextView startTimeDate = (TextView)v.findViewById(R.id.startdate);
		startTimeDate.setText(cal.getTime().getDate());
		
		TextView endTimeDate = (TextView)v.findViewById(R.id.enddate);
		endTimeDate.setText(cal.getTime().getDate());
		
		Button changeStartTime = (Button)v.findViewById(R.id.changetime);
		changeStartTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new TimePickerFragment();
			    newFragment.show(getChildFragmentManager(), "timePicker");
			}
		});
		
		return v;
	}

}
