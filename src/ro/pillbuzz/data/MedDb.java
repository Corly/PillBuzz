package ro.pillbuzz.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedDb extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "meds.db";
	private static final int DATABASE_VERSION = 1;
	
	public interface MedTable {
		String TABLE_NAME = "medicine";
		
		String COLUMN_ID = "_id";
		String COLUMN_NAME = "name";
		String COLUMN_DESCRIPTION = "description";
		String COLUMN_ADMINISTRATION = "administration";
		String COLUMN_DOSAGE = "dosage";
		String COLUMN_UNITS = "units";
		
		String CREATE_QUERY = "create table " + TABLE_NAME +
				"(" + COLUMN_ID + " integer primary key autoincrement, " +
				COLUMN_NAME + " text not null, " +
				COLUMN_DESCRIPTION + " text not null, " +
				COLUMN_ADMINISTRATION + " text not null, " +
				COLUMN_DOSAGE + " integer not null, " +
				COLUMN_UNITS + " text not null)";
	}
	
	public interface AlarmTable {
		String TABLE_NAME = "alarm";
		
		String COLUMN_ID = "_id";
		String COLUMN_INTERVAL = "interval";
		String COLUMN_START = "start";
		String COLUMN_END = "end";
		
		String CREATE_QUERY = "create table " + TABLE_NAME +
				"(" + COLUMN_ID + " integer primary key autoincrement, " +
				COLUMN_INTERVAL + " integer not null, " +
				COLUMN_START + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
				COLUMN_END + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
	}
	
	public MedDb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MedTable.CREATE_QUERY);
		db.execSQL(AlarmTable.CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + MedTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + AlarmTable.TABLE_NAME);
		onCreate(db);
	}

}
