package psiborg.freespace;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import psiborg.android5000.Android5000;
import psiborg.android5000.Suzanne;
import psiborg.android5000.TouchCamera;
import psiborg.android5000.base.Scene;
import psiborg.android5000.util.Vector3;


public class WorldActivity extends Activity {
    Android5000 render;
    Sensors sensors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("load", "DOES THIS RUN");
        super.onCreate(savedInstanceState);
        render = new Android5000(this);

        sensors = new Sensors(this);
        sensors.start();

        Scene scene = new Scene();
        scene.add(new TouchCamera());
        scene.add(new FPSCounter(this));
        Random r = new Random();
        Log.i("load", "start pointcloud load");
        for (int i=0; i<10; i++) {
            Suzanne s = new Suzanne(new Vector3(r.nextDouble()*20-10, r.nextDouble()*20-10, r.nextDouble()*20-10));
            s.transform.rotation.set(r.nextDouble()*2*Math.PI, r.nextDouble()*2*Math.PI, r.nextDouble()*2*Math.PI).normalize();
            scene.add(s);

            Cluster c = new Cluster(new Vector3(r.nextDouble()*20-10, r.nextDouble()*20-10, r.nextDouble()*20-10));
            c.addPoints(makeRandomBlobs(500, 10));
            scene.add(c);
        }
        Log.i("load", "end pointcloud load");
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

    private ArrayList<Blob> makeRandomBlobs(int num, double grouping) {
        ArrayList<Blob> blobs = new ArrayList<Blob>();
        Random r = new Random();
        for (int i=0; i<num; i++) {
            Blob b = new Blob();
            b.worldPos = new Vector3(r.nextDouble()*2*grouping-grouping, r.nextDouble()*2*grouping-grouping, r.nextDouble()*2*grouping-grouping);
            blobs.add(b);
        }
        return blobs;
    }
}
