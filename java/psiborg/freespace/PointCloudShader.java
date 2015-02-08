package psiborg.freespace;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import psiborg.android5000.base.Shader;
import psiborg.android5000.util.IO;
import psiborg.android5000.util.Vector3;

public class PointCloudShader extends Shader {
    private  Vector3 pos;
    private static final String vert = "pointcloud_vert";
    private static final String frag = "pointcloud_frag";
    private int s, pointHandle, transHandle, matHandle, colorHandle;
    private FloatBuffer pointBuffer;
    private float[] color;
    private static final byte DIM 	 = 3;
    private static final byte stride = DIM*4;
    public PointCloudShader(float[] color, float[] buffer) {
        s = instance(IO.readFile(vert), IO.readFile(frag));
        pointHandle = GLES20.glGetAttribLocation(s, "position");
        transHandle = GLES20.glGetAttribLocation(s, "translate");
        matHandle   = GLES20.glGetAttribLocation(s, "matrix");
        colorHandle = GLES20.glGetAttribLocation(s, "color");
        pos = new Vector3();
        this.color = color;

        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length*4);
        bb.order(ByteOrder.nativeOrder());
        pointBuffer = bb.asFloatBuffer();
        pointBuffer.put(buffer);
        pointBuffer.position(0);

        GLES20.glEnableVertexAttribArray(pointHandle);

        GLES20.glVertexAttribPointer(
                pointHandle, DIM,
                GLES20.GL_FLOAT, false,
                0, pointBuffer);

        GLES20.glDisableVertexAttribArray(pointHandle);
    }
    public void updatePosition(Vector3 pos) {
        this.pos.set(pos);
        GLES20.glUniform3fv(transHandle, 1, pos.toFloatArray(), 0);
    }
    @Override
    public void draw(float[] mvpMatrix) {
        if (pointHandle != -1) {
            GLES20.glUseProgram(s);
            GLES20.glEnableVertexAttribArray(pointHandle);
            GLES20.glUniform3fv(GLES20.glGetUniformLocation(s, "color"), 1, color, 0);
            GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(s, "matrix"),
                    1, false, mvpMatrix, 0);
            GLES20.glDrawArrays(GLES20.GL_POINTS, 0, pointBuffer.capacity() / 12);
            GLES20.glDisableVertexAttribArray(pointHandle);
        }
    }
}
