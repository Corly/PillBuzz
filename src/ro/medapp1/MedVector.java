package ro.medapp1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MedVector {
	private static MedVector INSTANCE = new MedVector();
	private ArrayList<Med> list = new ArrayList<Med>();
	private final long HOUR = 3600 * 1000; //hours in miliseconds
	private int counter = 0;

	private MedVector() {}

	public static MedVector getInstance() {
		return INSTANCE;
	}

	public ArrayList<Med> getList() {
		return list;
	}

	@SuppressWarnings({ "deprecation" })
	public boolean addMedToList(Med med, Context context) {
		//Set the calendar for the alarms
		Calendar calNow = Calendar.getInstance();
		Calendar calSet = (Calendar) calNow.clone();

		int hour = med.getFirstDoseHour();
		int minute = med.getFirstDoseMinute();
		int interval = med.getInterval();
		int startYear = med.getStartDateYear();
		int startMonth = med.getStartDateMonth();
		int startDay = med.getStartDateDay();
		int stopYear = med.getEndDateYear();
		int stopMonth = med.getEndDateMonth();
		int stopDay = med.getEndDateDay();
		
		Log.d("Debug", "START DAY " + startDay + " " + startMonth); 
		
		if ( hour == -1 || minute == -1 || interval == -1 || startDay == -1 ||
				startMonth == -1 || startYear == -1 || stopDay == -1 ||
				stopMonth == -1 || stopYear == -1 ) {
			Toast.makeText(context, "Alarm couldn't be set. You must complete all the time specification.",
					Toast.LENGTH_LONG).show();
		} 
		else {
			Date startDate = new Date(startYear - 1900, startMonth, startDay, hour, minute);
			Date endDate = new Date(stopYear - 1900, stopMonth, stopDay + 1, 0, 0);
			
			if (startDate.after(endDate)) {
				Toast.makeText(context, "Alarm couldn't be set. Start date after stop date.",
						Toast.LENGTH_LONG).show();
			}
			else {
				Date currentDate = new Date();
				/*while (startDate.before(endDate)) {
					if (startDate.after(currentDate)) {
						Log.d("Debug", startDate.toString());
						
						Log.d("Debug","Aici se seteaza mai multe: " + startDate.getHours() + " " + startDate.getMinutes()
								+ " " + startDate.getDate() + " " + startDate.getMonth() + " " + startDate.getYear());
						calSet.setTimeInMillis(System.currentTimeMillis());
						Log.d("Debug", calSet.toString());
						calSet.set(Calendar.DATE, startDate.getDate());
						//calSet.set(Calendar.MONTH, startDate.getMonth());
						//calSet.set(Calendar.YEAR, startDate.getYear());
						calSet.set(Calendar.HOUR_OF_DAY, startDate.getHours());
						calSet.set(Calendar.MINUTE, startDate.getMinutes());
						calSet.set(Calendar.SECOND, 0);
						calSet.set(Calendar.MILLISECOND, 0);
						
						//calSet.getTimeInMillis();
						
						calSet.setTime(startDate);
						Log.d("Debug", calSet.toString());
						
						Log.d("Debug", "Shalala calendar " + calSet.get(Calendar.DAY_OF_MONTH) + " " +
								calSet.get(Calendar.HOUR_OF_DAY) + " " + calSet.get(Calendar.MINUTE) + " " + 
								calSet.get(Calendar.MONTH));
						Log.d("Debug", "cal set time in millis " + calSet.getTimeInMillis());
						//med.addPendingIntentAlarm(setAlarm(calSet, context, med));
						setRepeatingAlarm(calSet, context, interval, med);
					}
					startDate = new Date(startDate.getTime() + interval * HOUR);
				}*/
				while (startDate.before(currentDate)) {
					startDate = new Date(startDate.getTime() + interval * HOUR);
				}
				
				calSet.setTimeInMillis(System.currentTimeMillis());
				calSet.setTime(startDate);
				Log.d("Debug", calSet.toString());
				setRepeatingAlarm(calSet, context, interval, med);
			}
		}
		
		//Log.d("Debug", med.getFirstDoseHour() + " " + med.getFirstDoseMinute() );
		/*calSet.set(Calendar.HOUR_OF_DAY, med.getFirstDoseHour());
		calSet.set(Calendar.MINUTE, med.getFirstDoseMinute());
		calSet.set(Calendar.SECOND, 0);
		calSet.set(Calendar.MILLISECOND, 0);
		med.setAlarmIntent(setAlarm(calSet, context));*/
		return list.add(med);
	}

	public void removeMedFromList(Context context, Med med) {
		for (Med m : list) {
			if (med.equals(m)) {
				Alarm.cancelIntent(context, med.getIntentID(), med.getAlarmIntent());
				//alarmManager.cancel(m.getAlarmIntent());
				list.remove(m);
			}
		}
	}

	public void updateMed(Context context, Med med) {
		removeMedFromList(context, med);
		addMedToList(med, context);
	}

	public PendingIntent setAlarm(Calendar cal, Context context, Med medicine)
	{

		Intent intent = new Intent(context, Alarm.class);
		medicine.setAlarmIntent(intent);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				context, counter++, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//Log.d("Debug", pendingIntent.toString());
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//Log.d("Debug", "cal get time " + cal.getTime());
		alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				pendingIntent);
		return pendingIntent;
	}
	
	public void setRepeatingAlarm(Calendar cal, Context context, int interval, Med medicine) {
		int id = medicine.getIntentID();
		
		Intent intent = new Intent(context, Alarm.class);
		intent.putExtra("endDay", medicine.getEndDateDay());
		intent.putExtra("endMonth", medicine.getEndDateMonth());
		intent.putExtra("endYear", medicine.getEndDateYear());
		intent.putExtra("interval", interval);
		intent.putExtra("id", id);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 
				id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//medicine.setAlarmIntent(pendingIntent);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				interval * HOUR, pendingIntent);
		
	}

}
