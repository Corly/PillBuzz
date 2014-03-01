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
		

	
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
  
		View v=inflater.inflate(R.layout.fragment_bracelet_settings, container, false);

		final NumberPicker delay=(NumberPicker)v.findViewById(R.id.delay_number_picker);
//		final NumberPicker bufferNumber=(NumberPicker)v.findViewById(R.id.buffer_number_picker);
//		final NumberPicker bufferUnit=(NumberPicker)v.findViewById(R.id.buffer_unit_number_picker);
		
		delay.setMaxValue(60);
		delay.setMinValue(0);
//		
//		bufferUnit.setMinValue(0);
//		bufferUnit.setMaxValue(2);
//		
//		bufferUnit.setDisplayedValues( new String[] { "hours", "days", "weeks" });
		
		
//		bufferUnit.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//			
//			@Override
//			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//				switch(newVal)
//				{
//				case 0: {
//					bufferNumber.setMaxValue(24);
//					bufferNumber.setMinValue(1);				
//				}break;
//				case 1: {
//					bufferNumber.setMaxValue(30);
//					bufferNumber.setMinValue(1);
//				}break;
//				case 2: {
//					bufferNumber.setMaxValue(2);
//					bufferNumber.setMinValue(1);
//				}break;
//				}
//			}});
//			
        return inflater.inflate(R.layout.fragment_bracelet_settings, container, false);
    }

		
}

