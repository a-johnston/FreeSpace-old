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
    private int s, pointHandle;
    private FloatBuffer pointBuffer;
    private float[] color;
    public PointCloudShader(float[] color) {
        s = instance(IO.readFile(vert), IO.readFile(frag));
        pointHandle = GLES20.glGetAttribLocation(s, "a_vertex");
        updateBuffer(new float[0]);
        pos = new Vector3();
        this.color = color;
        pointBuffer = FloatBuffer.allocate(0);
    }
    public void updateBuffer(float[] buffer) {
        /*
        GLES20.glEnableVertexAttribArray(pointHandle);
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length*4);
        bb.order(ByteOrder.nativeOrder());
        pointBuffer = bb.asFloatBuffer();
        pointBuffer.put(buffer);
        pointBuffer.position(0);

        GLES20.glVertexAttribPointer(
                pointHandle, 1,
                GLES20.GL_FLOAT, false,
                4, pointBuffer);
        GLES20.glDisableVertexAttribArray(pointHandle);
        */
    }
    public void updatePosition(Vector3 pos) {
        this.pos.set(pos);
        //GLES20.glUniform3fv(GLES20.glGetUniformLocation(s, "translation"), 1, pos.toFloatArray(), 0);
    }
    @Override
    public void draw(float[] mvpMatrix) {
        if (pointHandle != -1) {
            GLES20.glUseProgram(s);
            GLES20.glEnableVertexAttribArray(pointHandle);
            GLES20.glDrawArrays(GLES20.GL_POINTS, 0, pointBuffer.capacity() / 12);
            GLES20.glDisableVertexAttribArray(pointHandle);
        }
    }
}
