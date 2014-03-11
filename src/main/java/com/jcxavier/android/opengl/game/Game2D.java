package com.jcxavier.android.opengl.game;

import android.graphics.Point;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Game2D {

    void onLoad();

    void onLayout(Point screenSize);

    void onUpdate(double dt);

    void onDraw();
}
