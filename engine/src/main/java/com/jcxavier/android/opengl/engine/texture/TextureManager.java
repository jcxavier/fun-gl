package com.jcxavier.android.opengl.engine.texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import com.jcxavier.android.opengl.file.FileManager;
import com.jcxavier.android.opengl.util.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES20.*;

/**
 * Created on 12/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class TextureManager {

    private static final String TAG = TextureManager.class.getSimpleName();

    private static TextureManager sTextureManager = null;

    private final Map<String, Texture> mTextures;
    private int mMaxTextureSize;

    private TextureManager() {
        mTextures = new HashMap<>();
        mMaxTextureSize = 0;
    }

    public static TextureManager getInstance() {
        if (sTextureManager == null) {
            sTextureManager = new TextureManager();
        }

        return sTextureManager;
    }

    public void clean() {
        Collection<Texture> textures = mTextures.values();
        int[] buffer = new int[textures.size()];
        int idx = 0;

        for (Texture texture : textures) {
            buffer[idx] = texture.getName();
            idx++;
        }

        // remove information from GL
        glDeleteTextures(buffer.length, buffer, 0);

        mTextures.clear();
    }

    public void readMaxTextureSize() {
        int[] buffer = { 0 };
        glGetIntegerv(GL_MAX_TEXTURE_SIZE, buffer, 0);
        mMaxTextureSize = buffer[0];
    }

    public void preload(final String imageName) {
        load(imageName);
    }

    public Texture load(final String imageName) {
        if (mMaxTextureSize == 0) {
            throw new IllegalStateException("GL_MAX_TEXTURE_SIZE must be read before attempting to load textures.");
        }

        Texture texture = mTextures.get(imageName);

        if (texture == null) {
            // texture is not yet cached, needs to be loaded onto memory
            Bitmap image = readBitmap(imageName);
            int textureName = createTexture();
            texture = loadTextureToGL(image, textureName);

            if (texture != null) {
                // cache the texture if it is valid
                mTextures.put(imageName, texture);

                Log.d(TAG, String.format("Loaded texture %s: %s", imageName, texture));
            }
        }

        return texture;
    }

    private Bitmap readBitmap(final String imageName) {
        try {
            // load the input stream into a byte array
            InputStream is = FileManager.getInstance().readFile(imageName);
            byte[] imageData = BitmapUtils.readInputStreamAsByteArray(is);
            is.close();

            // try to load the bitmap in the original size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 1;

            // read out size
            BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

            while (options.outHeight > mMaxTextureSize || options.outWidth > mMaxTextureSize) {
                // retry, with 1/2 of the size of the texture
                options.inSampleSize *= 2;
                BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
            }

            // now decode the actual bitmap
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
        } catch (IOException ioe) {
            Log.e(TAG, String.format("Texture %s couldn't be read! %s", imageName, ioe.getMessage()));
        }

        return null;
    }

    private static int createTexture() {
        // generate a new OpenGL texture name and bind to it for sending the pixel data to OpenGL
        int[] buffer = { 0 };
        glGenTextures(1, buffer, 0);
        glBindTexture(GL_TEXTURE_2D, buffer[0]);

        return buffer[0];
    }

    private static Texture loadTextureToGL(final Bitmap image, final int textureName) {
        if (image == null || textureName == 0) {
            // there was a problem either in loading the image or generating the texture name
            return null;
        }

        // read image information
        int width = image.getWidth();
        int height = image.getHeight();
        boolean premultipliedAlpha = image.getConfig() != Bitmap.Config.RGB_565 && image.hasAlpha();

        Texture texture = new Texture(textureName, width, height, premultipliedAlpha);
        texture.configureTextureParameters();

        // load image
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, image, 0);

        // check for GL error
        int glError = glGetError();

        // recycle bitmap, it won't be used again
        image.recycle();

        if (glError != GL_NO_ERROR) {
            Log.e(TAG, "Error loading bitmap: " + glError);
            // keep going, this might not be 100% fatal
        }

        return texture;
    }
}
