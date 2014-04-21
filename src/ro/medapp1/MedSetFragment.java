package ro.medapp1;


import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;




public class MedSetFragment extends ListFragment{
	
	ArrayAdapter adapter;
	ArrayList<String> medNames = new ArrayList<String>();
	//static Activity context;
	
	public MedSetFragment()
	{
		ArrayList<Med> list = MedVector.getInstance().getList();
		for (Med m : list) {
			medNames.add(m.getName());
		}
		medNames.add("+");
	}

//	public void setContext(Activity context)
//	{
//		this.context=context;
//	}
	
	
	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	
		
		if(getActivity()!=null)
		
		{
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, medNames);
		setListAdapter(adapter);
		//MedVector.getInstance().setMClient(getActivity());
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		medNames = new ArrayList<String>();
		ArrayList<Med> list = MedVector.getInstance().getList();
		for (Med m : list) {
			medNames.add(m.getName());
		}
		medNames.add("+");
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, medNames);
		setListAdapter(adapter);
		//adapter.notifyDataSetChanged();
		//adapter.notifyDataSetInvalidated();
		//setListAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, medNames));
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Class medClass=null;
		try {
			medClass = Class.forName("ro.medapp1.MedSetActivity");
			Intent medSet=new Intent(getActivity(),medClass);
			medSet.putExtra("position", position);
			startActivity(medSet);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	

}
