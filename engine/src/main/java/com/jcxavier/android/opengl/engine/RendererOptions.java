package com.jcxavier.android.opengl.engine;

import android.graphics.Bitmap;
import com.jcxavier.android.opengl.math.Vector3;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public interface RendererOptions {

    void setBackgroundColor(Vector3 backgroundColor);

    void setStencilBufferSize(int size);

    void setDepthBufferSize(int size);

    void setBitmapConfig(Bitmap.Config bitmapConfig);

    void setSamples(int numberOfSamples);

    void setBlendingEnabled(boolean enabled);

    void setBackfaceCullingEnabled(boolean enabled);
}
