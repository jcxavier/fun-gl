package com.jcxavier.android.opengl.game.manager;

import android.graphics.Point;
import com.jcxavier.android.opengl.game.camera.Camera;
import com.jcxavier.android.opengl.game.camera.DefaultCamera;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class ScreenManager {

    private final Point mSize;
    private final Camera mCamera;

    public ScreenManager(final Camera camera) {
        mSize = new Point(1, 1);

        if (camera != null) {
            mCamera = camera;
        } else {
            // fallback camera
            mCamera = new DefaultCamera();
        }
    }

    public void onLayout(final Point screenSize) {
        mCamera.updateScreenSize(screenSize);
        mSize.set(screenSize.x, screenSize.y);
    }

    public Camera getCamera() {
        return mCamera;
    }

    public Point getSize() {
        return mSize;
    }
}
