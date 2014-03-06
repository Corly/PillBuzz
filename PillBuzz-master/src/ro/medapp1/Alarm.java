package ro.medapp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();


		intent=new Intent(context,MedDetailActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("fromAlarm", true);
		context.startActivity(intent);
	}


}
