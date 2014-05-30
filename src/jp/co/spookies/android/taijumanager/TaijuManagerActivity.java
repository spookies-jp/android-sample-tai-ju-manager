package jp.co.spookies.android.taijumanager;

import jp.co.spookies.android.taijumanager.dao.ManDao;
import jp.co.spookies.android.taijumanager.dao.TaiJuDao;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaijuManagerActivity extends Activity {

	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onResume() {
		super.onResume();

		// Database接続
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		ManDao men = new ManDao(db);
		TaiJuDao taiJues = new TaiJuDao(db);

		// 記録がひとつもないときはグラフボタンを無効化
		((Button) findViewById(R.id.btn_show)).setEnabled(taiJues.exists());
		// 個人情報が入力されていないときは記録ボタンを無効化
		((Button) findViewById(R.id.btn_record)).setEnabled(men.exists());
	}

	@Override
	public void onPause() {
		super.onPause();
		db.close();
	}

	// グラフボタン
	public void onClickShow(View v) {
		startActivity(new Intent(this, ShowActivity.class));
	}

	// 記録ボタン
	public void onClickRecord(View v) {
		startActivity(new Intent(this, RecordingActivity.class));
	}

	// 設定ボタン
	public void onClickSetting(View v) {
		startActivity(new Intent(this, SettingActivity.class));
	}

}