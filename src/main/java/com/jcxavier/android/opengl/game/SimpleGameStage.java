package com.jcxavier.android.opengl.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import com.jcxavier.android.opengl.game.camera.Camera;
import com.jcxavier.android.opengl.game.object.GameObject;
import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class SimpleGameStage implements GameStage {

    private final Point mBounds;
    private final List<GameObject> mGameObjects;

    private Camera mCamera;

    public SimpleGameStage(final Camera camera) {
        mBounds = new Point(1, 1);
        mGameObjects = new ArrayList<>();

        mCamera = camera;
    }

    @Override
    public void onLoad() {
        // TODO onLoad
    }

    @Override
    public void onLayout(final Point screenSize) {
        mCamera.updateScreenSize(screenSize);
        mBounds.set(screenSize.x, screenSize.y);
    }

    @Override
    public void onUpdate(final double dt) {
        Matrix4 projectionMatrix = mCamera.getProjectionMatrix();

        for (GameObject gameObject : mGameObjects) {
            gameObject.updateTransformations();
            gameObject.update(projectionMatrix);
        }
    }

    @Override
    public void onDraw() {
        for (GameObject gameObject : mGameObjects) {
            gameObject.draw();
        }
    }

    protected void addGameObject(final GameObject gameObject) {
        mGameObjects.add(gameObject);
    }

    protected void removeGameObject(final GameObject gameObject) {
        mGameObjects.remove(gameObject);
    }
}
