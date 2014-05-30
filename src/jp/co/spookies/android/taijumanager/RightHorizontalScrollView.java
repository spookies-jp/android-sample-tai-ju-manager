package jp.co.spookies.android.taijumanager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class RightHorizontalScrollView extends HorizontalScrollView {
	private boolean isInitialized;

	public RightHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		isInitialized = false;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// 最初に表示するときは一番右にスクロールしておく
		if (!isInitialized) {
			scrollTo(getWidth(), 0);
			isInitialized = true;
		}
		super.onDraw(canvas);
	}

}
