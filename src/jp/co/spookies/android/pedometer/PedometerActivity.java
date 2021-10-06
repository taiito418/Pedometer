package jp.co.spookies.android.pedometer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class PedometerActivity extends Activity {
	public static String PEDOMETER_STATE = "pedometer_state";
	private SharedPreferences pref;
	private int step;
	private StepView stepView;
	private TimerView timerView;
	private Button btnStart;
	private Button btnStop;
	private Thread thread;
	private boolean runFlag;
	private Handler handler;
	private int walkTime;
	private Runnable updater = new Runnable() {
		@Override
		public void run() {
			try {
				if (binder != null) {
					// サービスから情報取得
					step = binder.getStep();
					walkTime = binder.getWalkTime();
				}
				// Viewに反映
				stepView.setStep(step);
				timerView.setTime(walkTime);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
	private IPedometerService binder;
	private ServiceConnection connection = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = IPedometerService.Stub.asInterface(service);
		}

		public void onServiceDisconnected(ComponentName name) {
			binder = null;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		pref = PreferenceManager.getDefaultSharedPreferences(this);
		// 各ビュー取得
		stepView = (StepView) findViewById(R.id.step_view);
		timerView = (TimerView) findViewById(R.id.timer_view);
		btnStart = (Button) findViewById(R.id.btn_start);
		btnStop = (Button) findViewById(R.id.btn_stop);

		step = 0;
		handler = new Handler();
		walkTime = 0;
	}

	@Override
	public void onResume() {
		super.onResume();

		// 前回の停止時にサービスが動いていたらバインドする
		if (pref.getBoolean(PEDOMETER_STATE, false)) {
			Intent intent = new Intent(PedometerActivity.this, Pedometer.class);
			bindService(intent, connection, BIND_AUTO_CREATE);

			// 停止ボタンだけ有効に
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);
		}

		if (thread == null) {
			runFlag = true;
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (runFlag) {
						// 画面の更新処理
						handler.post(updater);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			});
			thread.start();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		// サービスが動いてるかを保存
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(PEDOMETER_STATE, btnStop.isEnabled());
		editor.commit();
		if (binder != null) {
			// いったんアンバインドする
			unbindService(connection);
		}

		runFlag = false;
		thread = null;
	}

	/**
	 * サービスの開始
	 * 
	 * @param v
	 */
	public void onStartClicked(View v) {
		// サービスを開始するときは現在の歩数と時間をサービスに渡す
		Intent intent = new Intent(PedometerActivity.this, Pedometer.class);
		intent.putExtra(Pedometer.STEP, step);
		intent.putExtra(Pedometer.WALK_TIME, walkTime);
		startService(intent);

		// サービスとバインド
		bindService(intent, connection, BIND_AUTO_CREATE);

		// 停止ボタンだけ有効に
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
	}

	/**
	 * サービスの停止
	 * 
	 * @param v
	 */
	public void onStopClicked(View v) {
		// サービスをアンバインドして停止
		Intent intent = new Intent(PedometerActivity.this, Pedometer.class);
		unbindService(connection);
		stopService(intent);
		binder = null;

		// 開始ボタンだけ有効に
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}

}