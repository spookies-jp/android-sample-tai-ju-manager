package jp.co.spookies.android.taijumanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.spookies.android.taijumanager.dao.TaiJuDao;
import jp.co.spookies.android.taijumanager.model.TaiJu;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecordingActivity extends Activity {

	private TaiJuDao taiJueDao;
	private SQLiteDatabase db;
	public static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recording);
	}

	@Override
	public void onResume() {
		super.onResume();

		// Database接続
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		taiJueDao = new TaiJuDao(db);

		Date date = new Date();
		try {
			// 時間、秒をゼロにする
			date = format.parse(format.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		TaiJu taiJu = taiJueDao.find(date.getTime());
		// 今日の体重がすでに記録されていたらそれを表示
		if (taiJu != null) {
			((EditText) findViewById(R.id.edit_weight)).setText(String
					.valueOf(taiJu.getWeight()));
			((EditText) findViewById(R.id.edit_memo)).setText(taiJu.getMemo());
		}

		// 日付の表示
		TextView _txv = (TextView) findViewById(R.id.txv_id);
		_txv.setText(format.format(date));
	}

	@Override
	public void onPause() {
		super.onPause();
		db.close();
	}

	/**
	 * 保存ボタン
	 * 
	 * @param v
	 */
	public void onSaveClicked(View v) {
		try {
			// 入力値取得
			TaiJu taiJu = new TaiJu();
			taiJu.setDate(format
					.parse(((TextView) findViewById(R.id.txv_id)).getText()
							.toString()).getTime());
			taiJu.setWeight(Float
					.parseFloat(((EditText) findViewById(R.id.edit_weight))
							.getText().toString()));
			taiJu.setMemo(((EditText) findViewById(R.id.edit_memo)).getText()
					.toString());
			// 保存
			if (taiJueDao.save(taiJu) < 0) {
				throw new Exception("could not save TaiJu");
			}
			Toast.makeText(this, "保存しました", Toast.LENGTH_LONG).show();

			// 保存に成功したらアクティビティを閉じる
			finish();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "保存できませんでした", Toast.LENGTH_LONG).show();
		}
	}
}