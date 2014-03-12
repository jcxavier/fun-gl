package com.jcxavier.android.opengl.game.manager.input;

import android.view.MotionEvent;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface InputHandler {

    boolean processTouch(MotionEvent event);
}
