package ro.medapp1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class UserSetFragment extends Fragment{
	
	public UserSetFragment()
	{
		
	}


	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_user_settings, container, false);
        TextView tv=(TextView)v.findViewById(R.id.silent);
        tv.setTextSize(MedListActivity.text_size);
        tv=(TextView)v.findViewById(R.id.password);
        tv.setTextSize(MedListActivity.text_size);
        tv=(TextView)v.findViewById(R.id.bracelet);
        tv.setTextSize(MedListActivity.text_size);
        return v;
	}

		
}
