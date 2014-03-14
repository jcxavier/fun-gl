package com.jcxavier.android.opengl.game.object;

import com.jcxavier.android.opengl.engine.shader.ShaderManager;
import com.jcxavier.android.opengl.engine.shader.TextureShader;
import com.jcxavier.android.opengl.engine.texture.Texture;
import com.jcxavier.android.opengl.engine.texture.TextureManager;
import com.jcxavier.android.opengl.math.Matrix4;
import com.jcxavier.android.opengl.math.Vector2;
import com.jcxavier.android.opengl.math.Vector3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;
import static com.jcxavier.android.opengl.util.Constants.FLOAT_SIZE;

/**
 * Created on 13/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class Sprite extends GameObject {

    private final Vector3[] mPositions;
    private final Vector2[] mUvs;

    private final Matrix4 mMvpMatrix;
    private final TextureShader mShader;
    private Texture mTexture;

    private int mVertexBufferHandle;
    private FloatBuffer mVertexBuffer;

    public Sprite(final String textureName) {
        this(TextureManager.getInstance().load(textureName));
    }

    public Sprite(final Texture texture) {
        mMvpMatrix = new Matrix4();
        mShader = (TextureShader) ShaderManager.getInstance().getShader(TextureShader.class);

        // 0 ---- 3
        //
        // |  \   |
        // |   \  |
        //
        // 1 ---- 2

        mPositions = new Vector3[4];
        for (int i = mPositions.length - 1; i >= 0; i--) {
            mPositions[i] = new Vector3();
        }
        // positions will be updated upon texture set

        mUvs = new Vector2[4];
        mUvs[0] = new Vector2(0.0f, 0.0f);
        mUvs[1] = new Vector2(0.0f, 1.0f);
        mUvs[2] = new Vector2(1.0f, 1.0f);
        mUvs[3] = new Vector2(1.0f, 0.0f);

        // sets texture, size and creates buffers
        setTexture(texture, true);
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

    private void updatePositions() {
        mPositions[0].set(0.0f, 0.0f, 0.0f);
        mPositions[1].set(0.0f, mSize.y, 0.0f);
        mPositions[2].set(mSize.x, mSize.y, 0.0f);
        mPositions[3].set(mSize.x, 0.0f, 0.0f);
    }

    private void updateVertexBufferInformation() {
        mVertexBuffer.position(0);

        for (int i = 0; i < 4; i++) {
            mVertexBuffer.put(mPositions[i].x);
            mVertexBuffer.put(mPositions[i].y);
            mVertexBuffer.put(mPositions[i].z);

            // there's no need for a 4th parameter, but it would get expanded to a vec4 in the shader anyway
            mVertexBuffer.put(1.0f);

            // serialize the UVs
            mVertexBuffer.put(mUvs[i].x);
            mVertexBuffer.put(mUvs[i].y);
        }
    }

    public final void setTexture(final Texture texture, final boolean resize) {
        mTexture = texture;

        if (resize) {
            setSize(new Vector2(mTexture.getWidth(), mTexture.getHeight()));
            populateBuffer(false);
        }
    }

    @Override
    public void setSize(final Vector2 size) {
        super.setSize(size);

        // update the positions with the new size
        updatePositions();
    }

    @Override
    protected void updatePostTransformations(final Matrix4 projectionMatrix) {
        mMvpMatrix.set(projectionMatrix);
        mMvpMatrix.multiply(mModelMatrix);
    }

    @Override
    public void draw() {
        glUseProgram(mShader.getProgram());

        // bind the texture, if available
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mTexture != null ? mTexture.getName() : 0);

        // additive alpha blending
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glBlendEquation(GL_FUNC_ADD);

        glBindBuffer(GL_ARRAY_BUFFER, mVertexBufferHandle);

        // configure shader
        mShader.setAttributePointers();
        mShader.setMVPMatrixUniform(mMvpMatrix.m);
        mShader.setAlphaUniform(mAlpha);
        mShader.setTextureUniform(0);

        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
    }
}
