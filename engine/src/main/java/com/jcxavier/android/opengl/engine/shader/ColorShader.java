package com.jcxavier.android.opengl.engine.shader;

import com.jcxavier.android.opengl.math.Vector4;

import static android.opengl.GLES20.*;
import static com.jcxavier.android.opengl.util.Constants.FLOAT_SIZE;

/**
 * Created on 11/03/2014.
 *
 * @author João Xavier <jcxavier@jcxavier.com>
 */
public class ColorShader extends Shader {

    public ColorShader() {
        mAttributesArray = new String[] { "a_Position" };

        mUniformMapping.put("u_MVPMatrix", null);
        mUniformMapping.put("u_Color", null);
    }

    @Override
    public void setAttributePointers() {
        glEnableVertexAttribArray(0);

        // 4 bytes position * FLOAT_SIZE
        final int stride = 4 * FLOAT_SIZE;

        glVertexAttribPointer(0, 4, GL_FLOAT, false, stride, 0);
    }

    public void setMVPMatrixUniform(final float[] matrix) {
        glUniformMatrix4fv(mUniformMapping.get("u_MVPMatrix"), 1, false, matrix, 0);
    }

    public void setColorUniform(final Vector4 color) {
        glUniform4f(mUniformMapping.get("u_Color"), color.x, color.y, color.z, color.w);
    }

    @Override
    protected String getVertexShader() {
        return "" +
                "attribute highp   vec4  a_Position;\n" +
                "\n" +
                "uniform highp     mat4  u_MVPMatrix;\n" +
                "uniform lowp      vec4  u_Color;\n" +
                "\n" +
                "varying lowp      vec4  v_Color;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_Position = u_MVPMatrix * a_Position;\n" +
                "    v_Color = u_Color;\n" +
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
