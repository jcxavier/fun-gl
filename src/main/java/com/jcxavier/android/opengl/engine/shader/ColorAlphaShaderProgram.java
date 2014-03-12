package com.jcxavier.android.opengl.engine.shader;

import static android.opengl.GLES20.*;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class ColorAlphaShaderProgram extends ShaderProgram {

    public ColorAlphaShaderProgram() {
        mAttributesArray = new String[] { "a_Position", "a_Color" };

        mUniformMapping.put("u_MVPMatrix", null);
        mUniformMapping.put("u_Alpha", null);
    }

    @Override
    public void setAttributePointers() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        final int stride = 8 * 4;

        glVertexAttribPointer(0, 4, GL_FLOAT, false, stride, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, stride, 4 * 4);
    }

    public void setMVPMatrixUniform(final float[] matrix) {
        glUniform4fv(mUniformMapping.get("u_MVPMatrix"), 1, matrix, 0);
    }

    public void setAlphaUniform(final float alpha) {
        glUniform1f(mUniformMapping.get("u_Alpha"), alpha);
    }

    @Override
    protected String getVertexShader() {
        return "" +
                "attribute highp   vec4  a_Position;\n" +
                "attribute lowp    vec4  a_Color;\n" +
                "\n" +
                "uniform highp     mat4  u_MVPMatrix;\n" +
                "uniform lowp     float  u_Alpha;\n" +
                "\n" +
                "varying lowp      vec4  v_Color;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_Position = u_MVPMatrix * vec4(a_Position.xyz, 1.0);\n" +
                "    v_Color = vec4(a_Color.rgb, u_Alpha);\n" +
                "}\n";
    }

    @Override
    protected String getFragmentShader() {
        return "" +
                "varying lowp      vec4  v_Color;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_FragColor = v_Color;\n" +
                "}\n";
    }
}
