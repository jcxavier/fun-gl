package com.jcxavier.android.opengl;

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

        DrawableObject staticShape = new DrawableObject();
        staticShape.setPosition(new Vector3(400f, 400f, 0f));
        staticShape.setSize(new Vector2(100f, 100f));
        staticShape.setAnchorPoint(new Vector2(0.0f, 0.0f));
        staticShape.setColor(new Vector3(0.5f, 0.5f, 1.0f));
        staticShape.setAlpha(0.7f);
        staticShape.setInputHandler(new InputHandler() {
            @Override
            public boolean processTouch(final MotionEvent event) {
                movingShape.setPosition(new Vector3(0f, 0f, 0f));
                return true;
            }
        });

        movingShape = new DrawableObject();
        movingShape.setPosition(new Vector3(0f, 0f, 0f));
        movingShape.setAnchorPoint(new Vector2(0.5f, 0.5f));
        movingShape.setSize(new Vector2(50f, 50f));
        movingShape.setColor(new Vector3(0.8f, 0.9f, 1.0f));

        addGameObject(movingShape);
        addGameObject(staticShape);
    }

    @Override
    public void onUpdate(double dt) {
        super.onUpdate(dt);

        movingShape.setPosition(movingShape.getPosition().add(new Vector3((float) dt * 20f, (float) dt * 20f, 0)));
    }
}
