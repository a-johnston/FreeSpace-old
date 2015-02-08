package psiborg.android5000.base;

import android.opengl.GLES20;
import android.util.Log;

public abstract class Shader implements Drawable {
	public static int instance(String vertex, String fragment) {
		int v = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		int f = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		GLES20.glShaderSource(v, vertex);
        checkError("vertex source error");
		GLES20.glCompileShader(v);
        checkError("vertex compile error");
		GLES20.glShaderSource(f, fragment);
        checkError("fragment source error");
		GLES20.glCompileShader(f);
        checkError("fragment compile error");
		int p = GLES20.glCreateProgram();
        checkError("create program error");
		GLES20.glAttachShader(p, v);
        checkError("attach vertex error");
		GLES20.glAttachShader(p, f);
        checkError("attach fragment error");
		GLES20.glLinkProgram(p);
        checkError("link program error");
		return p;
	}
    private static void checkError(String message) {
        if (GLES20.glGetError() != GLES20.GL_NO_ERROR) {
            Log.e("INSTANCE", message + " " + GLES20.glGetError());
            //throw new ArrayIndexOutOfBoundsException();
        }
    }
}
