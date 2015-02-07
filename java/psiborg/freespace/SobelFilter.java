package psiborg.freespace;

import android.graphics.Bitmap;

/**
 * Created by Joshua on 2/7/2015.
 */
public class SobelFilter {
    public static int[][] sobel_y = {{-1,-2,-1},
                                {0,0,0},
                                {1,2,1}};
    public static int[][] sobel_x = {{-1,0,1},
                                {-2,0,2},
                                {-1,0,1}};

    public int kernal_width = 3;
    public int kernal_height = 3;
    public int kernal_halfWidth = 1;
    public int kernal_halfHeight = 1;

    public static int pixel_x;
    public static int pixel_y;

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
        int width = 5;//original.getWidth();
        int height = 5;//original.getHeight();
        Bitmap result = Bitmap.createBitmap(original);
        int colors[] = new int[5*5];//width*height];
        //int resultColors[] = new int[width*height-2*width+2*height];
        original.getPixels(colors,0,width,0,0,width,height);
        for (int x = 1; x < width - 2; x++) {
            if(!(x%width == 0)) { //skip the borders of the 2d array
                for (int y = 1; y < height - 2; y++) {
                    pixel_x = (sobel_x[0][0] * colors[(x-1) * width + (y - 1)]) + (sobel_x[0][1] * colors[ x * width + (y - 1)]) + (sobel_x[0][2] * colors[ (x+1) * width + (y - 1)]) +
                            (sobel_x[1][0] * colors[(x-1) * width + y]) + (sobel_x[1][1] * colors[ x * width + y]) + (sobel_x[1][2] * colors[(x+1) * width + y]) +
                            (sobel_x[2][0] * colors[(x-1) * width + y + 1]) + (sobel_x[2][1] * colors[ x * width + y + 1]) + (sobel_x[2][2] * colors[(x+1) * width + y + 1]);
                    pixel_y = (sobel_y[0][0] * colors[(x-1) * width + y - 1]) + (sobel_y[0][1] * colors[ x * width + y - 1]) + (sobel_y[0][2] * colors[(x+1) * width + y - 1]) +
                            (sobel_y[1][0] * colors[(x-1) * width + y]) + (sobel_y[1][1] * colors[ x * width + y]) + (sobel_y[1][2] * colors[(x+1)* width + y]) +
                            (sobel_y[2][0] * colors[(x-1) * width + y + 1]) + (sobel_y[2][1] * colors[ x * width + y + 1]) + (sobel_x[2][2] * colors[(x+1)* width + y + 1]);

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
        }
        return result;
    }

}

