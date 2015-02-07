package psiborg.freespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;

import java.io.IOException;

/**
 * Created by Joshua on 2/6/2015.
 */
public class CameraInput extends TextureView implements TextureView.SurfaceTextureListener {
    SurfaceTexture mSurfaceTexture;
    Camera mCamera;
    Bitmap mBitmap;

    public CameraInput(Context context){
        super(context);
        this.setSurfaceTextureListener(this);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.i("cam", "AVAILABLE");
        mCamera = Camera.open();
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
            mBitmap = getBitmap();
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
        mBitmap = getBitmap();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface){
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    public SurfaceTexture getSurfaceTexture(){
        return mSurfaceTexture;
    }
}
