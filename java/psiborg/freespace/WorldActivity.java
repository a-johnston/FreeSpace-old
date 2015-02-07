package psiborg.freespace;

import android.app.Activity;
import android.os.Bundle;

import psiborg.android5000.Android5000;


public class WorldActivity extends Activity {
    Android5000 render;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        render = new Android5000(this);
    }


}
