package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Positionable {

    /**
     * Sets the position of the object.
     *
     * @param position the position to set
     */
    void setPosition(final Vector3 position);

    /**
     * Retrieves the position of the object.
     *
     * @return the position
     */
    Vector3 getPosition();
}
