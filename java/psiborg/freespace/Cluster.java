package psiborg.freespace;

import java.util.ArrayList;
import java.util.Collection;

import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.MeshData;
import psiborg.android5000.util.Vector3;

public class Cluster extends GameObject {
    public float[] color;
    private ArrayList<Blob> blobs;
    private PointCloudShader s;
    private boolean active;
    public Cluster(Vector3 pos) {
        blobs = new ArrayList<Blob>();
        transform.position.set(pos);
    }
    public void addPoints(Collection<Blob> l) {
        blobs.addAll(l);
    }
    public void load() {
        active = true;
        s = new PointCloudShader(new float[]{1.0f,1.0f,1.0f,1.0f}, buildCloud());
        shader = s;
        unifiedChangePosition(transform.position);
    }
    public void unload() {
        active = false;
    }
    public void setActive(boolean b) {
        active = b;
        if (active) {
            shader = s;
        } else {
            shader = null;
        }
    }
    public boolean getActive() {
        return active;
    }
    public void unifiedChangePosition(Vector3 pos) {
        this.transform.position.set(pos);
        s.updatePosition(pos);
    }
    private float[] buildCloud() {
        float[] data = new float[blobs.size()*3];
        int i = 0;
        for (Blob b : blobs) {
            data[i*3]   = (float)b.worldPos.x;
            data[i*3+1] = (float)b.worldPos.y;
            data[i*3+2] = (float)b.worldPos.z;
            i++;
        }
        return data;
    }
    private Blob[] toBlobs() {
        Blob[] r = new Blob[blobs.size()];
        int i=0;
        for (Blob b : blobs) {
            r[i++] = b;
        }
        return r;
    }
}
