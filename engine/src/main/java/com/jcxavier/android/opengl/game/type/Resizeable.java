package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Vector2;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Resizeable {

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
