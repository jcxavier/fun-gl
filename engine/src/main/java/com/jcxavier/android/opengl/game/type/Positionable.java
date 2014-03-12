package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Vector2;
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

    /**
     * Sets the size of the object.
     *
     * @param size the size to set
     */
    void setSize(final Vector2 size);

    /**
     * Retrieves the size of the object.
     *
     * @return the size
     */
    Vector2 getSize();

    /**
     * Sets the anchor point of the object.
     *
     * @param anchorPoint the anchor point to set
     */
    void setAnchorPoint(final Vector2 anchorPoint);

    /**
     * Retrieves the anchor point of the object.
     *
     * @return the anchor point
     */
    Vector2 getAnchorPoint();
}
