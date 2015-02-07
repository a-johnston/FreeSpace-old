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
    public static float yaw, pitch, radius = 2.5f;
    private Camera cam;
    @Override
    public void load() {
        cam = new Camera(
                new float[]{3.0f,  3.0f,  3.0f},
                new float[]{0.0f,  0.0f,  0.0f},
                new float[]{0.0f,  0.0f,  1.0f},
                70f,.1f,100f);
        cam.setMain();
    }
    @Override
    public void step() {
        //Quaternion q = new Quaternion(Sensors.euler.x, Sensors.euler.y, 0.0);
        //Vector3 up   = q.up().normalized();
        ColorShader.lightDir = new float[]{ (float)(Math.cos(GameEngine.time)*3), 0, (float)(Math.sin(GameEngine.time)*3) };
        cam.updateLook(new float[]{0,0,0},
                       new float[]{(float)(Math.cos(Sensors.euler.x)*Math.cos(-Sensors.euler.y)*radius),
                                   (float)(Math.sin(-Sensors.euler.y)*radius),
                                   (float)(Math.sin(Sensors.euler.x)*Math.cos(-Sensors.euler.y)*radius)},
                       new float[]{0,1,0});
        //cam.look = Sensors.rotationMatrix;
        //cam.updateMVP();

        //Quaternion q = new Quaternion(Sensors.euler.x, Sensors.euler.y, 0.0);
        //Log.i("l",Sensors.euler.toString());
        //cam.updateLook(q.forward().normalized().mult(-radius), Vector3.zero, q.up().normalized());
    }
    @Override
    public void unload() {
        if (Camera.active.equals(cam)) {
            Camera.active = null;
        }
    }
}
