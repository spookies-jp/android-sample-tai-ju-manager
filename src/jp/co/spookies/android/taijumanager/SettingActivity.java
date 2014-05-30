package jp.co.spookies.android.taijumanager;

import java.util.List;

import jp.co.spookies.android.taijumanager.dao.ManDao;
import jp.co.spookies.android.taijumanager.model.Man;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity {

	ManDao men;
	SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
	}

	@Override
	public void onResume() {
		super.onResume();

		// Database接続
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		men = new ManDao(db);
		List<Man> manList = men.findAll();
		// 個人情報が入力されていたらそれを表示
		if (manList.size() > 0) {
			Man man = manList.get(0);
			((EditText) findViewById(R.id.edit_name)).setText(man.getName());
			((EditText) findViewById(R.id.edit_height)).setText(String
					.valueOf(man.getHeight()));
			((EditText) findViewById(R.id.edit_age)).setText(String.valueOf(man
					.getAge()));
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		db.close();
	}

	/*
	 * 保存ボタン
	 */
	public void onSaveClicked(View v) {
		try {
			// 入力データ取得
			Man man = new Man();
			man.setName(((EditText) findViewById(R.id.edit_name)).getText()
					.toString());
			man.setAge(Integer
					.parseInt(((EditText) findViewById(R.id.edit_age))
							.getText().toString()));
			man.setHeight(Integer
					.parseInt(((EditText) findViewById(R.id.edit_height))
							.getText().toString()));
			if (men.save(man) < 0) {
				throw new Exception("could not save TaiJu");
			}
			Toast.makeText(this, "保存しました", Toast.LENGTH_LONG).show();

			// 保存に成功したらアクティビティを終了
			finish();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "保存できませんでした", Toast.LENGTH_LONG).show();
		}
	}

}