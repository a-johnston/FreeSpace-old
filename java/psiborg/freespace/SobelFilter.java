package psiborg.freespace;

import android.graphics.Bitmap;

/**
 * Created by Joshua on 2/7/2015.
 */
public class SobelFilter {
    public static double[][] sobel_y = {{-1.,-2.,-1.},
                                {0.,0.,0.},
                                {1.,2.,1.}};
    public static double[][] sobel_x = {{-1.,0.,1.},
                                {-2.,0.,2.},
                                {-1.,0.,1.}};

    //public static Vector3[] s_y = {new Vector3(sobel_x[0]),new Vector3({0,0,0}),new Vector3({1,2,1})};

    public int kernal_width = 3;
    public int kernal_height = 3;
    public int kernal_halfWidth = 1;
    public int kernal_halfHeight = 1;

    public static double pixel_x;
    public static double pixel_y;

    public static Bitmap applySobel(Bitmap original) {
        int width = original.getWidth();
        int height = original.getHeight();
        Bitmap result = Bitmap.createBitmap(original);
        for (int x = 1; x < width - 2; x++) {
            for (int y = 1; y < height - 2; y++) {
                pixel_x = (sobel_x[0][0] * original.getPixel(x - 1, y - 1)) + (sobel_x[0][1] * original.getPixel(x, y - 1)) + (sobel_x[0][2] * original.getPixel(x + 1, y - 1)) +
                        (sobel_x[1][0] * original.getPixel(x - 1, y)) + (sobel_x[1][1] * original.getPixel(x, y)) + (sobel_x[1][2] * original.getPixel(x + 1, y)) +
                        (sobel_x[2][0] * original.getPixel(x - 1, y + 1)) + (sobel_x[2][1] * original.getPixel(x, y + 1)) + (sobel_x[2][2] * original.getPixel(x + 1, y + 1));
                pixel_y = (sobel_y[0][0] * original.getPixel(x - 1, y - 1)) + (sobel_y[0][1] * original.getPixel(x, y - 1)) + (sobel_y[0][2] * original.getPixel(x + 1, y - 1)) +
                        (sobel_y[1][0] * original.getPixel(x - 1, y)) + (sobel_y[1][1] * original.getPixel(x, y)) + (sobel_y[1][2] * original.getPixel(x + 1, y)) +
                        (sobel_y[2][0] * original.getPixel(x - 1, y + 1)) + (sobel_y[2][1] * original.getPixel(x, y + 1)) + (sobel_x[2][2] * original.getPixel(x + 1, y + 1));

                int val = (int) Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));

                if (val < 0) {
                    val = 0;
                }

                if (val > 255) {
                    val = 255;
                }

                result.setPixel(x, y, val);
            }
        }
        return result;
    }

    public static Bitmap fastSobel(Bitmap original) {
        int width = original.getWidth();
        int height = original.getHeight();
        Bitmap result = Bitmap.createBitmap(original);
        int colors[] = new int[width*height];
        //int resultColors[] = new int[width*height-2*width+2*height];
        original.getPixels(colors,0,width,0,0,width,height);
        for (int x = 1; x < width - 2; x++) {
            //if(!(x%width == 0 || x%width == width-1)) { //skip the borders of the 2d array
                for (int y = 1; y < height - 2; y++) {
                    int i1 = (x-1) * width + (y - 1);
                    int i2 = (x+1) * width + y + 1;
                    pixel_x = (sobel_x[0][0] * colors[(x-1) * height + (y - 1)]) + (sobel_x[0][1] * colors[ x * height + (y - 1)]) + (sobel_x[0][2] * colors[ (x+1) * height + (y - 1)]) +
                            (sobel_x[1][0] * colors[(x-1) * height + y]) + (sobel_x[1][1] * colors[ x * height + y]) + (sobel_x[1][2] * colors[(x+1) * height + y]) +
                            (sobel_x[2][0] * colors[(x-1) * height + y + 1]) + (sobel_x[2][1] * colors[ x * height + y + 1]) + (sobel_x[2][2] * colors[(x+1) * height + y + 1]);
                    pixel_y = (sobel_y[0][0] * colors[(x-1) * height + y - 1]) + (sobel_y[0][1] * colors[ x * height + y - 1]) + (sobel_y[0][2] * colors[(x+1) * height + y - 1]) +
                            (sobel_y[1][0] * colors[(x-1) * height + y]) + (sobel_y[1][1] * colors[ x * height + y]) + (sobel_y[1][2] * colors[(x+1)* height + y]) +
                            (sobel_y[2][0] * colors[(x-1) * height + y + 1]) + (sobel_y[2][1] * colors[ x * height + y + 1]) + (sobel_x[2][2] * colors[(x+1)* height + y + 1]);

                    int val = (int) Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));

                    if (val < 0) {
                        val = 0;
                    }

                    if (val > 255) {
                        val = 255;
                    }

                    result.setPixel(x, y, val);
                }
            //}

        }
        return result;
    }


    public static Bitmap fastestSobel(Bitmap original) {
        int width = original.getWidth();
        int height = original.getHeight();
        Bitmap result = Bitmap.createBitmap(original);
        int stride = 5;
        int colors[] = new int[width*height];
        int resultColors[] = new int[width*height];
        original.getPixels(colors,0,width,0,0,width,height);
        for (int x = stride + 1; x < width - 30*stride; x+=stride) {
            //if(!(x%width == 0 || x%width == width-1)) { //skip the borders of the 2d array
            for (int y = stride+ 1; y < height - 30*stride; y+=stride) {
                int i1 = (x-stride) * height + (y - stride);
                int i2 = (x+stride) * height + y + stride;
                pixel_x = (sobel_x[0][0] * colors[(x-stride) * height + (y - stride)]) + (sobel_x[0][1] * colors[ x * height + (y - stride)]) + (sobel_x[0][2] * colors[ (x+stride) * height + (y - stride)]) +
                        (sobel_x[1][0] * colors[(x-stride) * height + y]) + (sobel_x[1][1] * colors[ x * height + y]) + (sobel_x[1][2] * colors[(x+stride) * height + y]) +
                        (sobel_x[2][0] * colors[(x-stride) * height + y + stride]) + (sobel_x[2][1] * colors[ x * height + y + stride]) + (sobel_x[2][2] * colors[(x+stride) * height + y + stride]);

                pixel_y = (sobel_y[0][0] * colors[(x-stride) * height + y - stride]) + (sobel_y[0][1] * colors[ x * height + y - stride]) + (sobel_y[0][2] * colors[(x+stride) * height + y - stride]) +
                        (sobel_y[1][0] * colors[(x-stride) * height + y]) + (sobel_y[1][1] * colors[ x * height + y]) + (sobel_y[1][2] * colors[(x+stride)* height + y]) +
                        (sobel_y[2][0] * colors[(x-stride) * height + y + stride]) + (sobel_y[2][1] * colors[ x * height + y + stride]) + (sobel_x[2][2] * colors[(x+stride)* height + y + stride]);

                int val = (int) Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));

                if(x*width+y < 921600) {
                    resultColors[x * width + y] = val;
                }
                //result.setPixel(x, y, val);
            }
            result = Bitmap.createBitmap(resultColors,0,width,width,height, Bitmap.Config.ARGB_8888);
        }
        return result;
    }
}

