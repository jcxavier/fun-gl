package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Vector3;

import java.util.Vector;

/**
 * Created by RicardoJorge on 13/03/14.
 */
public interface Rotatable {

    /**
     * Sets the rotation of the object.
     *
     * @param rotation the rotation to set
     */
    void setRotation(final Vector3 rotation);

    /**
     *
     * @return the rotation
     */
    Vector3 getRotation();

}
