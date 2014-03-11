package com.jcxavier.android.opengl.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

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

        mRenderer = new EngineRenderer((EngineActivity) context);
    }

    void loadRenderer() {
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return mRenderer.onTouchEvent(event);
    }
}
