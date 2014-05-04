package ro.medapp1;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
		
		int endDay = intent.getIntExtra("endDay", -1);
		int endMonth = intent.getIntExtra("endMonth", -1);
		int endYear = intent.getIntExtra("endYear", -1);
		int interval = intent.getIntExtra("interval", -1);
		int id = intent.getIntExtra("id", -1);
		
		Date date = new Date();
		date = new Date(date.getTime() + interval * 3600 * 1000);
		Log.d("Debug", date.getYear() + " an");
		if (date.getYear() + 1900 > endYear) {
			cancelIntent(context, id, intent);
		} else if (date.getYear() + 1900 == endYear ) {
			if (date.getMonth() > endMonth) {
				cancelIntent(context, id, intent);
			} else if (date.getMonth() == endMonth) {
				if (date.getDate() >= endDay) {
					cancelIntent(context, id, intent);
				}
			}
		}
		
		intent=new Intent(context,MedDetailActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("fromAlarm", true);
		context.startActivity(intent);
	}
	
	public static void cancelIntent(Context context, int id, Intent intent) {
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 
				id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}


}
