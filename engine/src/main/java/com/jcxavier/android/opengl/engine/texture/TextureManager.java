package com.jcxavier.android.opengl.engine.texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class TextureManager {

    private static TextureManager sTextureManager = null;

    private final Map<String, Texture> mTextures;

    private TextureManager() {
        mTextures = new HashMap<>();
    }

    public TextureManager getInstance() {
        if (sTextureManager == null) {
            sTextureManager = new TextureManager();
        }

        return sTextureManager;
    }
}
