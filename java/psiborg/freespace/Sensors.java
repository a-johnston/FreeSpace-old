package psiborg.freespace;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import psiborg.android5000.util.Vector3;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Sensors implements SensorEventListener {
    public static Vector3 euler = new Vector3();
    public static float[] rotationMatrix;

    private static final Vector3 magic = new Vector3(2,1,2);
    private SensorManager mSensorManager;
    private float[] rawAngles;

    public Sensors(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        rotationMatrix = new float[16];
        rawAngles      = new float[4];
    }

    public void start() {
        mSensorManager.registerListener(
                this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_GAME );
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        // It is good practice to check that we received the proper sensor event
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            // Convert the rotation-vector to a 4x4 matrix.
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager
                    .remapCoordinateSystem(rotationMatrix,
                            SensorManager.AXIS_X, SensorManager.AXIS_Z,
                            rotationMatrix);
            SensorManager.getOrientation(rotationMatrix, rawAngles);
            euler.set(rawAngles).mult(magic);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //IMPLYING I DO ANYTHING HERE
    }
}