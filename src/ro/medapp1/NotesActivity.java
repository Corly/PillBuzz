package ro.medapp1;

import java.util.Date;

import ro.pillbuzz.data.ExtraDb;
import ro.pillbuzz.data.NotesDb;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		
		name=(EditText) findViewById(R.id.name_notes);
		note=(EditText) findViewById(R.id.note_notes);
		save = (Button)findViewById(R.id.save_note);
		cancel=(Button)findViewById(R.id.cancel_note);
		save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				try {
					
					if(name.getText().toString().equals("")||note.getText().toString().equals(""))
					{
						Toast.makeText(NotesActivity.this,"all fields are required",Toast.LENGTH_LONG).show();
						return;
					}
					else
					{
					Date today=new Date();
					NotesDb db = new NotesDb(getApplicationContext());
					db.addNote(name.getText().toString(),note.getText().toString(),today.toString());
					db.close();
								
					
					
					Class sClass = Class.forName("ro.medapp1.MedListActivity");
					Intent intent=new Intent(NotesActivity.this,sClass);
					startActivity(intent);
				}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}			
		});
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Class sClass=null;
				try {
					sClass = Class.forName("ro.medapp1.MedListActivity");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent=new Intent(NotesActivity.this,sClass);
				startActivity(intent);
				
			}			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}
private Button save,cancel;
private EditText name,note;
}
