package com.jcxavier.android.opengl.game.object;

import java.lang.ref.WeakReference;

import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.manager.GameManager;
import com.jcxavier.android.opengl.game.manager.input.InputHandler;
import com.jcxavier.android.opengl.game.type.Positionable;
import com.jcxavier.android.opengl.game.type.Resizeable;
import com.jcxavier.android.opengl.game.type.Touchable;
import com.jcxavier.android.opengl.game.type.Updateable;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public abstract class GameObject implements Positionable, Resizeable, Updateable, Touchable {

    /**
     * Auxiliary vector for calculations.
     */
    private static final Vector3 TMP_VEC3 = new Vector3();

    protected final Vector3 mPosition;
    protected final Vector3 mScale;
    protected final Vector2 mSize;
    protected final Vector2 mAnchorPoint;
    protected float mAlpha;

    private final Vector3 mPivot;
    protected final Matrix4 mModelMatrix;

    private boolean mDirty;

    private WeakReference<GameManager> mGameManager;
    private InputHandler mInputHandler;
    private boolean mTouchable;

    private boolean mVisible;

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

        mGameManager = new WeakReference<>(null);
        mDirty = true;

        mVisible = true;
    }

    /**
     * Updates the state of the object with the given projection matrix.
     *
     * @param projectionMatrix the projection matrix
     */
    public final void update(final Matrix4 projectionMatrix) {
        updateTransformations();
        updatePostTransformations(projectionMatrix);
    }

    /**
     * Signals the game object to update its model transformation matrix. The transformations are only actually updated
     * if the object was modified.
     */
    private void updateTransformations() {
        if (mDirty) {
            mModelMatrix.setIdentity();

            mModelMatrix.translate(TMP_VEC3.set(mPosition).add(mPivot));
            mModelMatrix.translate(TMP_VEC3.set(mPivot).negate());

            // TODO rotation

            mModelMatrix.scale(mScale);
            mModelMatrix.translate(mPivot);

            mDirty = false;
        }
    }

    @Override
    public final void setPosition(final Vector3 position) {
        mPosition.set(position);
        mDirty = true;
    }

    @Override
    public final Vector3 getPosition() {
        return mPosition;
    }

    public final void setScale(Vector3 scale) {
        mScale.set(scale);
        mDirty = true;
    }

    public final Vector3 getScale() {
        return mScale;
    }

    @Override
    public void setSize(final Vector2 size) {
        mSize.set(size);
        setAnchorPoint(mAnchorPoint);
    }

    @Override
    public final Vector2 getSize() {
        return mSize;
    }

    /**
     * Sets the anchor point of this game object, between 0;0 and 1;1, where 0;0 is top-left and 1;1 is bottom-right.
     *
     * @param anchorPoint the anchor point to set
     */
    @Override
    public final void setAnchorPoint(final Vector2 anchorPoint) {
        mAnchorPoint.set(anchorPoint);
        mPivot.set(-mSize.x * mAnchorPoint.x, -mSize.y * mAnchorPoint.y, 0);
        mDirty = true;
    }

    @Override
    public final Vector2 getAnchorPoint() {
        return mAnchorPoint;
    }

    /**
     * Sets the alpha value of this object.
     *
     * @param alpha the alpha to set
     */
    public final void setAlpha(final float alpha) {
        mAlpha = alpha;
    }

    /**
     * Retrieves the alpha value of this object.
     *
     * @return the alpha value
     */
    public final float getAlpha() {
        return mAlpha;
    }

    public final void setVisible(final boolean visible) {
        mVisible = visible;
    }

    @Override
    public final boolean isVisible() {
        return mVisible;
    }

    @Override
    public boolean canBeTouched() {
        return mVisible && mSize.x > 0 && mSize.y > 0 && mAlpha > 0;
    }

    @Override
    public boolean isTouchedBy(final MotionEvent event) {
        // simplified picking (2D picking)
        float topLeftX = mPosition.x + mPivot.x;
        float topLeftY = mPosition.y + mPivot.y;
        float bottomRightX = mPosition.x + mPivot.x + mSize.x;
        float bottomRightY = mPosition.y + mPivot.y + mSize.y;
        float touchX = event.getX();
        float touchY = event.getY();

        return topLeftX <= touchX && touchX <= bottomRightX && topLeftY <= touchY && touchY <= bottomRightY;
    }

    @Override
    public boolean onTouch(final MotionEvent event) {
        return mTouchable && mInputHandler.processTouch(event);
    }

    public final void setGameManager(final GameManager gameManager) {
        mGameManager = new WeakReference<>(gameManager);
        handleInputManagerAddition(gameManager);
    }

    public final void setInputHandler(final InputHandler inputHandler) {
        mInputHandler = inputHandler;
        setTouchable(mInputHandler != null);
    }

    public void setTouchable(final boolean touchable) {
        mTouchable = touchable;

        // if there is no input handler, create an anonymous input handler that doesn't process touches
        if (mInputHandler == null) {
            mInputHandler = new InputHandler() {
                @Override
                public boolean processTouch(final MotionEvent event) {
                    return false;
                }
            };
        }

        GameManager gameManager = mGameManager.get();
        handleInputManagerAddition(gameManager);
    }

    private void handleInputManagerAddition(final GameManager gameManager) {
        if (gameManager != null) {
            if (mTouchable) {
                gameManager.getInputManager().addManagedObject(this);
            } else {
                gameManager.getInputManager().removeManagedObject(this);
            }
        }
    }

    @Override
    public abstract void clean();

    protected abstract void updatePostTransformations(final Matrix4 projectionMatrix);

    /**
     * Draws the object.
     */
    public abstract void draw();
}
