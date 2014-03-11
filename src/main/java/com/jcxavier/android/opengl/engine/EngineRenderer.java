package com.jcxavier.android.opengl.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.Game2D;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector3;

import static android.opengl.GLES20.*;

/**
 * Created on 31/01/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
class EngineRenderer implements GLSurfaceView.Renderer, RendererOptions {

    private final EngineActivity mActivity;

    private final Matrix4 mProjection;
    private final Vector3 mBgColor;

    private long lastUpdateTimeStamp;
    private Game2D mGame;

    EngineRenderer(final EngineActivity activity) {
        mActivity = activity;

        mProjection = new Matrix4();
        mBgColor = new Vector3();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mActivity.onGlContextLoad();

        resetTimestamp();
        mGame.onLoad();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        Matrix.orthoM(mProjection.m, 0, 0, width, height, 0, -1, 1);

        mGame.onLayout(new Point(width, height));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // calculate deltaTime
        long now = System.nanoTime();
        double dt = (now - lastUpdateTimeStamp) / 1.0e9;
        lastUpdateTimeStamp = now;

        // move clear color elsewhere
        glClearColor(mBgColor.x, mBgColor.y, mBgColor.z, 1.0f);
        glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        mGame.onUpdate(dt);
        mGame.onDraw();
    }

    public void setBackgroundColor(final Vector3 backgroundColor) {
        mBgColor.set(backgroundColor);
    }

    private void resetTimestamp() {
        lastUpdateTimeStamp = System.nanoTime();
    }

    public void setGame(final Game2D game) {
        mGame = game;
    }

    public boolean onTouchEvent(final MotionEvent event) {
        // TODO touch stuff
        return false;
    }
}
