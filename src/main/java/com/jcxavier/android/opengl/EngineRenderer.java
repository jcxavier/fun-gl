package com.jcxavier.android.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.jcxavier.android.opengl.math.Matrix4;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * Created on 31/01/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class EngineRenderer implements GLSurfaceView.Renderer {

    private final Matrix4 projection;

    public EngineRenderer() {
        projection = new Matrix4();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // TODO: initialize stuff
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        Matrix.orthoM(projection.m, 0, 0, width, height, 0, -1, 1);

        // TODO: notify orientation change
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        // TODO: go mad
    }
}
