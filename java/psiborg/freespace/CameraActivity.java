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
    public Camera mCamera;
    private TextureView mTextureView;
    private Bitmap mBitmap;
    private Bitmap sobel;
    private Bitmap compareSobel;
    //private CameraActivity.RenderingThread mThread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout content = new FrameLayout(this);

        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);


        content.addView(mTextureView, new FrameLayout.LayoutParams(500, 500, Gravity.CENTER));
        setContentView(content);
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
        mBitmap = mTextureView.getBitmap();
        sobel = SobelFilter.fastSobel(mBitmap);
        compareSobel = SobelFilter.applySobel(mBitmap);
        mBitmap = mTextureView.getBitmap();
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
        sobel = SobelFilter.fastSobel(mBitmap);

    }

    public Bitmap getSobel(){
        return sobel;
    }

    /*
    private static class RenderingThread extends Thread {
        private final TextureView mSurface;
        private volatile boolean mRunning = true;
        private CameraActivity mCamera;

        public RenderingThread(TextureView surface,CameraActivity camera) {
            mSurface = surface;
            mCamera = camera;

        }

        @Override
        public void run() {
            float x = 0.0f;
            float y = 0.0f;
            float speedX = 5.0f;
            float speedY = 3.0f;

            Paint paint = new Paint();
            paint.setColor(0xff00ff00);

            while (mRunning && !Thread.interrupted()) {
                final Canvas canvas = mSurface.lockCanvas(null);
                RectF dst = new RectF(0,0,mCamera.mCamera.getParameters().getPreviewSize().width,mCamera.mCamera.getParameters().getPreviewSize().height);
                try {
                //    canvas.drawBitmap(mCamera.getSobel(),null,dst,null);
                } catch(NullPointerException e){

                }
                finally {
                    mSurface.unlockCanvasAndPost(canvas);
                }

                if (x + 20.0f + speedX >= mSurface.getWidth() || x + speedX <= 0.0f) {
                    speedX = -speedX;
                }
                if (y + 20.0f + speedY >= mSurface.getHeight() || y + speedY <= 0.0f) {
                    speedY = -speedY;
                }

                x += speedX;
                y += speedY;

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    // Interrupted
                }
            }
        }

        void stopRendering() {
            interrupt();
            mRunning = false;
        }
    }*/
}
