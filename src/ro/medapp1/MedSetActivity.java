package ro.medapp1;

import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MedSetActivity extends FragmentActivity{
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setmedicine);

		context = this;
		Calendar cal = Calendar.getInstance();

		final TextView startTimeAll = (TextView)findViewById(R.id.starttime);
		startTimeAll.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + 
				cal.get(Calendar.MINUTE));

		final TextView startTimeDate = (TextView)findViewById(R.id.startdate);
		startTimeDate.setText("" + cal.getTime().getDate());

		final TextView endTimeDate = (TextView)findViewById(R.id.enddate);
		endTimeDate.setText("" + cal.getTime().getDate());

		Button changeStartTime = (Button)findViewById(R.id.changetime);
		changeStartTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TimePickerFragment newFragment = new TimePickerFragment();
				((TimePickerFragment) newFragment).setTextView(startTimeAll);
				newFragment.show(getSupportFragmentManager(), "timePicker");
			}
		});

		Button changeStartDate = (Button)findViewById(R.id.changestartdate);
		changeStartDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				((DatePickerFragment) newFragment).setTextView(startTimeDate);
				newFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});

		Button changeEndDate = (Button)findViewById(R.id.changeenddate);
		changeEndDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				((DatePickerFragment) newFragment).setTextView(endTimeDate);
				newFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});

		final Switch finalDateSwitch = (Switch)findViewById(R.id.switch_time_schedule);
		finalDateSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				LinearLayout ll = (LinearLayout)findViewById(R.id.linear_layout_end_date);
				if (!isChecked) {
					ll.setVisibility(View.GONE);
				}
				else {
					ll.setVisibility(View.VISIBLE);
				}
			}
		});

		final TextView intervalTextView = (TextView) findViewById(R.id.text_view_interval);
		Button button4h = (Button) findViewById(R.id.button_4h);
		Button button6h = (Button) findViewById(R.id.button_6h);
		Button button8h = (Button) findViewById(R.id.button_8h);
		Button button12h = (Button) findViewById(R.id.button_12h);

		button4h.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intervalTextView.setText("4h");
			}
		});

		button6h.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intervalTextView.setText("6h");
			}
		});

		button8h.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intervalTextView.setText("8h");
			}
		});

		button12h.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intervalTextView.setText("12h");
			}
		});
		
		Button buttonSave = (Button) findViewById(R.id.button_save);
		buttonSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Med med = new Med();
				
				EditText name = (EditText) findViewById(R.id.etName);
				med.setName(name.getText().toString());
				
				EditText description = (EditText) findViewById(R.id.edit_text_description);
				med.setDescription(description.getText().toString());
				
				EditText dosage = (EditText) findViewById(R.id.edit_text_dosage);
				try {
					med.setDosage(Integer.parseInt(dosage.getText().toString()));
				}
				catch (Exception e) {
					
				}
				
				EditText unit = (EditText) findViewById(R.id.edit_text_unit);
				med.setUnit(unit.getText().toString());
				
				EditText administration = (EditText) findViewById(R.id.edit_text_administration);
				med.setAdministrationMethod(administration.getText().toString());
				
				EditText previousDrugs = (EditText) findViewById(R.id.edit_text_previousdrug);
				med.setPreviousDrugs(previousDrugs.getText().toString());
				
				String time = startTimeAll.getText().toString();
				Log.d("HASSBFHSBFHSABFHASBFHSFSA", time);
				String[] vec = time.split(":");
				Log.d("HASSBFHSBFHSABFHASBFHSFSA", vec[0] + " " + vec[1]);
 				int hour = Integer.parseInt(vec[0]);
 				int minute = Integer.parseInt(vec[1]);
 				
 				med.setFirstDoseHour(hour);
 				med.setFirstDoseMinute(minute);
 				Log.d("HASSBFHSBFHSABFHASBFHSFSA", hour + " " + minute);
 				Log.d("HASSBFHSBFHSABFHASBFHSFSA", med.getFirstDoseHour() + " " + med.getFirstDoseMinute());
 				
 				time = startTimeDate.getText().toString();
 				String[] d = time.split("/");
 				med.setStartDateDay(Integer.parseInt(d[0]));
 				med.setStartDateMonth(Integer.parseInt(d[1]));
 				med.setStartDateYear(Integer.parseInt(d[2]));
 				
 				if (finalDateSwitch.isChecked()) {
 					time = endTimeDate.getText().toString();
 					String[] e = time.split("/");
 					med.setEndDateDay(Integer.parseInt(e[0]));
 					med.setEndDateMonth(Integer.parseInt(e[1]));
 					med.setEndDateYear(Integer.parseInt(e[2]));
 				}
 				else {
 					med.setEndDateDay(-1);
					med.setEndDateMonth(-1);
					med.setEndDateYear(-1);
 				}
 				
 				TextView interval = (TextView) findViewById(R.id.text_view_interval);
 				String s = interval.getText().toString();
 				try {
 					med.setInterval(Integer.parseInt(s.subSequence(0, s.length() - 2).toString()));
 				}
 				catch (Exception e) {
 					med.setInterval(-1);
 				}
 				
 				Log.d("HASSBFHSBFHSABFHASBFHSFSA", med.toString());
 				Log.d("Debug", med.getFirstDoseHour() + " " + med.getFirstDoseMinute());
 				
 				MedVector.getInstance().addMedToList(med, context);
 				finish();
			}
		});
	}
}
