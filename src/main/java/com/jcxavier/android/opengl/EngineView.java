package com.jcxavier.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created on 31/01/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class EngineView extends GLSurfaceView {

    private final EngineRenderer mRenderer;

    public EngineView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        mRenderer = new EngineRenderer();
        setRenderer(mRenderer);
    }
}
