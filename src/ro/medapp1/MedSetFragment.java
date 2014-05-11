package ro.medapp1;

import java.util.ArrayList;

import ro.pillbuzz.data.MedDb;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MedSetFragment extends ListFragment {
	
	ArrayAdapter<String> adapter;
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
		super.setArguments(args);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getActivity()!=null) {
			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, medNames);
			setListAdapter(adapter);
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != MedVector.getInstance().getList().size()) {
					final int pos = position;
					AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
					dialog.setTitle("Delete medicine");
					dialog.setMessage("Are you sure you want to delete this medicine?");
					dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface d, int which) {
							MedDb db = new MedDb(getActivity());
							db.deleteMed(MedVector.getInstance().getList().get(pos));
							MedVector.getInstance().removeMedFromList(getActivity(), 
									MedVector.getInstance().getList().get(pos));
							medNames.remove(pos);
							adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, medNames);
							setListAdapter(adapter);
						}
					});
					dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface d, int which) {
							d.dismiss();
						}
					});
					dialog.create().show();
				}
				return false;
			}
		});

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
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Class medClass=null;
		try {
			medClass = Class.forName("ro.medapp1.MedSetActivity");
			Intent medSet=new Intent(getActivity(),medClass);
			medSet.putExtra("position", position);
			startActivity(medSet);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}

}
