package psiborg.freespace;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Sensors implements SensorEventListener {
    private SensorManager man;
    public Sensors(Context context) {
        man  = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Log.e("sensors", man.toString());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
