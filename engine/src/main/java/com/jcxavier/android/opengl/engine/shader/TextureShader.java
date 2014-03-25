package com.jcxavier.android.opengl.engine.shader;

import static android.opengl.GLES20.*;
import static com.jcxavier.android.opengl.engine.cache.GLState.cachedGlVertexAttribArraySize;
import static com.jcxavier.android.opengl.util.Constants.FLOAT_SIZE;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class TextureShader extends Shader {

    // (4 bytes position + 2 bytes uvs) * FLOAT_SIZE
    private static final int STRIDE = (4 + 2) * FLOAT_SIZE;

    private int mUniformLocationMVPMatrix;
    private int mUniformLocationAlpha;
    private int mUniformLocationTexture;

    public TextureShader() {
        mAttributesArray = new String[] { "a_Position", "a_TexCoord" };

        mUniformMapping.put("u_MVPMatrix", null);
        mUniformMapping.put("u_Alpha", null);
        mUniformMapping.put("s_Texture", null);
    }

    @Override
    public int getAttributeBufferSize() {
        return STRIDE;
    }

    @Override
    public void setAttributePointers() {
        cachedGlVertexAttribArraySize(2);

        glVertexAttribPointer(0, 4, GL_FLOAT, false, STRIDE, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, STRIDE, 4 * FLOAT_SIZE);
    }

    @Override
    protected void onUniformLocationsAvailable() {
        mUniformLocationMVPMatrix = mUniformMapping.get("u_MVPMatrix");
        mUniformLocationAlpha = mUniformMapping.get("u_Alpha");
        mUniformLocationTexture = mUniformMapping.get("s_Texture");
    }

    public void setMVPMatrixUniform(final float[] matrix) {
        glUniformMatrix4fv(mUniformLocationMVPMatrix, 1, false, matrix, 0);
    }

    public void setAlphaUniform(final float alpha) {
        glUniform1f(mUniformLocationAlpha, alpha);
    }

    public void setTextureUniform(final int textureUnit) {
        glUniform1i(mUniformLocationTexture, textureUnit);
    }

    @Override
    protected String getVertexShader() {
        return "" +
                "attribute highp   vec4  a_Position;\n" +
                "attribute highp   vec2  a_TexCoord;\n" +
                "\n" +
                "uniform highp     mat4  u_MVPMatrix;\n" +
                "uniform lowp     float  u_Alpha;\n" +
                "\n" +
                "varying highp     vec2  v_TexCoord;\n" +
                "varying lowp     float  v_Alpha;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_Position = u_MVPMatrix * a_Position;\n" +
                "    v_TexCoord = a_TexCoord;\n" +
                "    v_Alpha = u_Alpha;\n" +
                "}\n";
    }

    @Override
    protected String getFragmentShader() {
        return "" +
                "uniform sampler2D       s_Texture;\n" +
                "\n" +
                "varying highp     vec2  v_TexCoord;\n" +
                "varying lowp     float  v_Alpha;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_FragColor = texture2D(s_Texture, v_TexCoord);\n" +
                "    gl_FragColor.a *= v_Alpha;\n" +
                "}\n";
    }
}
