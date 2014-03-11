package com.jcxavier.android.opengl.game.object;

import com.jcxavier.android.opengl.game.type.Positionable;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public abstract class GameObject implements Positionable {

    protected final Vector3 mPosition;
    protected final Vector3 mScale;
    protected final Vector2 mSize;
    protected final Vector2 mAnchorPoint;

    protected final Matrix4 mModel;

    private final Vector3 mPivot;
    private boolean mDirty;

    public GameObject() {
        mPosition = new Vector3(0, 0, 0);
        mScale = new Vector3(1, 1, 1);
        mAnchorPoint = new Vector2(0, 0);
        mPivot = new Vector3(0, 0, 0);

        mSize = new Vector2();
        mModel = new Matrix4();

        mDirty = true;
    }

    public void updateTransformations() {
        if (mDirty) {
            mModel.setIdentity();
            mModel.translate(Vector3.add(mPosition, mPivot));
            mModel.translate(Vector3.negate(mPivot));

            // TODO rotation

            mModel.scale(mScale);
            mModel.translate(mPivot);

            mDirty = false;
        }
    }

    @Override
    public void setPosition(final Vector3 position) {
        mPosition.set(position);
        mDirty = true;
    }

    @Override
    public Vector3 getPosition() {
        return mPosition;
    }

    @Override
    public void setSize(final Vector2 size) {
        mSize.set(size);
        setAnchorPoint(mAnchorPoint);
    }

    @Override
    public Vector2 getSize() {
        return mSize;
    }

    @Override
    public void setAnchorPoint(final Vector2 anchorPoint) {
        mAnchorPoint.set(anchorPoint);
        mPivot.set(-mSize.x * mAnchorPoint.x, -mSize.y * mAnchorPoint.y, 0);
        mDirty = true;
    }

    @Override
    public Vector2 getAnchorPoint() {
        return mAnchorPoint;
    }

    public abstract void update(final Matrix4 projectionMatrix);

    public abstract void draw();
}
