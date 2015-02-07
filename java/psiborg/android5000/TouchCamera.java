package psiborg.android5000;

import android.os.Debug;
import android.util.Log;

import psiborg.android5000.base.Camera;
import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.Matrix;
import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Vector3;
import psiborg.freespace.Sensors;

public class TouchCamera extends GameObject {
    public static TouchCamera instance;
    public double speed;
    private Vector3 look;
    private Camera cam;
    @Override
    public void load() {
        instance = this;
        look = new Vector3();
        cam = new Camera(
                new float[]{3.0f,  3.0f,  3.0f},
                new float[]{0.0f,  0.0f,  0.0f},
                new float[]{0.0f,  0.0f,  1.0f},
                70f,.1f,100f);
        cam.setMain();
    }
    @Override
    public void step() {
        look.set(Math.cos(Sensors.euler.x)*Math.cos(-Sensors.euler.y), Math.sin(-Sensors.euler.y), Math.sin(Sensors.euler.x)*Math.cos(-Sensors.euler.y));
        transform.position.add(Vector3.mult(look, speed));
        cam.updateLook(new float[]{(float)transform.position.x,(float)transform.position.y,(float)transform.position.z},
                       new float[]{(float)(transform.position.x + look.x),
                                   (float)(transform.position.y + look.y),
                                   (float)(transform.position.z + look.z)},
                       new float[]{0,1,0});
    }
    @Override
    public void unload() {
        if (Camera.active.equals(cam)) {
            Camera.active = null;
        }
    }
}
