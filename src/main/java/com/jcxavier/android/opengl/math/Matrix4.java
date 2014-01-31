package com.jcxavier.android.opengl.math;

import android.opengl.Matrix;

/**
 * Created on 31/01/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class Matrix4 {

    public final float[] m;

    public Matrix4() {
        m = new float[16];
        Matrix.setIdentityM(m, 0);
    }
}
