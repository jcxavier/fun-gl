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
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(final Vector3 v) {
        set(v);
    }

    @Override
    public final Vector3 set(final Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }
}
