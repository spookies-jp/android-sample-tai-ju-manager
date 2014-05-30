package jp.co.spookies.android.taijumanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.spookies.android.taijumanager.model.TaiJu;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class GraphView extends View {
	private Paint paint;
	private Paint paint_date;
	private List<Plot> plots = null;
	private float maxWeight, minWeight;
	private long maxTime, minTime;
	private int width;
	public static final int LEN_DAY = 60;
	public static final int DIV_HEIGHT = 30;
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"M/dd");

	public GraphView(Context context, List<TaiJu> taiJuList, int minWidth) {
		super(context);

		// paint初期化
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		paint.setColor(Color.rgb(255, 100, 170));
		paint_date = new Paint();
		paint_date.setAntiAlias(true);
		paint_date.setTextSize(18.0f);
		paint_date.setColor(Color.BLACK);
		setData(taiJuList);
		// widthはデータの長さから計算
		width = Math.max(minWidth,
				(int) ((maxTime - minTime) / 1000 / 60 / 60 / 24) * LEN_DAY);
		setLayoutParams(new LayoutParams(width, LayoutParams.FILL_PARENT));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.rgb(255, 255, 255));
		if (plots == null) {
			return;
		}
		int height = getHeight() - DIV_HEIGHT;
		int nx = (int) ((maxTime - minTime) / 1000 / 60 / 60 / 24) + 1;
		int ny = (int) Math.max(maxWeight - minWeight, 1.0);
		float dx = Math.max(width / nx, LEN_DAY);
		float dy = (float) height / ny;
		float _x, _y;

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(minTime);

		// 目盛
		for (int i = 0; i < nx; i++) {
			canvas.drawLine(dx * i, 0, dx * i, height, paint);
			canvas.drawText(
					(c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DATE),
					dx * i, height + DIV_HEIGHT / 2, paint_date);
			canvas.drawText(dateFormat.format(new Date(c.getTimeInMillis())),
					dx * i, height + DIV_HEIGHT / 2, paint_date);
			c.add(Calendar.DATE, 1);
		}
		for (int i = 0; i < ny; i++) {
			canvas.drawLine(0, dy * i, width, dy * i, paint);
		}

		// x軸とy軸の描画
		paint.setStrokeWidth(4.0f);
		canvas.drawLines(new float[] { 2, 0, 2, height - 2, 2, height - 2,
				width - 2, height - 2 }, paint);

		// プロット用のpaint
		paint.setColor(Color.rgb(0, 0, 25));
		PointF point = null;
		paint.setStrokeWidth(2.0f);

		// プロット
		for (Plot p : plots) {
			_x = (p.time - minTime / 1000 / 60 / 60 / 24) * dx;
			_y = (maxWeight - p.weight) * dy;
			// 点を打つ
			canvas.drawCircle(_x, _y, 5.0f, paint);

			// 一つ目の点は線で結ばない
			if (point == null) {
				point = new PointF(_x, _y);
			}
			// 点を線でつなぐ
			canvas.drawLine(_x, _y, point.x, point.y, paint);
			point = new PointF(_x, _y);
		}
	}

	/**
	 * 体重のデータを設定
	 * 
	 * @param taiJuList
	 */
	public void setData(List<TaiJu> taiJuList) {
		long t = 0;
		float weight;
		plots = new ArrayList<Plot>();
		maxWeight = 0;
		minWeight = Float.MAX_VALUE;
		maxTime = 0;
		minTime = Long.MAX_VALUE;

		for (TaiJu taiJu : taiJuList) {
			t = taiJu.getDate();
			weight = taiJu.getWeight();

			// 体重の最大最小も探す
			if (weight > maxWeight) {
				maxWeight = weight;
			}
			if (weight < minWeight) {
				minWeight = weight;
			}

			// 時刻をプロットするx座標の値に変換
			int _x = (int) (t / 1000 / 60 / 60 / 24);
			if (t > maxTime) {
				maxTime = t;
			}
			if (t < minTime) {
				minTime = t;
			}
			plots.add(new Plot(_x, weight));
		}
		maxWeight += 1.0f;
		minWeight -= 1.0f;
	}

	/**
	 * グラフが表示できる最大の体重を取得
	 * 
	 * @return
	 */
	public float getMaxWeight() {
		return maxWeight;
	}

	/**
	 * グラフが表示できる最小の体重を取得
	 * 
	 * @return
	 */
	public float getMinWeight() {
		return minWeight;
	}

	class Plot {
		long time;
		float weight;

		Plot(long time, float weight) {
			this.time = time;
			this.weight = weight;
		}
	}
}
