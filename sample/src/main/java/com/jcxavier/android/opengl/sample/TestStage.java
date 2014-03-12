package com.jcxavier.android.opengl.sample;

import com.jcxavier.android.opengl.game.SimpleGameStage;
import com.jcxavier.android.opengl.game.camera.Camera;
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
        staticShape.setPosition(new Vector3(400, 400, 0));
        staticShape.setSize(new Vector2(100, 100));
        staticShape.setColor(new Vector3(0.5f, 0.5f, 1.0f));
        staticShape.setAlpha(0.7f);

        movingShape = new DrawableObject();
        movingShape.setPosition(new Vector3(0, 0, 0));
        movingShape.setSize(new Vector2(50, 50));
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
