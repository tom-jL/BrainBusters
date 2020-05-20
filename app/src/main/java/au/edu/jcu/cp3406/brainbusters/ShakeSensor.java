package au.edu.jcu.cp3406.brainbusters;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeSensor implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 1.5f;
    private static final int SHAKE_TIME = 500;
    private static final int SHAKE_TIMEOUT = 3000;

    private OnShakeListener onShakeListener;
    private long shakeTimeStamp;
    private int shakeCount;

    public void setOnShakeListener(OnShakeListener listener){
        this.onShakeListener = listener;
    }

    public interface OnShakeListener {
        void onShake(int count) throws InstantiationException, IllegalAccessException;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (onShakeListener != null){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ );

            if(gForce > SHAKE_THRESHOLD){
                final long now = System.currentTimeMillis();
                if (shakeTimeStamp + SHAKE_TIME > now){
                    return;
                }

                if(shakeTimeStamp + SHAKE_TIMEOUT < now){
                    shakeCount = 0;
                }

                shakeTimeStamp = now;
                shakeCount++;

                try {
                    onShakeListener.onShake(shakeCount);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
