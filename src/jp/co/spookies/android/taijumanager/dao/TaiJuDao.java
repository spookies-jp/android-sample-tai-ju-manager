package jp.co.spookies.android.taijumanager.dao;

import java.util.ArrayList;
import java.util.List;

import jp.co.spookies.android.taijumanager.model.TaiJu;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TaiJuDao {
	public static final String TABLE_NAME = "taiju";
	static final String COLUMN_WEIGHT = "weight";
	static final String COLUMN_DATE = "created";
	static final String COLUMN_MEMO = "memo";
	static final String[] COLUMNS = { COLUMN_WEIGHT, COLUMN_DATE, COLUMN_MEMO };
	public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ COLUMN_DATE + " INTEGER PRIMARY KEY, " + COLUMN_MEMO + " TEXT, "
			+ COLUMN_WEIGHT + " REAL)";

	SQLiteDatabase db;

	public TaiJuDao(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * 全てのTaiJuをリストで取得
	 * 
	 * @return
	 */
	public List<TaiJu> findAll() {
		List<TaiJu> list = new ArrayList<TaiJu>();
		// query生成
		Cursor c = db.query(TABLE_NAME, COLUMNS, null, null, null, null,
				COLUMN_DATE);
		// 1行ずつfetch
		while (c.moveToNext()) {
			TaiJu taiJu = new TaiJu();
			taiJu.setDate(c.getLong(c.getColumnIndex(COLUMN_DATE)));
			taiJu.setWeight(c.getFloat(c.getColumnIndex(COLUMN_WEIGHT)));
			taiJu.setMemo(c.getString(c.getColumnIndexOrThrow(COLUMN_MEMO)));
			list.add(taiJu);
		}

		// cursorのclose
		c.close();

		return list;
	}

	/**
	 * 日付を指定してTaiJuを取得
	 * 
	 * @param date
	 * @return
	 */
	public TaiJu find(long date) {
		// query生成
		Cursor c = db.query(TABLE_NAME, COLUMNS, COLUMN_DATE + " = ?",
				new String[] { String.valueOf(date) }, null, null, COLUMN_DATE);
		TaiJu taiJu = null;
		// 1行だけfetch
		if (c.moveToFirst()) {
			taiJu = new TaiJu();
			taiJu.setDate(c.getLong(c.getColumnIndex(COLUMN_DATE)));
			taiJu.setWeight(c.getFloat(c.getColumnIndex(COLUMN_WEIGHT)));
			taiJu.setMemo(c.getString(c.getColumnIndexOrThrow(COLUMN_MEMO)));
		}

		// cursorのclose
		c.close();

		return taiJu;
	}

	/**
	 * 保存
	 * 
	 * @param taiJu
	 * @return
	 */
	public long save(TaiJu taiJu) {
		if (!taiJu.validate()) {
			// validationチェックにひっかかったら保存しない
			return -1;
		}
		// 値設定
		ContentValues values = new ContentValues();
		values.put(COLUMN_WEIGHT, taiJu.getWeight());
		values.put(COLUMN_MEMO, taiJu.getMemo());
		if (exists(taiJu.getDate())) {
			// データすでに存在するなら更新
			String where = COLUMN_DATE + " = ?";
			String[] arg = { String.valueOf(taiJu.getDate()) };
			return db.update(TABLE_NAME, values, where, arg);
		} else {
			// データがまだないなら挿入
			values.put(COLUMN_DATE, taiJu.getDate());
			return db.insert(TABLE_NAME, null, values);
		}
	}

	/**
	 * 日付で存在チェック
	 * 
	 * @param date
	 * @return
	 */
	public boolean exists(long date) {
		return find(date) != null;
	}

	/**
	 * データの存在チェック
	 * 
	 * @return
	 */
	public boolean exists() {
		return findAll().size() > 0;
	}
}
