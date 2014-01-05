package com.jcxavier.android.opengl.engine.cache;

import static android.opengl.GLES20.*;

/**
 * @author jxav
 */
public final class GLState {

    private static int sVertexAttribArraysEnabled;
    private static int sShaderProgram;
    private static int sActiveTexture;
    private static int sBoundTexture;
    private static int sBlendSourceFactor;
    private static int sBlendDestFactor;
    private static int sBlendEquation;

    /**
     * Clears the GL state. This should be called with every GL context creation.
     */
    public static void clean() {
        sVertexAttribArraysEnabled = 0;
        sShaderProgram = -1;
        sActiveTexture = -1;
        sBoundTexture = -1;
        sBlendSourceFactor = -1;
        sBlendDestFactor = -1;
        sBlendEquation = -1;
    }

    /**
     * This method takes the number of desired vertex attribute arrays wanted, and indexes them by 0. A call with size 2
     * will guarantee that only the vertex attribute array 0 and 1 are enabled.
     *
     * @param size the number of vertex attribute arrays to be set
     */
    public static void cachedGlVertexAttribArraySize(final int size) {
        if (size > sVertexAttribArraysEnabled) {
            // enable the new vertex attribute arrays
            for (int i = sVertexAttribArraysEnabled; i < size; i++) {
                glEnableVertexAttribArray(i);
            }

            sVertexAttribArraysEnabled = size;
        } else if (size < sVertexAttribArraysEnabled) {
            // disable the unneeded vertex attribute arrays
            for (int i = sVertexAttribArraysEnabled - 1; i >= size; i--) {
                glDisableVertexAttribArray(i);
            }

            sVertexAttribArraysEnabled = size;
        }
    }

    public static void cachedGlUseProgram(final int program) {
        if (program != sShaderProgram) {
            sShaderProgram = program;
            glUseProgram(program);
        }
    }

    public static void cachedGlActiveTexture(final int texture) {
        if (texture != sActiveTexture) {
            sActiveTexture = texture;
            // reset bound texture as well
            sBoundTexture = -1;
            glActiveTexture(texture);
        }
    }

    public static void cachedGlBindTexture(final int texture) {
        if (texture != sBoundTexture) {
            sBoundTexture = texture;
            glBindTexture(GL_TEXTURE0, texture);
        }
    }

    public static void cachedGlBlendFunc(final int sfactor, final int dfactor) {
        if (sfactor != sBlendSourceFactor || dfactor != sBlendDestFactor) {
            sBlendSourceFactor = sfactor;
            sBlendDestFactor = dfactor;
            glBlendFunc(sfactor, dfactor);
        }
    }

    public static void cachedGlBlendEquation(final int mode) {
        if (mode != sBlendEquation) {
            sBlendEquation = mode;
            glBlendEquation(mode);
        }
    }

    private GLState() {
        // can't be instantiated
    }
}
