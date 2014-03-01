package ro.medapp1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class BraceletSetFragment extends Fragment {

	public BraceletSetFragment()
	{
		
	}


	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		final NumberPicker delay=(NumberPicker)getView().findViewById(R.id.delay_number_picker);
//		final NumberPicker bufferNumber=(NumberPicker)getView().findViewById(R.id.buffer_number_picker);
//		final NumberPicker bufferUnit=(NumberPicker)getView().findViewById(R.id.buffer_unit_number_picker);
//		
//		delay.setMaxValue(60);
//		delay.setMinValue(0);
	
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bracelet_settings, container, false);
    }

		
}

