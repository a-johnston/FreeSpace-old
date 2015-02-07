package psiborg.freespace;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import psiborg.android5000.base.Shader;

public class PointCloudShader extends Shader {
    private static final String vert = "pointcloud_vert";
    private static final String frag = "pointcloud_frag";
    private int s;
    private FloatBuffer pointBuffer;
    public PointCloudShader() {
        s = instance(vert, frag);
    }
    public void updateBuffer(float[] buffer) {
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length*4);
        bb.order(ByteOrder.nativeOrder());
        pointBuffer = bb.asFloatBuffer();
        pointBuffer.put(buffer);
        pointBuffer.position(0);
    }
    @Override
    public void draw(float[] mvpMtrix) {
        
    }
}
