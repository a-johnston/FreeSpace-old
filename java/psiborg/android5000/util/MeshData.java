package psiborg.android5000.util;

import psiborg.freespace.Blob;

public class MeshData {
	public float[] points;
	public float[] normals;
	public float[] color;
	public float[] uv;
	public int[]   order;
    public static Vector3[] getNormals(Vector3[] points, int[] order) {
        Vector3[] norms = new Vector3[points.length];
        for (int i=0; i<norms.length; i++) {
            norms[i] = new Vector3(0,0,0);
        }
        for (int i=0; i<order.length; i+=3) {
            Vector3 n = getNormal(points[order[i]],points[order[i+1]],points[order[i+2]]);
            norms[order[i]]   = Vector3.add(norms[order[i]],n);
            norms[order[i+1]] = Vector3.add(norms[order[i+1]],n);
            norms[order[i+2]] = Vector3.add(norms[order[i+2]],n);
        }
        for (int i=0; i<norms.length; i++) norms[i] = Vector3.normalized(norms[i]);
        return norms;
    }
    public static Vector3 getNormal(Vector3 p1, Vector3 p2, Vector3 p3) {
        return Vector3.normalized(Vector3.cross(Vector3.sub(p2, p1), Vector3.sub(p3, p1)));
    }
    public static MeshData buildCloudMesh(Blob[] points, float[] color) {
        float[] p = new float[points.length*3];
        float[] c = new float[points.length*3];
        int[]   o = new int[points.length];
        int i = 0;
        for (Blob b : points) {
            p[i] = (float)b.worldPos.x; p[i+1] = (float)b.worldPos.y; p[i+2] = (float)b.worldPos.z;
            c[i] = color[0];            c[i+1] = color[1];            c[i+2] = color[2];
            o[i] = i;
            i++;
        }
        MeshData r = new MeshData();
        r.points = p;
        r.color  = c;
        r.order  = o;
        return r;
    }
}
