package psiborg.freespace;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;

public class Sensors {
    private SensorManager man;
    public Sensors(Context context) {
        man  = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Log.e("sensors", man.toString());
    }
}
