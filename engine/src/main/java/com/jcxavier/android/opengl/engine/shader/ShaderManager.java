package com.jcxavier.android.opengl.engine.shader;

import com.jcxavier.android.opengl.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public final class ShaderManager {

    private static ShaderManager sShaderManager = null;

    private final Map<Class<? extends Shader>, Shader> mShaders;

    private ShaderManager() {
        mShaders = new HashMap<>();
    }

    /**
     * Retrieves the shader manager singleton.
     *
     * @return the shader manager singleton
     */
    public static ShaderManager getInstance() {
        if (sShaderManager == null) {
            sShaderManager = new ShaderManager();
        }

        return sShaderManager;
    }

    /**
     * Safely cleans from memory and purges the shader cache and all its programs.
     */
    public void clean() {
        for (Shader program : mShaders.values()) {
            program.clean();
        }

        mShaders.clear();
    }

    /**
     * Retrieves the shader program for the given shader program class, lazily loading and compiling it if needed. Will
     * fail with an {@link IllegalStateException} if the shader program didn't compile successfully.
     *
     * @param shaderClass the shader program class type
     * @return the created shader program
     */
    public Shader getShader(Class<? extends Shader> shaderClass) {
        Shader shader = mShaders.get(shaderClass);

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
}
