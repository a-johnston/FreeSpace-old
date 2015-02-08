package psiborg.android5000.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import psiborg.android5000.GameEngine;

public class IO {
	public static MeshData loadObj(String filename) {
		MeshData mesh = new MeshData();
		ArrayList<Vector3> verts = new ArrayList<Vector3>();
		ArrayList<Vector3> cols  = new ArrayList<Vector3>();
		ArrayList<Integer> point = new ArrayList<Integer>();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(GameEngine.assetman.open(filename)));
			String s = in.readLine();
			while (s != null) {
				String[] data = s.split(" ");
				if (data[0].equals("v") == true) {
					mesh.points.add(new Vector3(Double.parseDouble(data[1]),
                                          Double.parseDouble(data[2]),
                                          Double.parseDouble(data[3])));
				} else  if (data[0].equals("c") == true) {
					mesh.color.add(new Vector3(Double.parseDouble(data[1]),
                                         Double.parseDouble(data[2]),
                                         Double.parseDouble(data[3])));
				} else  if (data[0].equals("f") == true) {
					mesh.order.add(new IVector3(Integer.parseInt(data[1])-1,
					                            Integer.parseInt(data[2])-1,
					                            Integer.parseInt(data[3])-1));
				}
				s = in.readLine();
			}
			in.close();
			
		} catch (IOException e) {
            Log.e("loadObj","Could not open file: "+filename);
        }

		mesh.normals = MeshData.getNormals(mesh.points, mesh.order);
		return mesh;
	}
	public static String readFile(String filename) {
		String r = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(GameEngine.assetman.open(filename)));
			String s = in.readLine();
			while (s != null) {
				r += s + "\n";
				s = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			//nothing doing
		}
		return r;
	}
}
