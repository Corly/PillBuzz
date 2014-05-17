package ro.pillbuzz.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExtraDb extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "extra.db";
		private static final int DATABASE_VERSION = 1;
		
		public interface ExtraTable {
			String TABLE_NAME = "extra";			
			String COLUMN_NAME = "name";
			String COLUMN_SYMPTOMS = "symptoms";
			String COLUMN_DOSAGE = "dosage";
			String COLUMN_UNITS = "units";
			String COLUMN_DATE="date";
			
			String CREATE_QUERY = "create table " + TABLE_NAME +
					"(" + COLUMN_NAME + " text not null, " +
					COLUMN_SYMPTOMS + " text not null, " +
					COLUMN_DOSAGE + " integer not null, " +
					COLUMN_UNITS + " text not null, " +
					COLUMN_DATE + " text not null )";
		}
				
		public ExtraDb(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(ExtraTable.CREATE_QUERY);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + ExtraTable.TABLE_NAME);
			onCreate(db);
		}
		
		public long addExtra(String name, String symptoms, int dosage, String units,String date) {
			SQLiteDatabase db = this.getReadableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(ExtraTable.COLUMN_NAME, name);
			values.put(ExtraTable.COLUMN_SYMPTOMS, symptoms);			
			values.put(ExtraTable.COLUMN_DOSAGE, dosage);
			values.put(ExtraTable.COLUMN_UNITS, units);
			values.put(ExtraTable.COLUMN_DATE, date);
			
			long id;
			id = db.insert(ExtraTable.TABLE_NAME, null, values);
			db.close();
			
			return id;
		}
}