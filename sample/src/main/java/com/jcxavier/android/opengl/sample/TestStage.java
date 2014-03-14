package com.jcxavier.android.opengl.sample;

import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.SimpleGameStage;
import com.jcxavier.android.opengl.game.camera.Camera;
import com.jcxavier.android.opengl.game.manager.input.InputHandler;
import com.jcxavier.android.opengl.game.object.DrawableObject;
import com.jcxavier.android.opengl.game.object.GameObject;
import com.jcxavier.android.opengl.game.object.Sprite;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class TestStage extends SimpleGameStage {

    private long mFrame;

    private DrawableObject mClickableShape;
    private GameObject mMovingObject;

    private int mAlphaSign;

    public TestStage(final Camera camera) {
        super(camera);

        mFrame = 0;
        mAlphaSign = 1;
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // create a static shape that resets the moving object upon click
        mClickableShape = new DrawableObject();

        // top-left of the object should be at (400, 400)
        mClickableShape.setPosition(new Vector3(450, 450, 0));
        mClickableShape.setSize(new Vector2(100, 100));
        mClickableShape.setAnchorPoint(new Vector2(0.5f, 0.5f));

        mClickableShape.setColor(new Vector3(0.5f, 0.5f, 1.0f));
        mClickableShape.setAlpha(0.7f);

        mClickableShape.setInputHandler(new InputHandler() {
            @Override
            public boolean processTouch(final MotionEvent event) {
                resetMovingShapePosition();
                return true;
            }
        });

        mMovingObject = new Sprite("spaceship.png");
        mMovingObject.setAnchorPoint(new Vector2(0.0f, 0.0f));
        mMovingObject.setScale(new Vector3(1.0f, 1.5f, 1.0f));
        mMovingObject.setAlpha(0.5f);

        // add the objects to the stage, they will be automatically managed and updated
        addGameObject(mMovingObject);
        addGameObject(mClickableShape);

        // set the initial position of the moving shape
        resetMovingShapePosition();
    }

    private void resetMovingShapePosition() {
        mMovingObject.setPosition(new Vector3(0, 0, 0));
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        mFrame++;
        float dtFloat = (float) dt;
        float moveOffset = dtFloat * 20;

        // update the current moving shape position with the previously computed moveOffset
        Vector3 currentPosition = mMovingObject.getPosition();
        currentPosition.x += moveOffset;
        currentPosition.y += moveOffset;

        // setting the position of the object will trigger the update of the transformations
        mMovingObject.setPosition(currentPosition);

        // shift alpha per frame (should loop in 2.5s)
        float alpha = mMovingObject.getAlpha() + mAlphaSign * dtFloat * 0.4f;
        mMovingObject.setAlpha(alpha);

        // reverse the sign once it is fully visible or invisible
        if (alpha > 1.0f || alpha < 0.0f) {
            mAlphaSign *= -1;
        }

        // trigger the button visibility (it shouldn't trigger while hidden)
        if (mFrame % 200 == 0) {
            mClickableShape.setVisible(!mClickableShape.isVisible());
        }
    }
}
