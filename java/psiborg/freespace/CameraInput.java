package psiborg.freespace;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by Joshua on 2/6/2015.
 */
public class CameraInput extends TextureView implements TextureView.SurfaceTextureListener {
    SurfaceTexture mSurfaceTexture;
    Camera mCamera;

    public CameraInput(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.setSurfaceTextureListener(this);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException ioe) {
            System.out.println("Camera Preview Failed :(");
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        mSurfaceTexture = getSurfaceTexture();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface){
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

}
