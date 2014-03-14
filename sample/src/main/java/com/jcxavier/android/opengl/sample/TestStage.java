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

    private DrawableObject xAxisRotationShape;
    private DrawableObject yAxisRotationShape;
    private DrawableObject zAxisRotationShape;


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
                resetMovingShapesPosition();
                return true;
            }
        });

        // create a moving shape with anchor point in the center of the object. should be aligned with the origin (0, 0)
        movingShape = new DrawableObject();
        movingShape.setSize(new Vector2(50, 50));
        movingShape.setAnchorPoint(new Vector2(0.5f, 0.5f));
        movingShape.setColor(new Vector3(0.8f, 0.9f, 1.0f));

        xAxisRotationShape = new DrawableObject();
        xAxisRotationShape.setSize(new Vector2(50, 50));
        xAxisRotationShape.setAnchorPoint(new Vector2(0.5f, 0.5f));
        xAxisRotationShape.setColor(new Vector3(0.2f, 0.4f, 1.0f));

        yAxisRotationShape = new DrawableObject();
        yAxisRotationShape.setSize(new Vector2(50, 50));
        yAxisRotationShape.setAnchorPoint(new Vector2(0.5f, 0.5f));
        yAxisRotationShape.setColor(new Vector3(0.4f, 1.0f, 0.8f));

        zAxisRotationShape = new DrawableObject();
        zAxisRotationShape.setPosition(new Vector3(400, 400, 0));
        zAxisRotationShape.setSize(new Vector2(50, 50));
        zAxisRotationShape.setAnchorPoint(new Vector2(0.5f, 0.5f));
        zAxisRotationShape.setColor(new Vector3(1.0f, 0.7f, 0.2f));


        // add the objects to the stage, they will be automatically managed and updated
        addGameObject(movingShape);
        addGameObject(xAxisRotationShape);
        addGameObject(yAxisRotationShape);
        addGameObject(zAxisRotationShape);
        addGameObject(clickableShape);

        // set the initial position of the moving shape
        resetMovingShapesPosition();
    }

    private void resetMovingShapesPosition() {
        movingShape.setPosition(new Vector3(25f, 25f, 0f));
        xAxisRotationShape.setPosition(new Vector3(25f, 25f, 0f));
        yAxisRotationShape.setPosition(new Vector3(25f, 25f, 0f));
        zAxisRotationShape.setPosition(new Vector3(400, 400, 0));
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        float moveOffset = (float) (dt * 20);

        // update the current moving shape position with the previously computed moveOffset
        Vector3 movingShapeCurrentPosition = movingShape.getPosition();
        movingShapeCurrentPosition.add(new Vector3(moveOffset, moveOffset, 0));
        // setting the position of the object will trigger the update of the transformations
        movingShape.setPosition(movingShapeCurrentPosition);

        Vector3 xAxisRotationShapeCurrentPosition = movingShape.getPosition();
        xAxisRotationShapeCurrentPosition.add(new Vector3(0, moveOffset, 0));
        xAxisRotationShape.setPosition(xAxisRotationShapeCurrentPosition);

        Vector3 yAxisRotationShapeCurrentPosition = movingShape.getPosition();
        yAxisRotationShapeCurrentPosition.add(new Vector3(moveOffset, 0, 0));
        yAxisRotationShape.setPosition(yAxisRotationShapeCurrentPosition);

        Vector3 zAxisRotationShapeCurrentPosition = movingShape.getPosition();
        zAxisRotationShapeCurrentPosition.add(new Vector3(-moveOffset, 0, 0));
        zAxisRotationShape.setPosition(zAxisRotationShapeCurrentPosition);

        float rotateOffset = (float) (dt * 5);

        // update the current moving shape rotation with the previously computed rotateOffset
        Vector3 movingShapeCurrentRotation = movingShape.getRotation();
        movingShapeCurrentRotation.add(new Vector3(moveOffset, moveOffset, moveOffset));
        movingShape.setRotation(movingShapeCurrentRotation);

        Vector3 xAxisRotationShapeCurrentRotation = movingShape.getRotation();
        xAxisRotationShapeCurrentRotation.add(new Vector3(moveOffset, 0, 0));
        xAxisRotationShape.setRotation(xAxisRotationShapeCurrentRotation);

        Vector3 yAxisRotationShapeCurrentRotation = movingShape.getRotation();
        yAxisRotationShapeCurrentRotation.add(new Vector3(0, moveOffset, 0));
        yAxisRotationShape.setRotation(yAxisRotationShapeCurrentRotation);

        Vector3 zAxisRotationShapeCurrentRotation = movingShape.getRotation();
        zAxisRotationShapeCurrentRotation.add(new Vector3(0, 0, moveOffset));
        zAxisRotationShape.setRotation(zAxisRotationShapeCurrentRotation);

    }
}
