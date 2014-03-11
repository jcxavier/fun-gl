package com.jcxavier.android.opengl.game.camera;

import android.graphics.Point;
import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Camera {

    void updateScreenSize(Point screenSize);

    Matrix4 getProjectionMatrix();
}
