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
                TouchCamera.instance.speed = 0.1;
				break;
			case (MotionEvent.ACTION_UP):
				TouchCamera.instance.speed = 0.0;
				break;
		}
	}
}
