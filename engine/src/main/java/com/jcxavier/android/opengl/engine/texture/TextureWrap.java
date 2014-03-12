package com.jcxavier.android.opengl.engine.texture;

/**
 * Created on 13/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public enum TextureWrap {

    /**
     * The integer part of the texture coordinates is ignored in this mode
     */
    REPEAT,
    /**
     * The texture coordinates are clamped to [0, 1]
     */
    CLAMP_TO_EDGE
}
