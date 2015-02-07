package psiborg.freespace;

import psiborg.android5000.util.Vector2;
import psiborg.android5000.util.Vector3;

public class Blob {
    public Vector3 worldPos, camPos;
    public Vector2 screenPos, oldPos;
    public Blob() {
        worldPos  = new Vector3();
        camPos    = new Vector3();
        screenPos = new Vector2();
        oldPos    = new Vector2();
    }
    public void updateScreenPos(Vector2 v) {
        oldPos    = screenPos;
        screenPos = v;
    }
}
