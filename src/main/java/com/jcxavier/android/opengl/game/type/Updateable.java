package com.jcxavier.android.opengl.game.type;

import com.jcxavier.android.opengl.math.Matrix4;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface Updateable {

    void update(Matrix4 projectionMatrix);

    void draw();
}
