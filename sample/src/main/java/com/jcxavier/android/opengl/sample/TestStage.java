package com.jcxavier.android.opengl.sample;

import android.graphics.Point;
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
    private DrawableObject mRotatingObjectX;
    private DrawableObject mRotatingObjectY;
    private DrawableObject mRotatingObjectZ;

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
                resetMovingShapesPosition();
                return true;
            }
        });

        // create a sprite of a spaceship with increased Y scale
        mMovingObject = new Sprite("spaceship.png");
        mMovingObject.setAnchorPoint(new Vector2(0.0f, 0.0f));
        mMovingObject.setScale(new Vector3(1.0f, 1.5f, 1.0f));
        mMovingObject.setAlpha(0.5f);

        // create a static shape that moves down and rotates on the x axis
        mRotatingObjectX = new DrawableObject();
        mRotatingObjectX.setSize(new Vector2(50, 50));
        mRotatingObjectX.setAnchorPoint(new Vector2(0.5f, 0.5f));
        mRotatingObjectX.setColor(new Vector3(0.2f, 0.4f, 1.0f));

        // create a static shape that moves to the right and rotates on the y axis
        mRotatingObjectY = new DrawableObject();
        mRotatingObjectY.setSize(new Vector2(50, 50));
        mRotatingObjectY.setAnchorPoint(new Vector2(1.0f, 1.0f));
        mRotatingObjectY.setColor(new Vector3(0.4f, 1.0f, 0.8f));

        // create a static shape that moves to the left and rotates on the z axis
        mRotatingObjectZ = new DrawableObject();
        mRotatingObjectZ.setPosition(new Vector3(400, 400, 0));
        mRotatingObjectZ.setSize(new Vector2(50, 50));
        mRotatingObjectZ.setAnchorPoint(new Vector2(0.5f, 0.5f));
        mRotatingObjectZ.setColor(new Vector3(1.0f, 0.7f, 0.2f));

        // add the objects to the stage, they will be automatically managed and updated
        addGameObject(mMovingObject);
        addGameObject(mRotatingObjectX);
        addGameObject(mRotatingObjectY);
        addGameObject(mRotatingObjectZ);
        addGameObject(mClickableShape);
    }

    @Override
    public void onLayout(Point screenSize) {
        super.onLayout(screenSize);

        // set the initial position of the moving shapes and also on screen rotation
        resetMovingShapesPosition();
    }

    private void resetMovingShapesPosition() {
        mMovingObject.setPosition(new Vector3(0, 0, 0));
        mRotatingObjectX.setPosition(new Vector3(25, 25, 0));
        mRotatingObjectY.setPosition(new Vector3(50, 50, 0));
        mRotatingObjectZ.setPosition(new Vector3(400, 400, 0));
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        // increment frame count
        mFrame++;

        float dtFloat = (float) dt;
        float moveOffset = dtFloat * 20;

        // get the current position of the GameObject and increment it
        Vector3 currentPosition = mMovingObject.getPosition();
        currentPosition.x += moveOffset;
        currentPosition.y += moveOffset;

        // setting the position of the object will trigger the update of the transformations
        mMovingObject.setPosition(currentPosition);

        // shift alpha per frame (should loop in 2.5s)
        float alpha = mMovingObject.getAlpha() + mAlphaSign * dtFloat * 0.4f;
        mMovingObject.setAlpha(alpha);

        // reverse the sign once it is fully visible or invisible
        if ((alpha > 1.0f && mAlphaSign > 0) || (alpha < 0.0f && mAlphaSign < 0)) {
            mAlphaSign *= -1;
        }

        // trigger the button visibility (it shouldn't trigger while hidden)
        if (mFrame % 200 == 0) {
            mClickableShape.setVisible(!mClickableShape.isVisible());
        }

        float rotateOffset = (float) (dt * 30);

        // get the current position of the GameObject and increments the rotation in the x axis
        Vector3 currentRotation = mRotatingObjectX.getRotation();
        currentRotation.x += rotateOffset;
        currentPosition = mRotatingObjectX.getPosition();
        currentPosition.y += moveOffset;

        // setting the rotation of the object will trigger the update of the transformations
        mRotatingObjectX.setRotation(currentRotation);
        mRotatingObjectX.setPosition(currentPosition);

        currentRotation = mRotatingObjectY.getRotation();
        currentRotation.y += rotateOffset;
        currentPosition = mRotatingObjectY.getPosition();
        currentPosition.x += moveOffset;

        mRotatingObjectY.setRotation(currentRotation);
        mRotatingObjectY.setPosition(currentPosition);

        currentRotation = mRotatingObjectZ.getRotation();
        currentRotation.z += rotateOffset;
        currentPosition = mRotatingObjectZ.getPosition();
        currentPosition.x -= moveOffset;

        mRotatingObjectZ.setRotation(currentRotation);
        mRotatingObjectZ.setPosition(currentPosition);
    }
}
