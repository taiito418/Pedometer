package jp.co.spookies.android.pedometer;
import android.location.Location;

interface IPedometerService {
    /**
     * 歩数を取得
     * @return 歩数
     */
    int getStep();
    
    /**
     * 歩いている時間を取得
     * @return 歩いている時間（秒）
     */
    int getWalkTime();
}