package psiborg.freespace;

import android.app.Activity;

import psiborg.android5000.GameEngine;
import psiborg.android5000.base.GameObject;

public class FPSCounter extends GameObject {
    String str;
    Activity act;
    public FPSCounter(Activity act) {
        str = "FreeSpace";
        this.act = act;
    }
    @Override
    public void step() {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (act.getActionBar() != null) {
                    act.getActionBar().setTitle(str + " " + (int)(GameEngine.getFPS()));
                }
            }
        });
    }
}
