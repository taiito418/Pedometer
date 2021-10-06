package jp.co.spookies.android.pedometer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 歩数を表示するView
 * 
 */
public class StepView extends LinearLayout {
	public final static int DIGIT = 8;
	public final static int[] numIds = { R.drawable.count_0,
			R.drawable.count_1, R.drawable.count_2, R.drawable.count_3,
			R.drawable.count_4, R.drawable.count_5, R.drawable.count_6,
			R.drawable.count_7, R.drawable.count_8, R.drawable.count_9 };
	private View[] digitView;

	public StepView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setOrientation(HORIZONTAL);

		// 左マージン
		View _emptyLeft = new View(context);
		_emptyLeft.setLayoutParams(new LayoutParams(0, 0, 60));
		addView(_emptyLeft);

		// 歩数を表示する領域
		digitView = new View[DIGIT];
		for (int i = 0; i < DIGIT; i++) {
			View _v = new View(context);
			_v.setLayoutParams(new LayoutParams(0, LayoutParams.FILL_PARENT, 45));
			_v.setBackgroundResource(numIds[0]);
			addView(_v);
			digitView[i] = _v;
		}

		// 右マージン
		View _emptyRight = new View(context);
		_emptyRight.setLayoutParams(new LayoutParams(0, 0, 60));
		addView(_emptyRight);
	}

	/**
	 * 歩数を設定
	 * 
	 * @param step
	 */
	public void setStep(int step) {
		int _base = 10;
		// 各桁ごとのViewに数字の画像を設定
		for (int i = 0; i < DIGIT; i++) {
			digitView[DIGIT - i - 1]
					.setBackgroundResource(numIds[step % _base]);
			step /= _base;
		}
	}

}
