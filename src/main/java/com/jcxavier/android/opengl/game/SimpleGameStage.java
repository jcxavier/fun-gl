package com.jcxavier.android.opengl.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.opengl.Matrix;
import com.jcxavier.android.opengl.game.object.GameObject;
import com.jcxavier.android.opengl.math.Matrix4;

/**
 * @author jxav
 */
public class SimpleGameStage implements GameStage {

    private final Point mBounds;
    private final Matrix4 mProjection;
    private final List<GameObject> mGameObjects;

    public SimpleGameStage() {
        mBounds = new Point(1, 1);
        mProjection = new Matrix4();
        mGameObjects = new ArrayList<>();
    }

    @Override
    public void onLoad() {
        // TODO onLoad
    }

    @Override
    public void onLayout(final Point screenSize) {
        mBounds.set(screenSize.x, screenSize.y);
        Matrix.orthoM(mProjection.m, 0, 0, screenSize.x, screenSize.y, 0, -1, 1);
    }

    @Override
    public void onUpdate(final double dt) {
        for (GameObject gameObject : mGameObjects) {
            gameObject.updateTransformations();
            gameObject.update(mProjection);
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
