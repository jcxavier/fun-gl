package com.jcxavier.android.opengl.engine.texture;

import static android.opengl.GLES20.*;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class Texture {

    private final int mName;
    private final int mWidth;
    private final int mHeight;
    private final boolean mPremultipliedAlpha;

    private TextureWrap mTextureWrap;
    private TextureFilteringMode mTextureFilteringMode;

    public Texture(final int name, final int width, final int height, final boolean premultipledAlpha) {
        mName = name;
        mWidth = width;
        mHeight = height;
        mPremultipliedAlpha = premultipledAlpha;

        // default texture parameters
        mTextureWrap = TextureWrap.REPEAT;
        mTextureFilteringMode = TextureFilteringMode.LINEAR;
    }

    void configureTextureParameters() {
        int wrapTarget = mTextureWrap == TextureWrap.REPEAT ? GL_REPEAT : GL_CLAMP_TO_EDGE;
        int filterTarget = mTextureFilteringMode == TextureFilteringMode.LINEAR ? GL_LINEAR : GL_NEAREST;

        glBindTexture(GL_TEXTURE_2D, mName);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapTarget);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapTarget);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filterTarget);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filterTarget);
    }

    public void setTextureFilteringMode(final TextureFilteringMode textureFilteringMode) {
        mTextureFilteringMode = textureFilteringMode;
        configureTextureParameters();
    }

    public void setTextureWrap(final TextureWrap textureWrap) {
        mTextureWrap = textureWrap;
        configureTextureParameters();
    }

    public int getName() {
        return mName;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public boolean hasPremultipliedAlpha() {
        return mPremultipliedAlpha;
    }

    @Override
    public String toString() {
        return "Texture{" +
                "name=" + mName +
                ", width=" + mWidth +
                ", height=" + mHeight +
                ", premultipliedAlpha=" + mPremultipliedAlpha +
                ", textureWrap=" + mTextureWrap +
                ", textureFilteringMode=" + mTextureFilteringMode +
                '}';
    }
}
