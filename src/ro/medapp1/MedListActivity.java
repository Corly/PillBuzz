package ro.medapp1;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

/**
 * An activity representing a list of Meds. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link MedDetailActivity} representing item details. On tablets, the activity
 * presents the list of items and item details side-by-side using two vertical
 * panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MedListFragment} and the item details (if present) is a
 * {@link MedDetailFragment}.
 * <p>
 * This activity also implements the required {@link MedListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class MedListActivity extends FragmentActivity implements
		MedListFragment.Callbacks {
	
	
	public static Activity context=null;
	public static float text_size;
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.activity_med_list);
		
		
		//setting the dimention of the text
		Display display=getWindowManager().getDefaultDisplay();
		text_size=display.getHeight()/30;
		

		if (findViewById(R.id.med_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			
			MedListFragment listFragment=((MedListFragment) getSupportFragmentManager().findFragmentById(
					R.id.med_list));
			listFragment.setActivateOnItemClick(true);
			
		}
		
		Button snooze=(Button) findViewById(R.id.SnoozeButton);
		snooze.setBackgroundResource(R.drawable.snooze);
		Button notes=(Button) findViewById(R.id.NotesButton);
		notes.setBackgroundResource(R.drawable.notes);
		Button calendar=(Button) findViewById(R.id.CalendarButton);
		calendar.setBackgroundResource(R.drawable.calendar);
		Button call=(Button) findViewById(R.id.CallButton);
		call.setBackgroundResource(R.drawable.phone);
		Button extra=(Button) findViewById(R.id.ExtraButton);
		extra.setBackgroundResource(R.drawable.extra);
		
		
		Button settings=(Button) findViewById(R.id.SettingsButton);
		settings.setBackgroundResource(R.drawable.settings);
		settings.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
						Class sClass=null;
						try {
							sClass = Class.forName("ro.medapp1.Settings");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Intent intent=new Intent(MedListActivity.this,sClass);
						startActivity(intent);
			}
			
		});

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link MedListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(MedDetailFragment.ARG_ITEM_ID, id);
			MedDetailFragment fragment = new MedDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.med_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, MedDetailActivity.class);
			detailIntent.putExtra(MedDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	
}
