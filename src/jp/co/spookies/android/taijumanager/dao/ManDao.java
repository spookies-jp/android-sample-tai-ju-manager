package jp.co.spookies.android.taijumanager.dao;

import java.util.ArrayList;
import java.util.List;

import jp.co.spookies.android.taijumanager.model.Man;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ManDao {
	static final String TABLE_NAME = "man";
	static final String COLUMN_ID = "id";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_AGE = "age";
	static final String COLUMN_HEIGHT = "height";
	static final String[] COLUMNS = { COLUMN_NAME, COLUMN_AGE, COLUMN_HEIGHT };
	public static final String CREATE_SQL = "CREATE TABLE "
			+ TABLE_NAME
			+ " (id INTEGER PRIMARY KEY, name text, age INTEGER, height INTEGER)";

	SQLiteDatabase db;

	public ManDao(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * 全て取得
	 * 
	 * @return
	 */
	public List<Man> findAll() {
		List<Man> list = new ArrayList<Man>();
		// query
		Cursor c = db.query(TABLE_NAME, COLUMNS, null, null, null, null,
				COLUMN_NAME);
		// 1行ずつfetch
		while (c.moveToNext()) {
			Man man = new Man();
			man.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
			man.setAge(c.getInt(c.getColumnIndex(COLUMN_AGE)));
			man.setHeight(c.getInt(c.getColumnIndex(COLUMN_HEIGHT)));
			list.add(man);
		}

		// cursorのclose
		c.close();

		return list;
	}

	/**
	 * 保存
	 * 
	 * @param man
	 * @return
	 */
	public long save(Man man) {
		if (!man.validate()) {
			return -1;
		}
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, man.getName());
		values.put(COLUMN_AGE, man.getAge());
		values.put(COLUMN_HEIGHT, man.getHeight());
		if (exists()) {
			String where = COLUMN_ID + " = ?";
			String[] arg = { "1" };
			return db.update(TABLE_NAME, values, where, arg);
		} else {
			values.put(COLUMN_ID, "1");
			return db.insert(TABLE_NAME, null, values);
		}
	}

	/**
	 * 存在チェック
	 * 
	 * @return
	 */
	public boolean exists() {
		return findAll().size() > 0;
	}
}
