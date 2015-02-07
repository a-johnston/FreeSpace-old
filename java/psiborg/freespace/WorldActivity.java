package psiborg.freespace;

import android.app.Activity;
import android.os.Bundle;

import psiborg.android5000.Android5000;
import psiborg.android5000.Suzanne;
import psiborg.android5000.TouchCamera;
import psiborg.android5000.base.Scene;


public class WorldActivity extends Activity {
    Android5000 render;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        render = new Android5000(this);

        Sensors sensors = new Sensors(this);

        Scene scene = new Scene();
        scene.add(new TouchCamera());
        scene.add(new Suzanne());
        render.setScene(scene);
    }


}
