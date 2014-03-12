package com.jcxavier.android.opengl.game;

import android.graphics.Point;
import android.view.MotionEvent;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface GameStage {

    void onLoad();

    void onUnload();

    void onLayout(Point screenSize);

    void onUpdate(double dt);

    void onDraw();

    boolean onTouchEvent(MotionEvent event);
}
