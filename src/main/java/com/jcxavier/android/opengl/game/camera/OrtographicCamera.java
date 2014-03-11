package com.jcxavier.android.opengl.game.camera;

import android.graphics.Point;
import android.opengl.Matrix;
import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class OrtographicCamera implements Camera {

    private final Matrix4 mProjectionMatrix;
    private final Point mScreenSize;

    /**
     * Creates a simple, non-configurable ortographic camera.
     */
    public OrtographicCamera() {
        mProjectionMatrix = new Matrix4();
        mScreenSize = new Point();
    }

    @Override
    public void updateScreenSize(final Point screenSize) {
        mScreenSize.set(screenSize.x, screenSize.y);
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        Matrix.orthoM(mProjectionMatrix.m, 0, 0, mScreenSize.x, mScreenSize.y, 0, -1, 1);
        return mProjectionMatrix;
    }
}
