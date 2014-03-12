package com.jcxavier.android.opengl.game.type;

import android.view.MotionEvent;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Touchable {

    boolean canBeTouched();

    boolean isTouchedBy(MotionEvent _event);

    boolean onTouch(MotionEvent event);
}
