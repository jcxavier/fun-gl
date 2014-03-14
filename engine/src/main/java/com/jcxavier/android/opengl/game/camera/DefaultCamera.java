package com.jcxavier.android.opengl.game.camera;

import android.graphics.Point;
import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 14/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class DefaultCamera implements Camera {

    private final Matrix4 mProjectionMatrix;

    /**
     * Creates a default camera with an identity matrix.
     */
    public DefaultCamera() {
        mProjectionMatrix = new Matrix4();
    }

    @Override
    public void updateScreenSize(final Point screenSize) {
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        return mProjectionMatrix;
    }
}
