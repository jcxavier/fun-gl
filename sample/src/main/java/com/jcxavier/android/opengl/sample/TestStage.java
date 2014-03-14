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

    private GameObject movingObject;

    public TestStage(final Camera camera) {
        super(camera);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // create a static shape that resets the moving object upon click
        DrawableObject clickableShape = new DrawableObject();

        // top-left of the object should be at (400, 400)
        clickableShape.setPosition(new Vector3(450, 450, 0));
        clickableShape.setSize(new Vector2(100, 100));
        clickableShape.setAnchorPoint(new Vector2(0.5f, 0.5f));

        clickableShape.setColor(new Vector3(0.5f, 0.5f, 1.0f));
        clickableShape.setAlpha(0.7f);

        clickableShape.setInputHandler(new InputHandler() {
            @Override
            public boolean processTouch(final MotionEvent event) {
                resetMovingShapePosition();
                return true;
            }
        });

        movingObject = new Sprite("spaceship.png");
        movingObject.setAnchorPoint(new Vector2(0.0f, 0.0f));
        movingObject.setScale(new Vector3(1.0f, 1.5f, 1.0f));
        movingObject.setAlpha(0.4f);

        // add the objects to the stage, they will be automatically managed and updated
        addGameObject(movingObject);
        addGameObject(clickableShape);

        // set the initial position of the moving shape
        resetMovingShapePosition();
    }

    private void resetMovingShapePosition() {
        movingObject.setPosition(new Vector3(0, 0, 0));
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        float moveOffset = (float) (dt * 20);

        // update the current moving shape position with the previously computed moveOffset
        Vector3 currentPosition = movingObject.getPosition();
        currentPosition.x += moveOffset;
        currentPosition.y += moveOffset;

        // setting the position of the object will trigger the update of the transformations
        movingObject.setPosition(currentPosition);
    }
}
