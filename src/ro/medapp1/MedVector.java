package ro.medapp1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import java.net.MalformedURLException;


public class MedVector {
	
	
	private static int serviceItemCounter=0;
	private MobileServiceClient mClient;
	private MobileServiceTable<Med> serviceMedTable;

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

		int hour = med.getNextDoseHour();
		int minute = med.getNextDoseMinute();
		int interval = med.getInterval();
		int startYear = med.getNextDoseYear();
		int startMonth = med.getNextDoseMonth();
		int startDay = med.getNextDoseDay();
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
			Date nextDate = new Date(startYear - 1900, startMonth, startDay, hour, minute);
			
			calSet.setTimeInMillis(System.currentTimeMillis());
			calSet.setTime(nextDate);
			Log.d("Debug", calSet.toString());
			setRepeatingAlarm(calSet, context, interval, med);
		}
		
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
		
//		Bundle arguments = new Bundle();
//		arguments.putString("name", medicine.getName());
//		arguments.putInt("hours",cal.getTime().getHours());
//		arguments.putInt("minutes",cal.getTime().getMinutes());
//		arguments.putInt("dosage", medicine.getDosage());
//		arguments.putString("unit", medicine.getUnit());
//		arguments.putString("description", medicine.getDescription());
//		arguments.putString("administration", medicine.getAdministrationMethod());
//		intent.putExtra("arguments",arguments);
		
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
		Bundle arguments = new Bundle();
		arguments.putString("name", medicine.getName());
		arguments.putInt("hours",cal.getTime().getHours());
		arguments.putInt("minutes",cal.getTime().getMinutes());
		arguments.putInt("dosage", medicine.getDosage());
		arguments.putString("unit", medicine.getUnit());
		arguments.putString("description", medicine.getDescription());
		arguments.putString("administration", medicine.getAdministrationMethod());
		intent.putExtra("arguments",arguments);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 
				id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//medicine.setAlarmIntent(pendingIntent);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				interval * HOUR, pendingIntent);
		
	}

	/**
	 * uploadeaza vectorul de medicamente pe azure
	 *TODO
	 *verificarea daca exista sau nu medicamentul deja
	 *updatarea medicamentelor existente
	 *stergerea meicamentelor
	 * @param context
	 */
	
	public void updateServerDatabase(final Context context)
	{
		
		try {
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			mClient = new MobileServiceClient( "https://pillbuzz.azure-mobile.net/", "gKTzUXhsGvQWrIiyoBXYlJluHiOQhc45", context );

			// Get the Mobile Service Table instance to use
			serviceMedTable = mClient.getTable(Med.class);
		} catch (MalformedURLException e) {
			createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error",context);
		}
		
		
		for(int i=0; i<list.toArray().length; i++)
		{
			
			serviceMedTable.insert(list.get(i), new TableOperationCallback<Med>() {

				public void onCompleted(Med entity, Exception exception, ServiceFilterResponse response) {
					
					if (exception == null) {
						
					} else {
						createAndShowDialog(exception, "Error",context);
					}

				}
			});

		}
		
		serviceItemCounter=list.toArray().length;
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

}

