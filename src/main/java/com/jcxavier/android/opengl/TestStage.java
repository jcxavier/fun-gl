package com.jcxavier.android.opengl;

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

    public TestStage(final Camera camera) {
        super(camera);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        DrawableObject shape1 = new DrawableObject();
        shape1.setPosition(new Vector3(400, 400, 0));
        shape1.setSize(new Vector2(100, 100));
        shape1.setColor(new Vector3(0.5f, 0.5f, 1.0f));

        DrawableObject shape2 = new DrawableObject();
        shape2.setPosition(new Vector3(200, 200, 0));
        shape2.setSize(new Vector2(50, 50));
        shape2.setColor(new Vector3(0.8f, 0.9f, 1.0f));

        addGameObject(shape1);
        addGameObject(shape2);
    }
}
