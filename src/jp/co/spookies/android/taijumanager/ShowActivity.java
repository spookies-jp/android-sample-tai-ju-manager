package jp.co.spookies.android.taijumanager;

import jp.co.spookies.android.taijumanager.dao.TaiJuDao;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ShowActivity extends Activity {
	SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show);
	}

	@Override
	public void onResume() {
		super.onResume();

		// Database接続
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		TaiJuDao taiJues = new TaiJuDao(db);

		// グラフ初期化
		GraphView view = new GraphView(this, taiJues.findAll(),
				((WindowManager) getSystemService(WINDOW_SERVICE))
						.getDefaultDisplay().getWidth());
		// 軸線初期化
		DivisionView div = (DivisionView) findViewById(R.id.div_view);
		div.setRange(view.getMaxWeight(), view.getMinWeight());
		LinearLayout l = (LinearLayout) findViewById(R.id.graph_view);
		l.addView(view);
	}

	@Override
	public void onPause() {
		super.onPause();
		db.close();
	}
}