package com.jcxavier.android.opengl.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import com.jcxavier.android.opengl.engine.gdx.GdxEglConfigChooser;

/**
 * Created on 31/01/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
class EngineView extends GLSurfaceView {

    final EngineRenderer mRenderer;

    EngineView(Context context) {
        super(context);

        // create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        // handle pauses (orientation changes) nicely
        setPreserveEGLContextOnPause(true);

        mRenderer = new EngineRenderer((EngineActivity) context);
    }

    void initializeConfigChooser() {
        Bitmap.Config config = mRenderer.mBitmapConfig;
        int r = BitmapConfigHelper.getRedBytes(config);
        int g = BitmapConfigHelper.getGreenBytes(config);
        int b = BitmapConfigHelper.getBlueBytes(config);
        int a = BitmapConfigHelper.getAlphaBytes(config);

        int depthBufferSize = mRenderer.mDepthBufferSize;
        int stencilBufferSize = mRenderer.mStencilBufferSize;
        int samples = mRenderer.mSamples;

        setEGLConfigChooser(new GdxEglConfigChooser(r, g, b, a, depthBufferSize, stencilBufferSize, samples));
    }

    void loadRenderer() {
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return mRenderer.onTouchEvent(event);
    }
}
