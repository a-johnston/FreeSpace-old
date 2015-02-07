package psiborg.android5000;

import android.util.Log;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.Matrix;
import psiborg.android5000.util.MeshData;
import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Vector3;
import psiborg.freespace.Sensors;

public class Suzanne extends GameObject {
    public static float yaw, pitch, radius = 2.5f;
    public static Quaternion rotation = new Quaternion(new Vector3(0,0,0).normalized(), .1);
    private ColorShader color;
    public Vector3 pos;
    public Suzanne(Vector3 pos) {
        this.pos = pos;
    }
    @Override
    public void load() {
        MeshData obj = IO.loadObj("suzanne.obj");
        Log.i("mesh", obj.points.length+"!");
        color = new ColorShader(obj);
        shader = color;
        color.position = this.pos.toFloatArray();
    }
    @Override
    public void step() {
        ColorShader.lightDir = new float[]{ (float)(Math.cos(GameEngine.time)*3), 0, (float)(Math.sin(GameEngine.time)*3) };
        transform.rotation.mult(rotation);
        transform.rotation.toMatrix(color.transform);
        //Matrix.translate(color.transform, this.pos);
    }
}
