package psiborg.android5000;

import android.util.Log;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.MeshData;
import psiborg.android5000.util.Quaternion;
import psiborg.android5000.util.Vector3;

public class Suzanne extends GameObject {
    public static float yaw, pitch, radius = 2.5f;
    public Quaternion rotation;
    private static MeshData obj = null;
    private ColorShader color;
    public Vector3 pos;
    public Suzanne(Vector3 pos) {
        this.pos = pos;
        this.rotation = new Quaternion(.1,0,0);
    }
    @Override
    public void load() {
        if (obj == null) {
            obj = IO.loadObj("suzanne.obj");
        }
        color = new ColorShader(obj);
        shader = color;
        color.position = this.pos.toFloatArray();
    }
    @Override
    public void step() {
        transform.rotation.mult(rotation);
        transform.rotation.toMatrix(color.transform);
        //Matrix.translate(color.transform, this.pos);
    }
}
