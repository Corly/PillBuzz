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
=======
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils; 
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;



import java.util.List;
/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class MedSetActivity extends PreferenceActivity {
	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */
	private static final boolean ALWAYS_SIMPLE_PREFS = false;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		setupSimplePreferencesScreen();
	}

	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}

		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Add 'general' preferences.
		addPreferencesFromResource(R.layout.preferences);
		
		//this.addPreferencesFromIntent(new DatepickerPreference(this,null).getIntent());
		//addPreferencesFromResource(R.layout.datepicker_dialog);
		// Bind the summaries of EditText/List/Dialog/Ringtone preferences to
		// their values. When their values change, their summaries are updated
		// to reflect the new value, per the Android Design guidelines.
		bindPreferenceSummaryToValue(findPreference("name"));
		bindPreferenceSummaryToValue(findPreference("description"));
		bindPreferenceSummaryToValue(findPreference("unit"));
		bindPreferenceSummaryToValue(findPreference("administration"));
		bindPreferenceSummaryToValue(findPreference("dosage"));
		bindPreferenceSummaryToValue(findPreference("interval"));
		bindPreferenceSummaryToValue(findPreference("startdate"));
		
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	/**
	 * Determines whether the simplified settings UI should be shown. This is
	 * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
	 * doesn't have newer APIs like {@link PreferenceFragment}, or the device
	 * doesn't have an extra-large screen. In these cases, a single-pane
	 * "simplified" settings UI should be shown.
	 */
	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
				|| !isXLargeTablet(context);
	}

	/** {@inheritDoc} */
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			//loadHeadersFromResource(R.xml.pref_headers, target);
		}
	}

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();

			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			} else if (preference instanceof RingtonePreference) {
				// For ringtone preferences, look up the correct display value
				// using RingtoneManager.
				if (TextUtils.isEmpty(stringValue)) {
					// Empty values correspond to 'silent' (no ringtone).
					//preference.setSummary(R.string.pref_ringtone_silent);

				} else {
					Ringtone ringtone = RingtoneManager.getRingtone(
							preference.getContext(), Uri.parse(stringValue));

					if (ringtone == null) {
						// Clear the summary if there was a lookup error.
						preference.setSummary(null);
					} else {
						// Set the summary to reflect the new ringtone display
						// name.
						String name = ringtone
								.getTitle(preference.getContext());
						preference.setSummary(name);
					}
				}

			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);
			}
			return true;
		}
	};

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}

	/**
	 * This fragment shows general preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.layout.preferences);

			// Bind the summaries of EditText/List/Dialog/Ringtone preferences
			// to their values. When their values change, their summaries are
			// updated to reflect the new value, per the Android Design
			// guidelines.
//			bindPreferenceSummaryToValue(findPreference("example_text"));
//			bindPreferenceSummaryToValue(findPreference("example_list"));
		}
	}

	/**
	 * This fragment shows notification preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class NotificationPreferenceFragment extends
			PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		
		}
	}

}
