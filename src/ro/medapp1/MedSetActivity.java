package ro.medapp1;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ro.pillbuzz.data.MedDb;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
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
	private final long HOUR = 3600 * 1000; //hours in miliseconds

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
	@SuppressWarnings("deprecation")
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}

		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Add 'general' preferences.
		addPreferencesFromResource(R.xml.preferences);
		setContentView(R.layout.settings);
		
		//this.addPreferencesFromIntent(new DatepickerPreference(this,null).getIntent());
		//addPreferencesFromResource(R.layout.datepicker_dialog);
		// Bind the summaries of EditText/List/Dialog/Ringtone preferences to
		// their values. When their values change, their summaries are updated
		// to reflect the new value, per the Android Design guidelines.
		bindPreferenceSummaryToStringValue(findPreference("name"));
		bindPreferenceSummaryToStringValue(findPreference("description"));
		bindPreferenceSummaryToStringValue(findPreference("unit"));
		bindPreferenceSummaryToStringValue(findPreference("administration"));
		bindPreferenceSummaryToStringValue(findPreference("interval"));
		bindPreferenceSummaryToStringValue(findPreference("startdate"));
		bindPreferenceSummaryToStringValue(findPreference("stopdate"));
		bindPreferenceSummaryToStringValue(findPreference("starttime"));
		bindPreferenceSummaryToIntValue(findPreference("dosage"));
		
		//Save the medicine
		Button save = (Button) findViewById(R.id.button_save_med);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences details = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				//get the details about the medicine
				String name = details.getString("name", "No Name");
				if(name.equals(R.string.default_med_name)) 
				{
					Toast.makeText(getApplicationContext(), "Please add a name for this medicine", Toast.LENGTH_SHORT).show();
					return;
				}
				String description = details.getString("description", "No Description");
				if(description.equals(R.string.default_med_description)) 
				{
					Toast.makeText(getApplicationContext(), "Please add a name for this medicine", Toast.LENGTH_SHORT).show();
					return;
				}
				String administration = details.getString("administration", "No Administration");
				int dosage = details.getInt("dosage", -1);
				
				String startDate = details.getString("startdate", "No start date");
				String stopDate = details.getString("stopdate", "No stop date");
				String startTime = details.getString("starttime", "No start time");
				
				ListPreference listPreferenceUnit = (ListPreference) findPreference("unit");
				String unit = listPreferenceUnit.getEntry().toString();
				
				ListPreference listPreferenceInterval = (ListPreference) findPreference("interval");
				String interval = listPreferenceInterval.getEntry().toString();
				
				int timeInterval = -1;
				int startTimeHour = -1;
				int startTimeMinute = -1;
				int startDateDay = -1;
				int startDateMonth = -1;
				int startDateYear = -1;
				int stopDateDay = -1;
				int stopDateMonth = -1;
				int stopDateYear = -1;
				int nextDateHour = -1;
				int nextDateMinute = -1;
				int nextDateDay = -1;
				int nextDateMonth = -1;
				int nextDateYear = -1;
				
				if (!interval.equals("")) {
					timeInterval = Integer.parseInt(interval.substring(0, interval.length() - 1));
				}
				
				if (!startTime.equals("No start time")) {
					String[] pieces = startTime.split(":");
					startTimeHour = Integer.parseInt(pieces[0]);
					startTimeMinute = Integer.parseInt(pieces[1]);
				}
				
				if (!startDate.equals("No start date")) {
					String[] pieces = startDate.split("/");
					startDateDay = Integer.parseInt(pieces[0]);
					startDateMonth = Integer.parseInt(pieces[1]);
					startDateYear = Integer.parseInt(pieces[2]);
				}
				
				if (!stopDate.equals("No stop date")) {
					String[] pieces = stopDate.split("/");
					stopDateDay = Integer.parseInt(pieces[0]);
					stopDateMonth = Integer.parseInt(pieces[1]);
					stopDateYear = Integer.parseInt(pieces[2]);
				}
				
				if ( startTimeHour == -1 || startTimeMinute == -1 || timeInterval == -1 ||
						startDateDay == -1 || startDateMonth == -1 || startDateYear == -1 ||
						stopDateDay == -1 || stopDateMonth == -1 || stopDateYear == -1 ) {
					Toast.makeText(getApplicationContext(), "Alarm couldn't be set. You must complete all the time specification.",
							Toast.LENGTH_LONG).show();
					return;
				}
				else {
					Date start = new Date(startDateYear - 1900, startDateMonth, startDateDay, 
							startTimeHour, startTimeMinute);
					Date end = new Date(stopDateYear - 1900, stopDateMonth, stopDateDay + 1, 0, 0);
					
					if (start.after(end)) {
						Toast.makeText(getApplicationContext(), "Alarm couldn't be set. Start date after stop date.",
								Toast.LENGTH_LONG).show();
						return;
					}
					else {
						Date currentDate = new Date();
						Date next = start;
						
						while (next.before(currentDate)) {
							next = new Date(next.getTime() + timeInterval * HOUR);
						}
						
						nextDateDay = next.getDate();
						nextDateMonth = next.getMonth();
						nextDateYear = next.getYear() + 1900;
						nextDateHour = next.getHours();
						nextDateMinute = next.getMinutes();
					}
				}
				
				AtomicInteger atomicInteger = new AtomicInteger();
				Med medicine = new Med(name, description, administration, dosage, unit, timeInterval,
						startTimeHour, startTimeMinute, startDateDay, startDateMonth, startDateYear,
						nextDateHour, nextDateMinute, nextDateDay, nextDateMonth, nextDateYear,
						stopDateDay, stopDateMonth, stopDateYear, atomicInteger.getAndIncrement());
				
				//ar trebui adaugat medicamentul cu addMedToList care seteaza si alarma.
				//dar momentan pentru test tinem asa.
				MedVector.getInstance().addMedToList(medicine, getApplicationContext());
				MedDb db = new MedDb(getApplicationContext());
				db.addMed(medicine);
				db.close();
				
		//		MedVector.getInstance().updateServerDatabase(MedSetActivity.this);
							
				finish();
				
				//Toast.makeText(getApplicationContext(), timeInterval + "", Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	private void createAndShowDialog(Exception exception, String title, Context context) {
		Throwable ex = exception;
		if(exception.getCause() != null){
			ex = exception.getCause();
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setMessage(ex.getMessage());
		builder.setTitle(title);
		builder.create().show();

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
	private static void bindPreferenceSummaryToStringValue(Preference preference) {
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
	
	private static void bindPreferenceSummaryToIntValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getInt(preference.getKey(),
						-1));
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
			addPreferencesFromResource(R.xml.preferences);

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

