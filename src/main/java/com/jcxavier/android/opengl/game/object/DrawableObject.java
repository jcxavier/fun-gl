package com.jcxavier.android.opengl.game.object;

import com.jcxavier.android.opengl.engine.shader.ColorAlphaShaderProgram;
import com.jcxavier.android.opengl.engine.shader.ShaderCache;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class DrawableObject extends GameObject {

    private final Matrix4 mMvpMatrix;
    private final ColorAlphaShaderProgram mShader;
    private int mVertexBufferHandle;
    private FloatBuffer mVertexBuffer;

    private final Vector3 mColor;

    public DrawableObject() {
        mMvpMatrix = new Matrix4();
        mShader = (ColorAlphaShaderProgram)
                ShaderCache.getSharedShaderCache().getShaderProgram(ColorAlphaShaderProgram.class);

        generateBuffers();
        populateBuffers(true);

        mColor = new Vector3(1, 1, 1);
    }

    protected void generateBuffers() {
        int[] buffers = { 0 };
        glGenBuffers(1, buffers, 0);
        mVertexBufferHandle = buffers[0];
    }

    protected void populateBuffers(final boolean fromScratch) {
        if (fromScratch) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8 * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.position(0);
            mVertexBuffer = byteBuffer.asFloatBuffer();
        }

        updateBufferInformation();

        glBindBuffer(GL_ARRAY_BUFFER, mVertexBufferHandle);

        mVertexBuffer.position(0);

        if (fromScratch) {
            glBufferData(GL_ARRAY_BUFFER, mVertexBuffer.capacity(), mVertexBuffer, GL_DYNAMIC_DRAW);
        } else {
            glBufferSubData(GL_ARRAY_BUFFER, 0, mVertexBuffer.capacity(), mVertexBuffer);
        }
    }

    private void updateBufferInformation() {
        mVertexBuffer.position(0);

        // 0 ---- 3
        //
        // |  \   |
        // |   \  |
        //
        // 1 ---- 2

        for (int i = 0; i < 4; i++) {
            // position
            if (i == 0 || i == 1) {
                mVertexBuffer.put(mPosition.x);
            } else {
                mVertexBuffer.put(mPosition.x + mSize.x);
            }

            if (i == 0 || i == 3) {
                mVertexBuffer.put(mPosition.y);
            } else {
                mVertexBuffer.put(mPosition.y + mSize.y);
            }

            mVertexBuffer.put(mPosition.z);
            mVertexBuffer.put(1.0f);

            // color
            mVertexBuffer.put(mColor.x);
            mVertexBuffer.put(mColor.y);
            mVertexBuffer.put(mColor.z);
            mVertexBuffer.put(1.0f);
        }
    }

    @Override
    public void update(Matrix4 projectionMatrix) {
        mMvpMatrix.set(projectionMatrix);
        mMvpMatrix.multiply(mModelMatrix);
    }

    @Override
    public void draw() {
        glUseProgram(mShader.getProgram());

        glBindBuffer(GL_ARRAY_BUFFER, mVertexBufferHandle);

        mShader.setAttributePointers();
        mShader.setMVPMatrixUniform(mMvpMatrix.m);
        mShader.setAlphaUniform(mAlpha);

        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
    }

    @Override
    public void setSize(final Vector2 size) {
        super.setSize(size);
        populateBuffers(false);
    }

    public void setColor(final Vector3 color) {
        mColor.set(color);
        populateBuffers(false);
    }
}
