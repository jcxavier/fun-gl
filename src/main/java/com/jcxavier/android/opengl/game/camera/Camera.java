package com.jcxavier.android.opengl.game.camera;

import android.graphics.Point;
import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Camera {

    /**
     * Updates the screen size in the camera.
     *
     * @param screenSize the screen size
     */
    void updateScreenSize(Point screenSize);

    /**
     * Retrieves the computed projection matrix for this camera.
     *
     * @return the projection matrix
     */
    Matrix4 getProjectionMatrix();
}
