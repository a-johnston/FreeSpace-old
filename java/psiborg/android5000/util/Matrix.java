package psiborg.android5000.util;

public class Matrix {
    public static final float[] identity = new float[]{1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1};
    private float[] m;
    public Matrix() {
        this.set(identity);
    }
    public Matrix(final float[] a) {
        this.set(a);
    }
    public Matrix set(final float[] a) {
        m = a.clone();
        return this;
    }
    public Matrix translate(final Vector3 v) {
        m[3]  += v.x;
        m[7]  += v.y;
        m[11] += v.z;
        return this;
    }
    public static Matrix lookMatrix(Vector3 from, Vector3 to, Vector3 up) {
        float[] f = new float[16];
        android.opengl.Matrix.setLookAtM(f, 0,
                (float) from.x, (float) from.y, (float) from.z,
                (float) to.x,   (float) to.y,   (float) to.z,
                (float) up.x,   (float) up.y,   (float) up.z);
        return new Matrix(f);
    }
}
