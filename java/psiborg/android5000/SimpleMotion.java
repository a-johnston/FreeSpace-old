package psiborg.android5000;

import android.util.Log;
import android.view.MotionEvent;

import psiborg.android5000.util.Vector3;
import psiborg.freespace.Sensors;

public class SimpleMotion {
	private static float px, py;
	public static void motion(MotionEvent e) {
		switch (e.getAction()) {
			case (MotionEvent.ACTION_DOWN):
                /*
                Log.i("thing", e.getY()+" :)");
                if (e.getY() > 1200) {
                    Sensors.gyro.add(new Vector3(0,1,0));
                } else if (e.getY() > 600) {
                    Sensors.gyro.add(new Vector3(0,0,1));
                } else {
                    Sensors.gyro.add(new Vector3(1,0,0));
                }
                */
                //Sensors.gyro.add(new Vector3(1,0,0));
				px = e.getX();
				py = e.getY();
				break;
			case (MotionEvent.ACTION_MOVE):
				float x = e.getX();
				float y = e.getY();
				TouchCamera.yaw   -= (px-x)/200;
				TouchCamera.pitch += (y-py)/200;
				px = x;
				py = y;
				break;
		}
	}
}
