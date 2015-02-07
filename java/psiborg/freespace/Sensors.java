package psiborg.freespace;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import psiborg.android5000.util.Vector3;

public class Sensors implements SensorEventListener {
    public static Vector3 gyro = new Vector3();
    private SensorManager sManager;
    public Sensors(Context context) {
        sManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
    }

    public void start() {
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop() {
        sManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        //>>IMPLYING I DO SOMETHING
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }
        gyro.lerp(new Vector3(event.values).normalized(), .2).normalized();
        Log.i("gyro", gyro.x+" "+gyro.y+" "+gyro.z);
    }
}