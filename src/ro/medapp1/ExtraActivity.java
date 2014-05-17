package ro.medapp1;

import java.util.Date;

import ro.pillbuzz.data.ExtraDb;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class ExtraActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extra);

		initializeComponents();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.extra, menu);
		return true;
	}

	private void initializeComponents() {
		spinner = (Spinner) findViewById(R.id.unit_extra);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.doze_entries,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		numberPicker = (NumberPicker) findViewById(R.id.dose_extra);
		numberPicker.setMaxValue(500);
		numberPicker.setMinValue(0);

		name=(EditText) findViewById(R.id.name_extra);
		symptoms=(EditText) findViewById(R.id.symptoms_extra);
		
		
		
		save = (Button) findViewById(R.id.save_extra);
		cancel = (Button) findViewById(R.id.cancel_extra);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if(name.getText().toString().equals("")||symptoms.getText().toString().equals("")||numberPicker.getValue()==0)
				{
					Toast.makeText(ExtraActivity.this,"all fields are required",Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
				Date today=new Date();
				ExtraDb db = new ExtraDb(getApplicationContext());
				db.addExtra(name.getText().toString(),symptoms.getText().toString(), numberPicker.getValue(), spinner.getSelectedItem().toString(),today.toString());
				db.close();
							
				try {
					Class sClass = Class.forName("ro.medapp1.MedListActivity");
					Intent intent = new Intent(ExtraActivity.this, sClass);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				try {
				Class sClass = Class.forName("ro.medapp1.MedListActivity");
				Intent intent = new Intent(ExtraActivity.this, sClass);
				startActivity(intent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		});
	}
	private EditText name,symptoms;
	private NumberPicker numberPicker;
	private Button save, cancel;
	private Spinner spinner;
}
