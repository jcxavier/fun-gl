package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Transformable {

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
     * Sets the scale of the object.
     *
     * @param scale the scale to set
     */
    void setScale(final Vector3 scale);

    /**
     * Retrieves the scale of the object.
     *
     * @return the scale
     */
    Vector3 getScale();

    /**
     * Sets the rotation of the object, where the rotation for each axis is represented by the vector components and is
     * given in degrees.
     *
     * @param rotation the rotation to set
     */
    void setRotation(final Vector3 rotation);

    /**
     * Retrieves the rotation of the object.
     *
     * @return the rotation
     */
    Vector3 getRotation();
}
