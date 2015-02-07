package psiborg.freespace;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;

import java.io.IOException;

public class CameraActivity extends Activity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private TextureView mTextureView;
    private Bitmap mBitmap;
    private SurfaceTexture mSurfaceTexture;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);

        setContentView(mTextureView);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(
                previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();

            mBitmap = mTextureView.getBitmap();
        } catch (IOException ioe) {
            // Something bad happened
        }
        mTextureView.setAlpha(1.0f);
        mTextureView.setRotation(90.0f);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //mSurfaceTexture = mTextureView.getSurfaceTexture();
        //inputImages.add(mTextureView.getBitmap());
        mBitmap = mTextureView.getBitmap();
        Bitmap sobeled = SobelFilter.fastSobel(mBitmap);

    }
}

/*
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;

import java.io.IOException;
import java.util.ArrayList;


public class CameraActivity extends Activity implements TextureView.SurfaceTextureListener {
    SurfaceTexture mSurfaceTexture;
    Camera mCamera;
    Bitmap mBitmap;
    TextureView mTextureView;
    ArrayList<Bitmap> inputImages = new ArrayList<Bitmap>();
    ArrayList<Bitmap> outputImages = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);

        setContentView(mTextureView);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.i("cam", "AVAILABLE");
        mCamera = Camera.open();
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
            mBitmap = mTextureView.getBitmap();
        } catch (IOException ioe) {
            System.out.println("Camera Preview Failed :(");
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        //don't need to worry about this
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        mSurfaceTexture = getSurfaceTexture();
        //inputImages.add(mTextureView.getBitmap());
        mBitmap = mTextureView.getBitmap();
        Bitmap sobeled = SobelFilter.fastSobel(mBitmap);
        //setContentView();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    public SurfaceTexture getSurfaceTexture() {
        return mSurfaceTexture;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
*/