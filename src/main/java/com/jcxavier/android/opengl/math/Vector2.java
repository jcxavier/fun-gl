package com.jcxavier.android.opengl.math;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class Vector2 implements IVector<Vector2> {

    public float x;
    public float y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(final Vector2 v) {
        x = v.x;
        y = v.y;
    }

    @Override
    public final Vector2 set(final Vector2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vector2 set(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public Vector2 add(final Vector2 _v) {
        return set(x + _v.x, y + _v.y);
    }

    @Override
    public Vector2 sub(final Vector2 _v) {
        return set(x - _v.x, y - _v.y);
    }
}
