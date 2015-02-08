package psiborg.android5000.base;

import java.util.LinkedList;
import java.util.Queue;

public class Scene {
    public static LinkedList<GameObject> queue = new LinkedList<GameObject>();
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
        while (!queue.isEmpty()) {
            this.add(queue.remove());
        }
        for (GameObject o : list) {
            o.step();
        }
    }
    public void draw(float[] mvp) {
        for (GameObject o : list) {
            if (o.shader != null) {
                o.draw(mvp);
            }
        }
    }
    public static void newInstance(GameObject o) {
        queue.add(o);
    }
}
