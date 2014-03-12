package com.jcxavier.android.opengl.game.object;

import com.jcxavier.android.opengl.game.type.Positionable;
import com.jcxavier.android.opengl.game.type.Resizeable;
import com.jcxavier.android.opengl.game.type.Transformable;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public abstract class GameObject implements Positionable, Resizeable, Transformable {

    protected final Vector3 mPosition;
    protected final Vector3 mScale;
    protected final Vector2 mSize;
    protected final Vector2 mAnchorPoint;
    protected float mAlpha;

    private final Vector3 mPivot;
    protected final Matrix4 mModelMatrix;

    private boolean mDirty;

    /**
     * Creates a simple game object, able to position itself and handle basic transformations.
     */
    public GameObject() {
        mPosition = new Vector3(0, 0, 0);
        mScale = new Vector3(1, 1, 1);
        mAnchorPoint = new Vector2(0, 0);
        mPivot = new Vector3(0, 0, 0);
        mAlpha = 1;

        mSize = new Vector2();
        mModelMatrix = new Matrix4();

        mDirty = true;
    }

    /**
     * Signals the game object to update its model transformation matrix. The transformations are only actually updated
     * if the object was modified.
     */
    @Override
    public void updateTransformations() {
        if (mDirty) {
            mModelMatrix.setIdentity();
            mModelMatrix.translate(Vector3.add(mPosition, mPivot));
            mModelMatrix.translate(Vector3.negate(mPivot));

            // TODO rotation

            mModelMatrix.scale(mScale);
            mModelMatrix.translate(mPivot);

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

    /**
     * Sets the anchor point of this game object, between 0;0 and 1;1, where 0;0 is top-left and 1;1 is bottom-right.
     *
     * @param anchorPoint the anchor point to set
     */
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

    /**
     * Sets the alpha value of this object.
     *
     * @param alpha the alpha to set
     */
    public void setAlpha(final float alpha) {
        mAlpha = alpha;
    }

    /**
     * Retrieves the alpha value of this object.
     *
     * @return the alpha value
     */
    public float getAlpha() {
        return mAlpha;
    }

    /**
     * Updates the state of the object with the given projection matrix.
     *
     * @param projectionMatrix the projection matrix
     */
    public abstract void update(final Matrix4 projectionMatrix);

    /**
     * Draws the object.
     */
    public abstract void draw();
}
