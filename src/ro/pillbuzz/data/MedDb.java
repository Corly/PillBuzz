package ro.pillbuzz.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ro.medapp1.Med;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
		String COLUMN_NEXT = "next";
		String COLUMN_INTENT_ID = "intent_id";
		
		String CREATE_QUERY = "create table " + TABLE_NAME +
				"(" + COLUMN_ID + " integer primary key autoincrement, " +
				COLUMN_INTERVAL + " integer not null, " +
				COLUMN_START + " DATETIME, " +
				COLUMN_END + " DATETIME, " + 
				COLUMN_NEXT + " DATETIME, " +
				COLUMN_INTENT_ID + " integer not null)";
	}
	
	public interface RelationTable {
		String TABLE_NAME = "relation";
		
		String COLUMN_ID = "_id";
		String COLUMN_MEDICINE = "medicine";
		String COLUMN_ALARM = "alarm";
		
		String CREATE_QUERY = "create table " + TABLE_NAME +
				"(" + COLUMN_ID + " integer primary key autoincrement, " +
				COLUMN_MEDICINE + " integer not null, " + 
				COLUMN_ALARM + " integer not null)";
	}
	
	public MedDb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MedTable.CREATE_QUERY);
		db.execSQL(AlarmTable.CREATE_QUERY);
		db.execSQL(RelationTable.CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + MedTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + AlarmTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + RelationTable.TABLE_NAME);
		onCreate(db);
	}
	
	public long addMedicine(String name, String description, String administration, int dosage, String units) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(MedTable.COLUMN_NAME, name);
		values.put(MedTable.COLUMN_DESCRIPTION, description);
		values.put(MedTable.COLUMN_ADMINISTRATION, administration);
		values.put(MedTable.COLUMN_DOSAGE, dosage);
		values.put(MedTable.COLUMN_UNITS, units);
	
		long id;
		id = db.insert(MedTable.TABLE_NAME, null, values);
		db.close();
		
		return id;
	}

	public long addAlarm(int interval, Date startDate, Date endDate, Date nextDate, int intentId) {
		SQLiteDatabase db = this.getReadableDatabase();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		
		Log.d("DEBUG", dateFormat.format(startDate) + "\n" +
				dateFormat.format(endDate) + "\n" +
				dateFormat.format(nextDate));
		
		ContentValues values = new ContentValues();
		values.put(AlarmTable.COLUMN_INTERVAL, interval);
		values.put(AlarmTable.COLUMN_START, dateFormat.format(startDate));
		values.put(AlarmTable.COLUMN_END, dateFormat.format(endDate));
		values.put(AlarmTable.COLUMN_NEXT, dateFormat.format(nextDate));
		values.put(AlarmTable.COLUMN_INTENT_ID, intentId);
		
		long id;
		id = db.insert(AlarmTable.TABLE_NAME, null, values);
		db.close();
		
		return id;
	}
	
	public long addRelation(int medId, int alarmId) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(RelationTable.COLUMN_MEDICINE, medId);
		values.put(RelationTable.COLUMN_ALARM, alarmId);
		
		long id;
		id = db.insert(RelationTable.TABLE_NAME, null, values);
		db.close();
		
		return id;
	}
	
	public void addMed(Med med) {
		long medId = addMedicine(med.getName(), med.getDescription(), 
				med.getAdministrationMethod(), med.getDosage(), med.getUnit());
		
		Calendar calendar = Calendar.getInstance();
		//start date
		calendar.clear();
		calendar.set(med.getStartDateYear(), med.getStartDateMonth(), 
				med.getStartDateDay(), med.getFirstDoseHour(), med.getFirstDoseMinute());
		Date startDate = calendar.getTime();
		
		//end date
		calendar.clear();
		calendar.set(med.getEndDateYear(), med.getEndDateMonth(), med.getEndDateDay() + 1);
		Date endDate = calendar.getTime();
		
		//next date
		calendar.clear();
		calendar.set(med.getNextDoseYear(), med.getNextDoseMonth(), med.getNextDoseDay(),
				med.getNextDoseHour(), med.getNextDoseMinute());
		Date nextDate = calendar.getTime();
		
		long alarmId = addAlarm(med.getInterval(), startDate, endDate, nextDate, med.getIntentID());
		
		addRelation((int)medId, (int)alarmId);
	}
	
	@SuppressWarnings("deprecation")
	public Med getMed(String name) {
		Med med = new Med();
		
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQueryMed = "SELECT * FROM " + MedTable.TABLE_NAME + " WHERE " + MedTable.COLUMN_NAME +
				" = '" + name + "'";

		Cursor c = db.rawQuery(selectQueryMed, null);
		if (c != null) {
			c.moveToFirst();
		}
		
		med.setName(c.getString(c.getColumnIndex(MedTable.COLUMN_NAME)));
		med.setDescription(c.getString(c.getColumnIndex(MedTable.COLUMN_DESCRIPTION)));
		med.setAdministrationMethod(c.getString(c.getColumnIndex(MedTable.COLUMN_ADMINISTRATION)));
		med.setUnit(c.getString(c.getColumnIndex(MedTable.COLUMN_UNITS)));
		med.setDosage(c.getInt(c.getColumnIndex(MedTable.COLUMN_DOSAGE)));
		
		String selectQueryAlarm = "SELECT * FROM " + AlarmTable.TABLE_NAME + " ta, " +
				MedTable.TABLE_NAME + " tm, " + RelationTable.TABLE_NAME + " tr WHERE tm." +
				MedTable.COLUMN_NAME + " = '" + name + "'" + " AND tm." + MedTable.COLUMN_ID +
				" = " + "tr." + RelationTable.COLUMN_MEDICINE + " AND ta." + AlarmTable.COLUMN_ID +
				" = " + "tr." + RelationTable.COLUMN_ALARM;
		
		c = db.rawQuery(selectQueryAlarm, null);
		if (c != null) {
			c.moveToFirst();
		}
		
		Timestamp start = Timestamp.valueOf(c.getString(c.getColumnIndex(AlarmTable.COLUMN_START)));
		Timestamp next = Timestamp.valueOf(c.getString(c.getColumnIndex(AlarmTable.COLUMN_NEXT)));
		Timestamp end = Timestamp.valueOf(c.getString(c.getColumnIndex(AlarmTable.COLUMN_END)));
		
		med.setInterval(c.getInt(c.getColumnIndex(AlarmTable.COLUMN_INTERVAL)));
		med.setIntentID(c.getInt(c.getColumnIndex(AlarmTable.COLUMN_INTENT_ID)));
		med.setStartDateDay(start.getDate());
		med.setStartDateMonth(start.getMonth());
		med.setStartDateYear(start.getYear() + 1900);
		med.setFirstDoseHour(start.getHours());
		med.setFirstDoseMinute(start.getMinutes());
		med.setNextDoseDay(next.getDate());
		med.setNextDoseMonth(next.getMonth());
		med.setNextDoseYear(next.getYear() + 1900);
		med.setNextDoseHour(next.getHours());
		med.setNextDoseMinute(next.getMinutes());
		med.setEndDateDay(end.getDate());
		med.setEndDateMonth(end.getMonth());
		med.setEndDateYear(end.getYear() + 1900);
		
		return med;
	}
	
	public List<Med> getAllMeds() {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Med> meds = new ArrayList<Med>();
		
		String selectQuery = "SELECT * FROM " + MedTable.TABLE_NAME;
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				meds.add(this.getMed(c.getString(c.getColumnIndex(MedTable.COLUMN_NAME))));
			} while (c.moveToNext());
		}
		
		return meds;
	}
	
	public void updateMedAlarmIntentId(String name, int intentId) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQueryAlarm = "SELECT * FROM " + AlarmTable.TABLE_NAME + " ta, " +
				MedTable.TABLE_NAME + " tm, " + RelationTable.TABLE_NAME + " tr WHERE tm." +
				MedTable.COLUMN_NAME + " = '" + name + "'" + " AND tm." + MedTable.COLUMN_ID +
				" = " + "tr." + RelationTable.COLUMN_MEDICINE + " AND ta." + AlarmTable.COLUMN_ID +
				" = " + "tr." + RelationTable.COLUMN_ALARM;
		
		Cursor c = db.rawQuery(selectQueryAlarm, null);
		if (c != null) {
			c.moveToFirst();
		}
		
		ContentValues values = new ContentValues();
		values.put(AlarmTable.COLUMN_INTENT_ID, intentId);
		db.update(AlarmTable.TABLE_NAME, values, 
				AlarmTable.COLUMN_ID + "=" + c.getInt(c.getColumnIndex(AlarmTable.COLUMN_ID)), null);
		db.close();
	}

}
