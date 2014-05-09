package ro.medapp1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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

		TextView name =(TextView)rootView .findViewById(R.id.med_name_tv);
		TextView time=(TextView)rootView.findViewById(R.id.time_tv);
		TextView dosage=(TextView)rootView.findViewById(R.id.dosage_tv);
		TextView description=(TextView)rootView.findViewById(R.id.description_tv);
		TextView administration=(TextView)rootView.findViewById(R.id.administration_tv);
		
		name.setText(getArguments().getString("name"));
		int hours,minutes;
		hours=getArguments().getInt("hours");
		minutes=getArguments().getInt("minutes");
		if(minutes<10)
			time.setText(hours+" : 0"+minutes);
		else
			time.setText(hours+" : "+minutes);
		dosage.setText(getArguments().getInt("dosage")+"  "+getArguments().getString("unit"));
		description.setText(getArguments().getString("description"));
		administration.setText(getArguments().getString("administration"));
		return rootView;
	}
}
