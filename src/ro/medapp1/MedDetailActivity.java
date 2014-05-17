package ro.medapp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

/**
 * An activity representing a single Med detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link MedListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link MedDetailFragment}.
 */
public class MedDetailActivity extends FragmentActivity {
	Vibrator v;
	MediaPlayer mediaPlayer;
	public static boolean isSnooze = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_med_detail);
		
		
		Log.d("Hello","Balloo");
		Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("fromAlarm", false)) {
        	Log.d("Hello","Balloo");
    		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    		// Vibrate for 500 milliseconds
    		long[] ceva = { 500, 1000 };
    		v.vibrate(ceva, 0);
    		if (!isSnooze) {
    			mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
    			mediaPlayer.start();
    		}
    		
    		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle("Remainder");
    		builder.setMessage("You have to take some pills");
    		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				v.cancel();
    				if (!isSnooze) {
    					mediaPlayer.stop();
    				}
    			}
    		});
    		builder.create().show();
        }

		// Show the Up button in the action bar.
				
		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments;
			Intent myIntent=getIntent();
			arguments=myIntent.getExtras().getBundle("arguments");
			MedDetailFragment fragment = new MedDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.med_detail_container, fragment).commit();
		}
	}

	public static boolean isSnooze() {
		return isSnooze;
	}

	public static void setSnooze(boolean isSnooze) {
		MedDetailActivity.isSnooze = isSnooze;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this, MedListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
