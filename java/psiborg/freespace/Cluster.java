package psiborg.freespace;

import java.util.ArrayList;
import java.util.Collection;

import psiborg.android5000.Android5000;
import psiborg.android5000.ColorShader;
import psiborg.android5000.GameEngine;
import psiborg.android5000.base.GameObject;
import psiborg.android5000.util.MeshData;
import psiborg.android5000.util.Vector3;

public class Cluster extends GameObject {
    public static final int sub = 5;
    public static final double w = 1.0;

    private static int worldSize = 20;
    private static Cluster[][][] chunks;


    private ColorShader s;
    private MeshData mesh;
    private char[][][] map;
    private boolean active;
    public Cluster() {
        map = new char[sub][sub][sub];
        active = false;
        if (chunks == null) {
            chunks = new Cluster[worldSize][worldSize][worldSize];
            chunks[0][0][0] = this;
            this.transform.position = Vector3.zero;
            for (int i=0; i<worldSize; i++) {
                for (int j=0; j<worldSize; j++) {
                    for (int k=0; k<worldSize; k++) {
                        if (i+j+k != 0) {
                            Cluster c = new Cluster();

                            c.transform.position = new Vector3(i*w,j*w,k*w);
                            chunks[i][j][k] = c;
                        }
                    }
                }
            }
        }
    }
    public void load() {
        active = true;
    }
    public void unload() {
        active = false;
    }
    public void setActive(boolean b) {
        active = b;
        if (active) {
            if (mesh == null) {
                mesh = MeshData.voxelSurface(transform.position, map);
                s = new ColorShader(mesh);
            }
        }
    }
    public boolean getActive() {
        return active;
    }
    @Override
    public void draw(float[] camera) {
        if (active) {
            s.draw(camera);
        }
    }
}
