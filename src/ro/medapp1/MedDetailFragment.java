package ro.medapp1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A fragment representing a single Med detail screen. This fragment is either
 * contained in a {@link MedListActivity} in two-pane mode (on tablets) or a
 * {@link MedDetailActivity} on handsets.
 */
public class MedDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";


	public MedDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_med_detail,
				container, false);

	
		// Show the dummy content as text in a TextView.
		

		return rootView;
	}
}
