package com.jcxavier.android.opengl.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.view.MotionEvent;
import com.jcxavier.android.opengl.game.camera.Camera;
import com.jcxavier.android.opengl.game.manager.GameManager;
import com.jcxavier.android.opengl.game.manager.ScreenManager;
import com.jcxavier.android.opengl.game.manager.input.InputManager;
import com.jcxavier.android.opengl.game.object.GameObject;
import com.jcxavier.android.opengl.game.type.Updateable;
import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class SimpleGameStage implements GameStage, GameManager {

    private final List<Updateable> mUpdateableObjects;

    private final InputManager mInputManager;
    private final ScreenManager mScreenManager;

    public SimpleGameStage(final Camera camera) {
        mUpdateableObjects = new ArrayList<>();

        mInputManager = new InputManager();
        mScreenManager = new ScreenManager(camera);
    }

    @Override
    public void onLoad() {
        // no additional setup required here
    }

    @Override
    public void onUnload() {
        for (Updateable object : mUpdateableObjects) {
            object.clean();
        }

        mUpdateableObjects.clear();
    }

    @Override
    public void onLayout(final Point screenSize) {
        mScreenManager.onLayout(screenSize);
    }

    @Override
    public void onUpdate(final double dt) {
        Matrix4 projectionMatrix = mScreenManager.getCamera().getProjectionMatrix();

        mInputManager.onUpdate();

        for (int i = 0, len = mUpdateableObjects.size(); i < len; i++) {
            mUpdateableObjects.get(i).update(projectionMatrix);
        }
    }

    @Override
    public final void onDraw() {
        for (int i = 0, len = mUpdateableObjects.size(); i < len; i++) {
            Updateable object = mUpdateableObjects.get(i);

            if (object.isVisible()) {
                object.draw();
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        mInputManager.queueEvent(event);
        // event was consumed by this stage
        return true;
    }

    @Override
    public final InputManager getInputManager() {
        return mInputManager;
    }

    @Override
    public final ScreenManager getScreenManager() {
        return mScreenManager;
    }

    protected final void addGameObject(final GameObject gameObject) {
        gameObject.setGameManager(this);
        mUpdateableObjects.add(gameObject);
    }

    protected final void removeGameObject(final GameObject gameObject) {
        mUpdateableObjects.remove(gameObject);
        gameObject.setGameManager(null);
        gameObject.clean();
    }
}
