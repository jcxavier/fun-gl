package com.jcxavier.android.opengl.engine.shader;

import com.jcxavier.android.opengl.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class ShaderCache {

    private static ShaderCache sSharedShaderCache = null;

    private final Map<Class<? extends ShaderProgram>, ShaderProgram> mShaders;

    private ShaderCache() {
        mShaders = new HashMap<>();
    }

    /**
     * Retrieves the shader cache singleton.
     *
     * @return the shared shader cache singleton
     */
    public static ShaderCache getSharedShaderCache() {
        if (sSharedShaderCache == null) {
            sSharedShaderCache = new ShaderCache();
        }

        return sSharedShaderCache;
    }

    /**
     * Safely cleans from memory and purges the shader cache and all its programs.
     */
    public static void purgeSharedShaderCache() {
        if (sSharedShaderCache != null) {
            sSharedShaderCache.deleteAllShaderPrograms();
        }

        sSharedShaderCache = null;
    }

    /**
     * Retrieves the shader program for the given shader program class, lazily loading and compiling it if needed. Will
     * fail with an {@link IllegalStateException} if the shader program didn't compile successfully.
     *
     * @param shaderClass the shader program class type
     * @return the created shader program
     */
    public ShaderProgram getShaderProgram(Class<? extends ShaderProgram> shaderClass) {
        ShaderProgram shader = mShaders.get(shaderClass);

        if (shader == null) {
            shader = ReflectionUtils.makeObjectOfType(shaderClass, null);

            if (shader.compile()) {
                mShaders.put(shaderClass, shader);
            } else {
                return null;
            }
        }

        if (shader == null) {
            throw new IllegalStateException("Failed to load shader program " + shaderClass.getSimpleName());
        }

        return shader;
    }

    private void deleteAllShaderPrograms() {
        for (ShaderProgram program : mShaders.values()) {
            program.tearDownGL();
        }

        mShaders.clear();
    }
}
