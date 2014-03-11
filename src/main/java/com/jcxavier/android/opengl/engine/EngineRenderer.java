package com.jcxavier.android.opengl.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.GameStage;
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

    Bitmap.Config mBitmapConfig;
    int mDepthBufferSize;
    int mStencilBufferSize;
    int mSamples;
    boolean mBlendingEnabled;
    boolean mBackfaceCullingEnabled;

    private long mLastUpdateTimeStamp;
    private GameStage mGame;

    EngineRenderer(final EngineActivity activity) {
        mActivity = activity;

        mProjection = new Matrix4();

        // default options
        mBgColor = new Vector3(0, 0, 0); // black
        mBitmapConfig = Bitmap.Config.ARGB_8888;
        mDepthBufferSize = 16;
        mStencilBufferSize = 8;
        mSamples = 0;
        mBlendingEnabled = true;
        mBackfaceCullingEnabled = false;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mActivity.onGlContextLoad();

        applyRendererOptions();

        resetTimestamp();
        mGame.onLoad();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        mGame.onLayout(new Point(width, height));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // calculate deltaTime
        long now = System.nanoTime();
        double dt = (now - mLastUpdateTimeStamp) / 1.0e9;
        mLastUpdateTimeStamp = now;

        glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        mGame.onUpdate(dt);
        mGame.onDraw();
    }

    @Override
    public void setBackgroundColor(final Vector3 backgroundColor) {
        mBgColor.set(backgroundColor);
    }

    @Override
    public void setStencilBufferSize(final int size) {
        mStencilBufferSize = size;
    }

    @Override
    public void setDepthBufferSize(final int size) {
        mDepthBufferSize = size;
    }

    @Override
    public void setSamples(final int numberOfSamples) {
        mSamples = numberOfSamples;
    }

    @Override
    public void setBitmapConfig(final Bitmap.Config bitmapConfig) {
        mBitmapConfig = bitmapConfig;
    }

    @Override
    public void setBlendingEnabled(final boolean enabled) {
        mBlendingEnabled = enabled;
    }

    @Override
    public void setBackfaceCullingEnabled(final boolean enabled) {
        mBackfaceCullingEnabled = enabled;
    }

    private void applyRendererOptions() {
        glClearColor(mBgColor.x, mBgColor.y, mBgColor.z, 1.0f);

        if (mBlendingEnabled) {
            glEnable(GL_BLEND);
        } else {
            glDisable(GL_BLEND);
        }

        if (mBackfaceCullingEnabled) {
            glCullFace(GL_BACK);
            glEnable(GL_CULL_FACE);
        } else {
            glDisable(GL_CULL_FACE);
        }

        if (mDepthBufferSize > 0) {
            glDepthFunc(GL_LEQUAL);
            glEnable(GL_DEPTH_TEST);
        } else {
            glDisable(GL_DEPTH_TEST);
        }
    }

    private void resetTimestamp() {
        mLastUpdateTimeStamp = System.nanoTime();
    }

    public void setGame(final GameStage game) {
        mGame = game;
    }

    public boolean onTouchEvent(final MotionEvent event) {
        // TODO touch stuff
        return false;
    }
}
