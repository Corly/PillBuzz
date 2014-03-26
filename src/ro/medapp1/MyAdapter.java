package ro.medapp1;


import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Model> {

	private final Context context;
	private final ArrayList<Model> modelsArrayList;

	public MyAdapter(Context context, ArrayList<Model> modelsArrayList) {

		super(context, R.xml.target_item, modelsArrayList);

		this.context = context;
		this.modelsArrayList = modelsArrayList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// 1. Create inflater 
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 2. Get rowView from inflater

		View rowView = null;
		if(!modelsArrayList.get(position).isGroupHeader()){
			rowView = inflater.inflate(R.xml.target_item, parent, false);

			// 3. Get icon,title & counter views from the rowView
			TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

			// 4. Set the text for textView
			titleView.setText(modelsArrayList.get(position).getTitle());

			rowView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					MedListActivity list=(MedListActivity)context;
					list.onItemSelected(Integer.toString(position));
				}
			});

		}
		else{
			rowView = inflater.inflate(R.xml.header, parent, false);
			TextView titleView = (TextView) rowView.findViewById(R.id.header);
			titleView.setText(modelsArrayList.get(position).getTitle());
			rowView.setOnClickListener(null);
		}

		// 5. retrn rowView
		return rowView;
	}
}