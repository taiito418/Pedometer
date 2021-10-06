package jp.co.spookies.android.pedometer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 時間を表示するためのView
 * 
 */
public class TimerView extends LinearLayout {
	public final static int DIGIT = 6;
	public final static int[] numIds = { R.drawable.time_0, R.drawable.time_1,
			R.drawable.time_2, R.drawable.time_3, R.drawable.time_4,
			R.drawable.time_5, R.drawable.time_6, R.drawable.time_7,
			R.drawable.time_8, R.drawable.time_9 };
	private View[] digitView;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setOrientation(HORIZONTAL);

		// 左マージン
		View _emptyLeft = new View(context);
		_emptyLeft.setLayoutParams(new LayoutParams(0, 0, 125));
		addView(_emptyLeft);

		// 時間を表示する領域
		digitView = new View[DIGIT];
		for (int i = 0; i < DIGIT; i++) {
			View _v = new View(context);
			_v.setLayoutParams(new LayoutParams(0, LayoutParams.FILL_PARENT, 48));
			_v.setBackgroundResource(numIds[0]);
			addView(_v);
			digitView[i] = _v;

			// 2桁ごとにコロンを表示
			if (i % 2 == 1 && i != DIGIT - 1) {
				View _colon = new View(context);
				_colon.setLayoutParams(new LayoutParams(0,
						LayoutParams.FILL_PARENT, 10));
				_colon.setBackgroundResource(R.drawable.time_colon);
				addView(_colon);
			}
		}

		// 右マージン
		View _emptyRight = new View(context);
		_emptyRight.setLayoutParams(new LayoutParams(0, 0, 47));
		addView(_emptyRight);
	}

	/**
	 * 時間を設定
	 * 
	 * @param time
	 */
	public void setTime(int time) {
		// 各桁ごとに数字の画像を設定
		for (int i = 0; i < DIGIT; i++) {
			int _base = 10;

			// 60進数
			if (i == 1 || i == 3) {
				_base = 6;
			}
			digitView[DIGIT - i - 1]
					.setBackgroundResource(numIds[time % _base]);
			time /= _base;
		}
	}

}
