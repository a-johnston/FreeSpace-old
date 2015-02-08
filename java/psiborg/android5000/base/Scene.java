package psiborg.android5000.base;

import java.util.LinkedList;

public class Scene {
    private boolean loaded;
    private LinkedList<GameObject> list;
    public Scene() {
        list = new LinkedList<GameObject>();
        loaded = false;
    }
    public void add(GameObject o) {
        list.add(o);
        if (loaded) o.load();
    }
    public void remove(GameObject o) {
        list.remove(o);
        if (loaded) o.unload();
    }
    public boolean contains(GameObject o) {
        return list.contains(o);
    }
    public void load() {
        for (GameObject o : list) {
            o.load();
        }
        loaded = true;
    }
    public void unload() {
        for (GameObject o : list) {
            o.unload();
        }
        loaded = false;
    }
    public void step() {
        for (GameObject o : list) {
            o.step();
        }
    }
    public void draw(float[] mvp) {
        for (GameObject o : list) {
            if (o.shader != null) {
                o.shader.draw(mvp);
            }
        }
    }
}
