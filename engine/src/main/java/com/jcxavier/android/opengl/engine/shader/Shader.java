package com.jcxavier.android.opengl.engine.shader;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static android.opengl.GLES20.*;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public abstract class Shader {

    private static final String TAG = Shader.class.getSimpleName();

    private final int mProgram;

    /**
     * The mapping of the uniform variables to their respective locations
     */
    protected final Map<String, Integer> mUniformMapping;

    /**
     * The array of attributes contained in the vertex shader to be bound by index to their locations
     */
    protected String[] mAttributesArray;

    /**
     * Creates a shader program within the OpenGL context.
     */
    protected Shader() {
        mProgram = glCreateProgram();
        mUniformMapping = new HashMap<>();
    }

    /**
     * Deletes the shader program.
     */
    public void clean() {
        if (mProgram > 0) {
            glDeleteProgram(mProgram);
        }
    }

    /**
     * Compiles the vertex and fragment shaders, attaching them to the shader program and linking it. The shaders are
     * then detached and deleted, and will be implicitly freed once the program object is deleted. If the compilation
     * fails the allocated shaders are freed and their names invalidated.
     *
     * @return {@code true} if the shader program compiled succesfully, {@code false} otherwise
     */
    public boolean compile() {
        int[] vertShader = { 0 };
        int[] fragShader = { 0 };

        // create and compile vertex shader
        if (!compileShader(vertShader, GL_VERTEX_SHADER, getVertexShader())) {
            Log.e(TAG, "Failed to compile vertex shader");
            return false;
        }

        // create and compile fragment shader
        if (!compileShader(fragShader, GL_FRAGMENT_SHADER, getFragmentShader())) {
            Log.e(TAG, "Failed to compile fragment shader");
            return false;
        }

        // attach vertex shader to program
        glAttachShader(mProgram, vertShader[0]);

        // attach fragment shader to program
        glAttachShader(mProgram, fragShader[0]);

        // bind attribute locations. this needs to be done prior to linking
        for (int i = mAttributesArray.length - 1; i >= 0; i--) {
            glBindAttribLocation(mProgram, i, mAttributesArray[i]);
        }

        // link program
        if (!linkProgram(mProgram)) {
            Log.e(TAG, "Failed to link program: " + mProgram);

            if (vertShader[0] > 0) {
                glDeleteShader(vertShader[0]);
            }

            if (fragShader[0] > 0) {
                glDeleteShader(fragShader[0]);
            }

            if (mProgram > 0) {
                glDeleteProgram(mProgram);
            }

            return false;
        }

        // get uniform locations
        for (String key : mUniformMapping.keySet()) {
            int location = glGetUniformLocation(mProgram, key);
            mUniformMapping.put(key, location);
        }

        onUniformLocationsAvailable();

        // release vertex and fragment shaders
        if (vertShader[0] > 0) {
            glDetachShader(mProgram, vertShader[0]);
            glDeleteShader(vertShader[0]);
        }

        if (fragShader[0] > 0) {
            glDetachShader(mProgram, fragShader[0]);
            glDeleteShader(fragShader[0]);
        }

        return true;
    }

    private boolean compileShader(int[] shader, int type, String source) {
        if (source == null) {
            Log.e(TAG, "Failed to load shader");
            return false;
        }

        shader[0] = glCreateShader(type);
        glShaderSource(shader[0], source);
        glCompileShader(shader[0]);


        int[] buffer = { 0 };
        glGetShaderiv(shader[0], GL_COMPILE_STATUS, buffer, 0);

        if (buffer[0] == GL_FALSE) {
            glDeleteShader(shader[0]);
            return false;
        }

        return true;
    }

    private boolean linkProgram(int prog) {
        glLinkProgram(prog);

        int[] status = { 0 };
        glGetProgramiv(prog, GL_LINK_STATUS, status, 0);

        return status[0] != GL_FALSE;
    }

    /**
     * @return the program name as created by OpenGL.
     */
    public int getProgram() {
        return mProgram;
    }

    protected abstract void onUniformLocationsAvailable();

    /**
     * Sets up the pointers to the attribute locations.
     */
    public abstract void setAttributePointers();

    /**
     * Retrieves the vertex shader for the given shader program.
     *
     * @return the vertex shader to be compiled, or {@code null} if the shader wasn't found
     */
    protected abstract String getVertexShader();

    /**
     * Retrieves the fragment shader for the given shader program.
     *
     * @return the fragment shader to be compiled, or {@code null} if the shader wasn't found
     */
    protected abstract String getFragmentShader();
}
