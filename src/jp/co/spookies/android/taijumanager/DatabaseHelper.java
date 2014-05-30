package jp.co.spookies.android.taijumanager;

import jp.co.spookies.android.taijumanager.dao.TaiJuDao;
import jp.co.spookies.android.taijumanager.dao.ManDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE TABLE
		db.execSQL(TaiJuDao.CREATE_SQL);
		db.execSQL(ManDao.CREATE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
