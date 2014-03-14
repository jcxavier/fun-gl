package com.jcxavier.android.opengl.math;

import android.opengl.Matrix;

import com.jcxavier.android.opengl.engine.type.RotationMode;

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

    public void multiply(Matrix4 matrix) {
        Matrix.multiplyMM(TMP_MATRIX, 0, m, 0, matrix.m, 0);
        System.arraycopy(TMP_MATRIX, 0, m, 0, 16);
    }

    /**
     *
     * @param rotation the rotation vector to apply
     * @param rotMode the order the order that the rotations will be applied
     */

    public void rotate(final Vector3 rotation, RotationMode rotMode) {

        switch (rotMode) {
            case XYZ: {
                Matrix.rotateM(m, 0, rotation.x, 1, 0, 0);
                Matrix.rotateM(m, 0, rotation.y, 0, 1, 0);
                Matrix.rotateM(m, 0, rotation.z, 0, 0, 1);
                break;
            }
            case XZY: {
                Matrix.rotateM(m, 0, rotation.x, 1, 0, 0);
                Matrix.rotateM(m, 0, rotation.z, 0, 0, 1);
                Matrix.rotateM(m, 0, rotation.y, 0, 1, 0);
                break;
            }
            case YXZ: {
                Matrix.rotateM(m, 0, rotation.y, 0, 1, 0);
                Matrix.rotateM(m, 0, rotation.x, 1, 0, 0);
                Matrix.rotateM(m, 0, rotation.z, 0, 0, 1);
                break;
            }
            case YZX: {
                Matrix.rotateM(m, 0, rotation.y, 0, 1, 0);
                Matrix.rotateM(m, 0, rotation.z, 0, 0, 1);
                Matrix.rotateM(m, 0, rotation.x, 1, 0, 0);
                break;
            }
            case ZXY: {
                Matrix.rotateM(m, 0, rotation.z, 0, 0, 1);
                Matrix.rotateM(m, 0, rotation.x, 1, 0, 0);
                Matrix.rotateM(m, 0, rotation.y, 0, 1, 0);
                break;
            }
            case ZYX: {
                Matrix.rotateM(m, 0, rotation.z, 0, 0, 1);
                Matrix.rotateM(m, 0, rotation.y, 0, 1, 0);
                Matrix.rotateM(m, 0, rotation.x, 1, 0, 0);
                break;
            }
            default:{
                // unity default rotation
                Matrix.rotateM(m, 0, rotation.z, 0, 0, 1);
                Matrix.rotateM(m, 0, rotation.x, 1, 0, 0);
                Matrix.rotateM(m, 0, rotation.y, 0, 1, 0);
            }
        }
    }

}
