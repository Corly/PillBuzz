package ro.medapp1;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MedVector {
	private static MedVector INSTANCE = new MedVector();
	private ArrayList<Med> list = new ArrayList<Med>();

	private MedVector() {}

	public static MedVector getInstance() {
		return INSTANCE;
	}

	public ArrayList<Med> getList() {
		return list;
	}

	public boolean addMedToList(Med med, Context context) {
		Calendar calNow = Calendar.getInstance();
		Calendar calSet = (Calendar) calNow.clone();

		Log.d("Debug", med.getFirstDoseHour() + " " + med.getFirstDoseMinute() );
		calSet.set(Calendar.HOUR_OF_DAY, med.getFirstDoseHour());
		calSet.set(Calendar.MINUTE, med.getFirstDoseMinute());
		calSet.set(Calendar.SECOND, 0);
		calSet.set(Calendar.MILLISECOND, 0);
		med.setAlarmIntent(setAlarm(calSet, context));
		return list.add(med);
	}

	public void removeMedFromList(Context context, Med med) {
		for (Med m : list) {
			if (med.equals(m)) {
				AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(m.getAlarmIntent());
				list.remove(m);
			}
		}
	}

	public void updateMed(Context context, Med med) {
		for (Med m : list) {
			if (med.equals(m)) {
				AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(m.getAlarmIntent());
				list.remove(m);
				list.add(med);
			}
		}
	}

	public PendingIntent setAlarm(Calendar cal, Context context)
	{

		Intent intent = new Intent(context, Alarm.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				context, 1, intent, 0);
		Log.d("Debug", pendingIntent.toString());
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Log.d("Debug", cal.getTimeInMillis() + "");
		alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				pendingIntent);
		return pendingIntent;
	}

}
