package com.jcxavier.android.opengl.sample;

import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.SimpleGameStage;
import com.jcxavier.android.opengl.game.camera.Camera;
import com.jcxavier.android.opengl.game.manager.input.InputHandler;
import com.jcxavier.android.opengl.game.object.DrawableObject;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class TestStage extends SimpleGameStage {

    private DrawableObject movingShape;

    public TestStage(final Camera camera) {
        super(camera);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // create a static shape that resets the moving shape upon click
        DrawableObject clickableShape = new DrawableObject();
        clickableShape.setPosition(new Vector3(400, 400, 0));
        clickableShape.setSize(new Vector2(100, 100));
        clickableShape.setAnchorPoint(new Vector2(0.0f, 0.0f));
        clickableShape.setColor(new Vector3(0.5f, 0.5f, 1.0f));
        clickableShape.setAlpha(0.7f);
        clickableShape.setInputHandler(new InputHandler() {
            @Override
            public boolean processTouch(final MotionEvent event) {
                resetMovingShapePosition();
                return true;
            }
        });

        // create a moving shape with anchor point in the center of the object. should be aligned with the origin (0, 0)
        movingShape = new DrawableObject();
        movingShape.setSize(new Vector2(50, 50));
        movingShape.setAnchorPoint(new Vector2(0.5f, 0.5f));
        movingShape.setColor(new Vector3(0.8f, 0.9f, 1.0f));

        // add the objects to the stage, they will be automatically managed and updated
        addGameObject(movingShape);
        addGameObject(clickableShape);

        // set the initial position of the moving shape
        resetMovingShapePosition();
    }

    private void resetMovingShapePosition() {
        movingShape.setPosition(new Vector3(25f, 25f, 0f));
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        float moveOffset = (float) (dt * 20);

        // update the current moving shape position with the previously computed moveOffset
        Vector3 currentPosition = movingShape.getPosition();
        currentPosition.x += moveOffset;
        currentPosition.y += moveOffset;

        // setting the position of the object will trigger the update of the transformations
        movingShape.setPosition(currentPosition);
    }
}
