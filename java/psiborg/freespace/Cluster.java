package psiborg.freespace;

import java.util.ArrayList;

import psiborg.android5000.base.GameObject;

public class Cluster extends GameObject {
    public boolean active;
    private float[] color;
    ArrayList<Blob> blobs;

    public Cluster() {
        blobs = new ArrayList<Blob>();
    }
    public void load() {
        active = true;
    }
    public void unload() {
        active = false;
    }
}
