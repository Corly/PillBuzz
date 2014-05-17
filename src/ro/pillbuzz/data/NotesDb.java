package ro.pillbuzz.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDb extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "notes.db";
		private static final int DATABASE_VERSION = 1;
		
		public interface NotesTable {
			String TABLE_NAME = "note";
			
			String COLUMN_TITLE = "title";
			String COLUMN_CONTENT = "content";
			String COLUMN_DATE="date";
			
			String CREATE_QUERY = "create table " + TABLE_NAME +
					"(" + COLUMN_TITLE + " text not null, " +
					COLUMN_CONTENT + " text not null, " +
					COLUMN_DATE + " text not null, )";
		}
				
		public NotesDb(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(NotesTable.CREATE_QUERY);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + NotesTable.TABLE_NAME);
			onCreate(db);
		}
		
		public long addNote(String title, String content,String date) {
			SQLiteDatabase db = this.getReadableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(NotesTable.COLUMN_TITLE, title);
			values.put(NotesTable.COLUMN_CONTENT, content);
			values.put(NotesTable.COLUMN_DATE, date);
			
			long id;
			id = db.insert(NotesTable.TABLE_NAME, null, values);
			db.close();
			
			return id;
		}
}