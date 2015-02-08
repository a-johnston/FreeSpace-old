package psiborg.android5000.util;

import java.util.ArrayList;

import psiborg.freespace.Cluster;

public class MeshData {
    public ArrayList<Vector3> points, normals, color;
    public ArrayList<IVector3> order;
    public ArrayList<Vector2> uv;
//	public float[] points;
//	public float[] normals;
//	public float[] color;
//	public float[] uv;
//	public int[]   order;
    public float[] getPoints() {
        float[] r = new float[points.size()*3];
        int i=0;
        for (Vector3 v : points) {
            r[i++] = (float)v.x;
            r[i++] = (float)v.y;
            r[i++] = (float)v.z;
        }
        return r;
    }
    public float[] getNormals() {
        float[] r = new float[normals.size()*3];
        int i=0;
        for (Vector3 v : normals) {
            r[i++] = (float)v.x;
            r[i++] = (float)v.y;
            r[i++] = (float)v.z;
        }
        return r;
    }
    public float[] getColor() {
        float[] r = new float[color.size()*3];
        int i=0;
        for (Vector3 v : color) {
            r[i++] = (float)v.x;
            r[i++] = (float)v.y;
            r[i++] = (float)v.z;
        }
        return r;
    }
    public int[] getOrder() {
        int[] r = new int[order.size()*3];
        int i=0;
        for (IVector3 v : order) {
            r[i++] = v.x;
            r[i++] = v.y;
            r[i++] = v.z;
        }
        return r;
    }
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
    public static ArrayList<Vector3> getNormals(ArrayList<Vector3> points, ArrayList<IVector3> order) {
        ArrayList<Vector3> norms = new ArrayList<Vector3>();
        for (int i=0; i<points.size(); i++) {
            norms.add(new Vector3());
        }
        for (IVector3 i : order) {
            Vector3 n = getNormal(points.get(i.x),points.get(i.y),points.get(i.z));
            norms.get(i.x).add(n);
            norms.get(i.y).add(n);
            norms.get(i.z).add(n);
        }
        for (Vector3 n : norms) n.normalized();
        return norms;
    }
    public static Vector3 getNormal(Vector3 p1, Vector3 p2, Vector3 p3) {
        return Vector3.normalized(Vector3.cross(Vector3.sub(p2, p1), Vector3.sub(p3, p1)));
    }
    public static MeshData voxelSurface(Vector3 base, char[][][] vox) {
        MeshData mesh = new MeshData();
        for (int i=0; i<=vox.length; i++) {
            for (int j=0; j<=vox.length; j++) {
                for (int k=0; k<=vox.length; k++) {
                    mesh.points.add(Vector3.add(base, new Vector3(i*Cluster.w/Cluster.sub,j*Cluster.w/Cluster.sub,k*Cluster.w/Cluster.sub)));
                    if ((i != vox.length) && (j != vox.length) && (k != vox.length)) {
                        boolean[] s = sides(vox, i, j, k);
                        int cbl = getSharedPoint(i, j, k);
                        int ctl = cbl + 1;
                        int fbl = cbl + Cluster.sub;
                        int ftl = ctl + Cluster.sub;
                        int cbr = cbl + Cluster.sub * Cluster.sub;
                        int ctr = ctl + Cluster.sub * Cluster.sub;
                        int fbr = fbl + Cluster.sub * Cluster.sub;
                        int ftr = ftl + Cluster.sub * Cluster.sub;
                        //left (all l)
                        if (s[0]) {
                            mesh.order.add(new IVector3(ctl, ftl, fbl));
                            mesh.order.add(new IVector3(cbl, ctl, ftl));
                        }
                        //right (all r)
                        if (s[1]) {
                            mesh.order.add(new IVector3(ftr, ctr, fbr));
                            mesh.order.add(new IVector3(fbr, ctr, cbr));
                        }
                        //front (all c)
                        if (s[2]) {
                            mesh.order.add(new IVector3(ctr, ctl, cbr));
                            mesh.order.add(new IVector3(cbr, ctl, cbl));
                        }
                        //back (all f)
                        if (s[3]) {
                            mesh.order.add(new IVector3(ftl, ftr, fbr));
                            mesh.order.add(new IVector3(fbl, ftl, fbr));
                        }
                        //bottom (all b)
                        if (s[4]) {
                            mesh.order.add(new IVector3(cbr, cbl, cbl));
                            mesh.order.add(new IVector3(fbl, fbr, cbr));
                        }
                        //top (all t)
                        if (s[5]) {
                            mesh.order.add(new IVector3(ctl, ctr, ftl));
                            mesh.order.add(new IVector3(ctr, ftr, ftl));
                        }
                    }
                }
            }
        }
        return mesh;
    }
    private static boolean[] sides(char[][][] vox, int i, int j, int k) {
        boolean[] r = new boolean[6]; //[-i, +i, -j, +j, -k, +k]
        if (i==0 || vox[i-1][j][k]==0)            r[0] = true;
        if (i==vox.length-1 || vox[i+1][j][k]==0) r[1] = true;

        if (j==0 || vox[i][j-1][k]==0)            r[2] = true;
        if (j==vox.length-1 || vox[i][j+1][k]==0) r[3] = true;

        if (k==0 || vox[i][j][k-1]==0)            r[4] = true;
        if (k==vox.length-1 || vox[i][j][k+1]==0) r[5] = true;

        return r;
    }
    private static int getSharedPoint(int i, int j, int k) {
        return k + Cluster.sub*j + Cluster.sub*Cluster.sub*i;
    }
}
