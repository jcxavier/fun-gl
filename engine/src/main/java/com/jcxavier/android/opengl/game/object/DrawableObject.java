package com.jcxavier.android.opengl.game.object;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.jcxavier.android.opengl.engine.shader.ColorShader;
import com.jcxavier.android.opengl.engine.shader.ShaderManager;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;
import com.jcxavier.android.opengl.math.Vector4;

import static android.opengl.GLES20.*;
import static com.jcxavier.android.opengl.engine.cache.GLState.*;
import static com.jcxavier.android.opengl.util.Constants.FLOAT_SIZE;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class DrawableObject extends GameObject {

    private final Matrix4 mMvpMatrix;
    private final ColorShader mShader;
    private int mVertexBufferHandle;
    private FloatBuffer mVertexBuffer;

    private final Vector4 mColor;

    public DrawableObject() {
        mColor = new Vector4();
        mMvpMatrix = new Matrix4();

        mShader = (ColorShader) ShaderManager.getInstance().getShader(ColorShader.class);

        generateBuffer();
        populateBuffer(true);
    }

    @Override
    public void clean() {
        int[] buffer = { mVertexBufferHandle };
        glDeleteBuffers(1, buffer, 0);
        mVertexBufferHandle = 0;
    }

    protected void generateBuffer() {
        int[] buffer = { 0 };
        glGenBuffers(1, buffer, 0);
        mVertexBufferHandle = buffer[0];
    }

    protected void populateBuffer(final boolean fromScratch) {
        boolean createNewBuffer = fromScratch;

        // the buffer was deleted or wasn't generated for some reason
        if (mVertexBufferHandle == 0) {
            generateBuffer();
            createNewBuffer = true;
        }

        if (createNewBuffer) {
            // number of vertices
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(mShader.getAttributeBufferSize() * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.position(0);
            mVertexBuffer = byteBuffer.asFloatBuffer();
        }

        updateVertexBufferInformation();

        glBindBuffer(GL_ARRAY_BUFFER, mVertexBufferHandle);

        mVertexBuffer.position(0);

        // the capacity of the float buffer is its size with float stride, so we must multiply by the float size to get
        // the actual byte size
        if (createNewBuffer) {
            glBufferData(GL_ARRAY_BUFFER, mVertexBuffer.capacity() * FLOAT_SIZE, mVertexBuffer, GL_DYNAMIC_DRAW);
        } else {
            glBufferSubData(GL_ARRAY_BUFFER, 0, mVertexBuffer.capacity() * FLOAT_SIZE, mVertexBuffer);
        }
    }

    private void updateVertexBufferInformation() {
        mVertexBuffer.position(0);

        // 0 ---- 3
        //
        // |  \   |
        // |   \  |
        //
        // 1 ---- 2

        for (int i = 0; i < 4; i++) {
            if (i == 0 || i == 1) {
                mVertexBuffer.put(0.0f);
            } else {
                mVertexBuffer.put(mSize.x);
            }

            if (i == 0 || i == 3) {
                mVertexBuffer.put(0.0f);
            } else {
                mVertexBuffer.put(mSize.y);
            }

            mVertexBuffer.put(0.0f);

            // there's no need for a 4th parameter, but it would get expanded to a vec4 in the shader anyway
            mVertexBuffer.put(1.0f);
        }
    }

    @Override
    protected void updatePostTransformations(final Matrix4 projectionMatrix) {
        mMvpMatrix.set(projectionMatrix);
        mMvpMatrix.multiply(mModelMatrix);

        mColor.w = mAlpha;
    }

    @Override
    public void draw() {
        cachedGlUseProgram(mShader.getProgram());

        // additive alpha blending
        cachedGlBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        cachedGlBlendEquation(GL_FUNC_ADD);

        glBindBuffer(GL_ARRAY_BUFFER, mVertexBufferHandle);

        mShader.setAttributePointers();
        mShader.setMVPMatrixUniform(mMvpMatrix.m);
        mShader.setColorUniform(mColor);

        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
    }

    @Override
    public void setSize(final Vector2 size) {
        super.setSize(size);
        populateBuffer(false);
    }

    public void setColor(final Vector3 color) {
        mColor.x = color.x;
        mColor.y = color.y;
        mColor.z = color.z;
    }
}
