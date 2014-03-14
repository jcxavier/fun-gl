package com.jcxavier.android.opengl.math;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class Vector4 implements IVector<Vector4> {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
    }

    public Vector4(final float x, final float y, final float z, final float w) {
        set(x, y, z, w);
    }

    public Vector4(final Vector4 v) {
        set(v);
    }

    @Override
    public Vector4 set(final Vector4 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
        return this;
    }

    public Vector4 set(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @Override
    public Vector4 add(final Vector4 v) {
        return set(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    @Override
    public Vector4 sub(final Vector4 v) {
        return set(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    @Override
    public Vector4 negate() {
        return set(-x, -y, -z, -w);
    }
}
