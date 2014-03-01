package ro.medapp1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MedSetFragment extends ListFragment{
	
	String medNames[] = {"med1","med2","med3","+"};
	static Activity context;
	

	
	public MedSetFragment()
	{
		
	}

	public void setContext(Activity context)
	{
		this.context=context;
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
		
		if(context!=null)
		
		{
		setListAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, medNames));
		
		
		}
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Class medClass=null;
		try {
			medClass = Class.forName("ro.medapp1.MedSettings");
			Intent medSet=new Intent(context,medClass);
			startActivity(medSet);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	

}
