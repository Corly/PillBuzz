package ro.medapp1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
	
	private MedListFragment listFragment;
	public static Activity context=null;
	public static float text_size;
	boolean mTwoPane;
	Button back, snooze,notes,calendar,call,extra,settings;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		setContentView(R.layout.activity_med_list);
		
		
		//setting the dimention of the text
		Display display=getWindowManager().getDefaultDisplay();
		text_size=display.getHeight()/30;
		 initializeButtons();			
			listFragment=((MedListFragment) getSupportFragmentManager().findFragmentById(
					R.id.med_list));
		if (findViewById(R.id.med_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
			

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			listFragment.setActivateOnItemClick(true);
			
		}
	

		// TODO: If exposing deep links into your app, handle intents here.
	}
	
	public void initializeButtons()
	{
		//initializing buttons
		
		snooze=(Button) findViewById(R.id.SnoozeButton);
		notes=(Button) findViewById(R.id.NotesButton);
		calendar=(Button) findViewById(R.id.CalendarButton);
		call=(Button) findViewById(R.id.CallButton);
		extra=(Button) findViewById(R.id.ExtraButton);
		settings=(Button) findViewById(R.id.SettingsButton);
		
		//settings backgrounds
		
		snooze.setBackgroundResource(R.drawable.snooze);
		notes.setBackgroundResource(R.drawable.notes);
		calendar.setBackgroundResource(R.drawable.calendar);
		call.setBackgroundResource(R.drawable.phone);
		extra.setBackgroundResource(R.drawable.extra);
		settings.setBackgroundResource(R.drawable.settings);
		
	call.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			SharedPreferences details = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			String number=details.getString("emergencyNo",null);
			if(number!=null)
			{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+number));
				startActivity(callIntent);
			}
		}
	});
	notes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Class sClass=null;
				try {
					sClass = Class.forName("ro.medapp1.NotesActivity");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent=new Intent(MedListActivity.this,sClass);
				startActivity(intent);
				
			}
		});
		
		
		extra.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Class sClass=null;
				try {
					sClass = Class.forName("ro.medapp1.ExtraActivity");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent=new Intent(MedListActivity.this,sClass);
				startActivity(intent);
				
			}
		});
		
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
	}
	/**
	 * Callback method from {@link MedListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		Bundle arguments = new Bundle();
		arguments.putString(MedDetailFragment.ARG_ITEM_ID, id);
		MedVector meds=MedVector.getInstance();
		int position =Integer.parseInt(listFragment.getModels().get(Integer.parseInt(id)).getCounter());
		arguments.putString("name", meds.getList().get(position).getName());
		arguments.putInt("hours", listFragment.getModels().get(Integer.parseInt(id)).getHour());
		arguments.putInt("minutes", listFragment.getModels().get(Integer.parseInt(id)).getMinutes());
		arguments.putInt("dosage", meds.getList().get(position).getDosage());
		arguments.putString("unit", meds.getList().get(position).getUnit());
		arguments.putString("description", meds.getList().get(position).getDescription());
		arguments.putString("administration", meds.getList().get(position).getAdministrationMethod());
		
		
		
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			MedDetailFragment fragment = new MedDetailFragment();

			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.med_detail_container, fragment).commit();
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, MedDetailActivity.class);
			detailIntent.putExtra(MedDetailFragment.ARG_ITEM_ID, id);
			detailIntent.putExtra("arguments",arguments);
			startActivity(detailIntent);
		}
	}
	
	
}
