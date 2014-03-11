package com.jcxavier.android.opengl.math;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class Vector3 implements IVector<Vector3> {

    public float x;
    public float y;
    public float z;

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(final float x, final float y, final float z) {
        set(x, y, z);
    }

    public Vector3(final Vector3 v) {
        set(v);
    }

    @Override
    public Vector3 set(final Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    public Vector3 set(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Vector3 add(final Vector3 _v) {
        return set(x + _v.x, y + _v.y, z + _v.z);
    }

    @Override
    public Vector3 sub(final Vector3 _v) {
        return set(x - _v.x, y - _v.y, z - _v.z);
    }

    /**
     * Constructs a new vector containing the sum of two given vectors.
     *
     * @param _vectorLeft  the left-hand side vector
     * @param _vectorRight the right-hand side vector
     * @return a new vector with the result of the sum
     */
    public static Vector3 add(Vector3 _vectorLeft, Vector3 _vectorRight) {
        Vector3 v = new Vector3(_vectorLeft);
        v.add(_vectorRight);
        return v;
    }

    /**
     * Constructs a new vector containing the subtraction of two given vectors.
     *
     * @param _vectorLeft  the left-hand side vector
     * @param _vectorRight the right-hand side vector
     * @return a new vector with the result of the subtraction
     */
    public static Vector3 subtract(Vector3 _vectorLeft, Vector3 _vectorRight) {
        Vector3 v = new Vector3(_vectorLeft);
        v.sub(_vectorRight);
        return v;
    }

    /**
     * Constructs a new negated vector (i.e. with all its components negated).
     *
     * @param _vector the original vector
     * @return a new vector
     */
    public static Vector3 negate(final Vector3 _vector) {
        return new Vector3(-_vector.x, -_vector.y, -_vector.z);
    }
}
