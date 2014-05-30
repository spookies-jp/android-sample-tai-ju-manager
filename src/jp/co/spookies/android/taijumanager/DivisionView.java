package jp.co.spookies.android.taijumanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DivisionView extends View {
	Paint paint;
	float maxWeight = 0;
	float minWeight = 0;

	public DivisionView(Context context, AttributeSet attr) {
		super(context, attr);
		paint = new Paint();
		paint.setAntiAlias(true);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.rgb(255, 255, 255));
		int height = getHeight() - GraphView.DIV_HEIGHT;
		int width = getWidth();
		int ny = (int) (maxWeight - minWeight);
		int dy = height / ny;
		paint.setColor(Color.rgb(0, 0, 0));
		paint.setTextSize(Math.min(dy / 2, width / 3));
		for (int i = 0; i < ny; i++) {
			float weight = minWeight + i;
			canvas.drawText(String.valueOf(weight), 0, height - dy * i, paint);
		}
	}

	/**
	 * グラフの上限下限を設定
	 * 
	 * @param maxWeight
	 * @param minWeight
	 */
	public void setRange(float maxWeight, float minWeight) {
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
	}
}
