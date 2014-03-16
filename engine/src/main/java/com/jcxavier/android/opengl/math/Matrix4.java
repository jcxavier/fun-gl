package com.jcxavier.android.opengl.math;

import android.opengl.Matrix;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class Matrix4 {

    private static final float[] TMP_MATRIX = new float[16];

    public final float[] m;

    public Matrix4() {
        m = new float[16];
        Matrix.setIdentityM(m, 0);
    }

    public void set(final Matrix4 matrix) {
        System.arraycopy(matrix.m, 0, m, 0, 16);
    }

    public Matrix4 copy() {
        Matrix4 copyMatrix = new Matrix4();
        copyMatrix.set(this);
        return copyMatrix;
    }

    public void setIdentity() {
        Matrix.setIdentityM(m, 0);
    }

    public void translate(final Vector3 pos) {
        Matrix.translateM(m, 0, pos.x, pos.y, pos.z);
    }

    public void scale(final Vector3 scale) {
        Matrix.scaleM(m, 0, scale.x, scale.y, scale.z);
    }

    public void multiply(final Matrix4 matrix) {
        Matrix.multiplyMM(TMP_MATRIX, 0, m, 0, matrix.m, 0);
        System.arraycopy(TMP_MATRIX, 0, m, 0, 16);
    }

    /**
     * Rotates the matrix around the x axis.
     *
     * @param degrees the rotation to apply, in degrees
     */
    public void rotateX(final float degrees) {
        Matrix.rotateM(m, 0, degrees, 1, 0, 0);
    }

    /**
     * Rotates the matrix around the y axis.
     *
     * @param degrees the rotation to apply, in degrees
     */
    public void rotateY(final float degrees) {
        Matrix.rotateM(m, 0, degrees, 0, 1, 0);
    }

    /**
     * Rotates the matrix around the z axis.
     *
     * @param degrees the rotation to apply, in degrees
     */
    public void rotateZ(final float degrees) {
        Matrix.rotateM(m, 0, degrees, 0, 0, 1);
    }

}
