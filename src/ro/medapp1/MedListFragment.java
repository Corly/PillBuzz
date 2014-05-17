package ro.medapp1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A list fragment representing a list of Meds. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link MedDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class MedListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	// private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	// private static Callbacks sDummyCallbacks = new Callbacks() {
	// @Override
	// public void onItemSelected(String id) {
	// }
	// };

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MedListFragment() {
	}

	ArrayList<Model> models;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setListAdapter(new
		// ArrayAdapter<DummyContent.DummyItem>(getActivity(),
		// android.R.layout.simple_list_item_activated_1,
		// android.R.id.text1, DummyContent.ITEMS));

		// if extending Activity
		// setContentView(R.layout.activity_main);

		// 1. pass context and data to the custom adapter
		
		MyAdapter adapter = new MyAdapter(getActivity(), generateData());
		
		// if extending Activity 2. Get ListView from activity_main.xml
		// ListView listView = (ListView) findViewById(R.id.med_list);

		// 3. setListAdaptegetr
		// listView.setAdapter(adapter); if extending Activity
		setListAdapter(adapter);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyAdapter adapter = new MyAdapter(getActivity(), generateData());

		setListAdapter(adapter);
	}

	private ArrayList<Model> generateData() {
		models = new ArrayList<Model>();
		Date today = new Date();
		int day,month,year;
		day=today.getDate();
		month=today.getMonth()+1;
		year=today.getYear()+1900;
		if(day<10)
			if(month<10)
				models.add(new Model("0"+today.getDate() + "/0" + (today.getMonth()+1) + "/"+ (today.getYear()+1900)));
			else 
				models.add(new Model("0"+today.getDate() + "/" + (today.getMonth()+1) + "/"+ (today.getYear()+1900)));
		else 
			if(month<10)
				models.add(new Model(today.getDate() + "/0" + (today.getMonth()+1) + "/"+ (today.getYear()+1900)));
			else
				models.add(new Model(today.getDate() + "/" + (today.getMonth()+1) + "/"+ (today.getYear()+1900)));
		System.out.println("add");
		for (Med med : MedVector.getInstance().getList()) {
			System.out.println("for each");
			Date startDate = new Date(med.getStartDateYear() - 1900,
					med.getStartDateMonth(), med.getStartDateDay(),
					med.getFirstDoseHour(), med.getFirstDoseMinute());
			Date endDate = new Date(med.getEndDateYear() - 1900,
					med.getEndDateMonth(), med.getEndDateDay());
			if (today.after(startDate) && today.before(endDate)) {
				recursiveAdd(med.getName(), ((Integer) MedVector.getInstance()
						.getList().indexOf(med)).toString(),
						med.getFirstDoseHour(), med.getFirstDoseMinute(),
						med.getInterval(), false);
				recursiveAdd(med.getName(), ((Integer) MedVector.getInstance()
						.getList().indexOf(med)).toString(),
						med.getFirstDoseHour()+med.getInterval(), med.getFirstDoseMinute(),
						med.getInterval(), true);
			}
			
		}
		//System.out.println(models.get(0).getTitle());
		Collections.sort(models,new CustomComparator());
		return models;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
		getListView().setDivider(null);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		// mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		// mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	public ArrayList<Model> getModels() {
		return models;
	}

	public void recursiveAdd(String element, String index, int hour,
			int minutes, int interval, boolean add) {

		if (add) {
			if (hour < 24) {
				models.add(new Model(element, index, hour , minutes));
				recursiveAdd(element, index, hour + interval, minutes,
						interval, true);
			}
		} else {
			if (hour > 0) {
				recursiveAdd(element, index, hour - interval, minutes,
						interval, false);
				models.add(new Model(element, index, hour , minutes));
			}
		}
	}
}

class CustomComparator implements Comparator<Model> {
    @Override
    public int compare(Model o1, Model o2) {
        if(o1.isGroupHeader()||o2.isGroupHeader()) return 0;
    	return o1.compareTo(o2);
    }

	
}