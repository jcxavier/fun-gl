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
    private DrawableObject mRorationObject1;
    private DrawableObject mRotationObject2;
    private DrawableObject mRotationObject3;

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
                resetMovingShapesPosition();
                return true;
            }
        });

        mMovingObject = new Sprite("spaceship.png");
        mMovingObject.setAnchorPoint(new Vector2(0.0f, 0.0f));
        mMovingObject.setScale(new Vector3(1.0f, 1.5f, 1.0f));
        mMovingObject.setAlpha(0.5f);

        // create a static shape that moves down and rotates on the x axis
        mRorationObject1 = new DrawableObject();
        mRorationObject1.setSize(new Vector2(50, 50));
        mRorationObject1.setAnchorPoint(new Vector2(0.5f, 0.5f));
        mRorationObject1.setColor(new Vector3(0.2f, 0.4f, 1.0f));

        // create a static shape that moves to the right and rotates on the y axis
        mRotationObject2 = new DrawableObject();
        mRotationObject2.setSize(new Vector2(50, 50));
        mRotationObject2.setAnchorPoint(new Vector2(0.5f, 0.5f));
        mRotationObject2.setColor(new Vector3(0.4f, 1.0f, 0.8f));

        // create a static shape that moves to the left and rotates on the z axis
        mRotationObject3 = new DrawableObject();
        mRotationObject3.setPosition(new Vector3(400, 400, 0));
        mRotationObject3.setSize(new Vector2(50, 50));
        mRotationObject3.setAnchorPoint(new Vector2(0.5f, 0.5f));
        mRotationObject3.setColor(new Vector3(1.0f, 0.7f, 0.2f));


        // add the objects to the stage, they will be automatically managed and updated
        addGameObject(mMovingObject);
        addGameObject(mRorationObject1);
        addGameObject(mRotationObject2);
        addGameObject(mRotationObject3);
        addGameObject(mClickableShape);

        // set the initial position of the moving shape
        resetMovingShapesPosition();
    }

    private void resetMovingShapesPosition() {
        mMovingObject.setPosition(new Vector3(25f, 25f, 0f));
        mRorationObject1.setPosition(new Vector3(25f, 25f, 0f));
        mRotationObject2.setPosition(new Vector3(25f, 25f, 0f));
        mRotationObject3.setPosition(new Vector3(400, 400, 0));
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        mFrame++;
        float dtFloat = (float) dt;
        float moveOffset = dtFloat * 20;

        // get the current position of the GameObject and increment it
        Vector3 currentPosition = mMovingObject.getPosition();
        currentPosition.x += moveOffset;
        currentPosition.y += moveOffset;

        // setting the position of the object will trigger the update of the transformations
        mMovingObject.setPosition(currentPosition);

        currentPosition = mRorationObject1.getPosition();
        currentPosition.y += moveOffset;

        mRorationObject1.setPosition(currentPosition);

        currentPosition = mRotationObject2.getPosition();
        currentPosition.x += moveOffset;

        mRotationObject2.setPosition(currentPosition);

        currentPosition = mRotationObject3.getPosition();
        currentPosition.x -= moveOffset;

        mRotationObject3.setPosition(currentPosition);

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

        float rotateOffset = (float) (dt * 30);

        // get the current position of the GameObject and increments the rotation in the x axis
        Vector3 currentRotation = mRorationObject1.getRotation();
        currentRotation.x += rotateOffset;

        // setting the rotation of the object will trigger the update of the transformations
        mRorationObject1.setRotation(currentRotation);

        currentRotation = mRotationObject2.getRotation();
        currentRotation.y += rotateOffset;

        mRotationObject2.setRotation(currentRotation);

        currentRotation = mRotationObject3.getRotation();
        currentRotation.z += rotateOffset;

        mRotationObject3.setRotation(currentRotation);

    }
}
