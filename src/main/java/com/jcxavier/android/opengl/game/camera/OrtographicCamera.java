package com.jcxavier.android.opengl.game.camera;

import android.graphics.Point;
import android.opengl.Matrix;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class OrtographicCamera implements Camera {

    private final Matrix4 mProjectionMatrix;
    private final Matrix4 mLookAtMatrix;

    private final Vector3 mEye;
    private final Vector3 mCenter;
    private final Vector3 mUp;

    private final Point mScreenSize;

    /**
     * Creates a simple, non-configurable ortographic camera.
     */
    public OrtographicCamera() {
        mProjectionMatrix = new Matrix4();
        mLookAtMatrix = new Matrix4();

        mEye = new Vector3(0.0f, 0.0f, 0.0f);
        mCenter = new Vector3(0.0f, 0.0f, -1.0f);
        mUp = new Vector3(0.0f, 1.0f, 0.0f);

        mScreenSize = new Point();
    }

    @Override
    public void updateScreenSize(final Point screenSize) {
        mScreenSize.set(screenSize.x, screenSize.y);
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        Matrix.orthoM(mProjectionMatrix.m, 0, 0, mScreenSize.x, mScreenSize.y, 0, -1024, 1024);
        Matrix.setLookAtM(mLookAtMatrix.m, 0, mEye.x, mEye.y, mEye.z, mCenter.x, mCenter.y, mCenter.z, mUp.x, mUp.y, mUp.z);

        mProjectionMatrix.multiply(mLookAtMatrix);
        return mProjectionMatrix;
    }
}
