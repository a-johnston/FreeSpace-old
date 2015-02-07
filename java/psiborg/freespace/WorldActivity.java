package psiborg.freespace;

import android.app.Activity;
import android.os.Bundle;

import java.util.Random;

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
        Random r = new Random();
        for (int i=0; i<50; i++) {
            Suzanne s = new Suzanne(new Vector3(r.nextDouble()*50-25, r.nextDouble()*50-25, r.nextDouble()*50-25));
            s.transform.rotation.set(r.nextDouble()*2*Math.PI, r.nextDouble()*2*Math.PI, r.nextDouble()*2*Math.PI);
            scene.add(s);
        }
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
