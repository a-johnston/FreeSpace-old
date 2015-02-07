package psiborg.freespace;

import android.app.Activity;
import android.os.Bundle;

import psiborg.android5000.Android5000;
import psiborg.android5000.Suzanne;
import psiborg.android5000.TouchCamera;
import psiborg.android5000.base.Scene;
import psiborg.android5000.util.Vector3;


public class WorldActivity extends Activity {
    Android5000 render;
    Sensors sensors;
    CameraInput camin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        render = new Android5000(this);
        camin  = new CameraInput(this);

        sensors = new Sensors(this);
        sensors.start();

        Scene scene = new Scene();
        scene.add(new TouchCamera());
        scene.add(new Suzanne(new Vector3(5,0,5)));
        scene.add(new Suzanne(new Vector3(-5,0,5)));
        scene.add(new Suzanne(new Vector3(5,0,-5)));
        scene.add(new Suzanne(new Vector3(-5,0,-5)));
        render.setScene(scene);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensors.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensors.stop();
    }
}
