package com.jcxavier.android.opengl.game;

import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * @author jxav
 */
public interface Positionable {

    void setPosition(final Vector3 position);

    Vector3 getPosition();

    void setSize(final Vector2 size);

    Vector2 getSize();

    void setAnchorPoint(final Vector2 anchorPoint);

    Vector2 getAnchorPoint();
}
