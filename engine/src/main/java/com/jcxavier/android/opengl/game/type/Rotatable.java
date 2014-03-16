package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Vector3;
import com.jcxavier.android.opengl.engine.type.RotationMode;

import java.util.Vector;

/**
 * Created by RicardoJorge on 13/03/14.
 */
public interface Rotatable {

    /**
     * Sets the rotation of the object
     *
     * @param rotation the rotation to set with degrees angles
     */
    void setRotation(final Vector3 rotation);

    /**
     *
     * @return the rotation
     */
    Vector3 getRotation();

    /**
     * Sets the rotation mode of the object.
     *
     * @param rotMode the order that the rotations will be applied
     */
    void setRotationMode(final RotationMode rotMode);

    /**
     *
     * @return returns the rotation mode of the object.
     */
    RotationMode getRotationMode();
}
