package jp.co.spookies.android.pedometer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * 歩数と時間をカウントするサービス
 * 
 */
public class Pedometer extends Service implements SensorEventListener {
	public static final String STEP = "step";
	public static final String WALK_TIME = "time";
	private int step = 0;
	private int walkTime = 0;
	private long startTime = 0;
	private SensorManager sensorManager;
	private NotificationManager notificationManager;
	private boolean isStepping;
	private float accel;

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	private final IPedometerService.Stub binder = new IPedometerService.Stub() {
		public int getStep() throws RemoteException {
			// 歩数
			return step;
		}

		public int getWalkTime() throws RemoteException {
			if (startTime == 0) {
				startTime = System.currentTimeMillis();
			}
			int _t = (int) ((System.currentTimeMillis() - startTime) / 1000);
			// 起動時間
			return walkTime + _t;
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		isStepping = false;
		accel = 0;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		if (intent == null) {
			return START_NOT_STICKY;
		}
		Bundle extras = intent.getExtras();
		if (extras != null) {
			// 歩数初期化
			step = extras.getInt(STEP);
			// 時間初期化
			walkTime = extras.getInt(WALK_TIME);
		}
		startTime = System.currentTimeMillis();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (sensor != null) {
			// センサー登録
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}

		// Notification設定
		Notification notification = new Notification(R.drawable.pedo_icon01,
				"start", System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, PedometerActivity.class), 0);
		notification.setLatestEventInfo(this, getText(R.string.app_name),
				getText(R.string.app_name), contentIntent);
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notificationManager.notify(R.string.app_name, notification);

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (sensorManager != null) {
			// センサー解除
			sensorManager.unregisterListener(this);
		}
		// Notification削除
		notificationManager.cancel(R.string.app_name);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// 端末の加速度
		float _accel = event.values[0] * event.values[0] + event.values[1]
				* event.values[1] + event.values[2] * event.values[2];
		accel = accel * 0.1f + _accel * 0.9f;
		if (!isStepping && accel > 120.0f) {
			// 端末が上に移動
			isStepping = true;
		} else if (isStepping && accel < 70.0f) {
			// 端末が下に移動
			step++;
			isStepping = false;
		}
	}

}
