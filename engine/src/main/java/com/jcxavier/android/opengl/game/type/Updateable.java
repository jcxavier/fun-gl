package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Updateable {

    /**
     * Retrieves whether this object is visible (and thus updateable).
     *
     * @return true if the object is visible, false otherwise
     */
    boolean isVisible();

    void clean();

    void update(Matrix4 projectionMatrix);

    void draw();
}
