package ro.medapp1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;



public class UserSetFragment extends Fragment{
	
	Switch silentSwitch,braceletSwitch,passwordSwitch;
	Button change_passwd,save_passwd,cancel_passwd;
	TextView silent,password,bracelet;
	
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
        
        //setting the text size 
        
        silent=(TextView)v.findViewById(R.id.silent);        
        password=(TextView)v.findViewById(R.id.password);      
        bracelet=(TextView)v.findViewById(R.id.bracelet);     

        change_passwd=(Button)v.findViewById(R.id.change_passwd);
        save_passwd=(Button)v.findViewById(R.id.save_passwd);
        cancel_passwd=(Button)v.findViewById(R.id.cancel_passwd);
        
        silentSwitch=(Switch)v.findViewById(R.id.togglebutton_silent);
        braceletSwitch=(Switch)v.findViewById(R.id.togglebutton_bracelet);
        passwordSwitch=(Switch)v.findViewById(R.id.togglebutton_password);
     
        return v;
	}
	
	public void setup()
	{
		silent.setTextSize(MedListActivity.text_size);
		password.setTextSize(MedListActivity.text_size);
		bracelet.setTextSize(MedListActivity.text_size);
		change_passwd.setTextSize(MedListActivity.text_size);
	    save_passwd.setTextSize(MedListActivity.text_size);
	    cancel_passwd.setTextSize(MedListActivity.text_size);
	    
	    
	}

		
}
