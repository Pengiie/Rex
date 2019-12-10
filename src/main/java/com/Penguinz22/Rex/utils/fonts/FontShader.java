package com.Penguinz22.Rex.utils.fonts;

import com.Penguinz22.Rex.graphics.BatchShader;
import com.Penguinz22.Rex.graphics.Shader;
import com.Penguinz22.Rex.gui.GuiShader;
import com.Penguinz22.Rex.utils.Strings;

public class FontShader {

    private static final String PASS_TEXTURE_COORDS = "p_textureCoords";

    public static final String TEXTURE_OFFSET_UNIFORM = "u_textureOffset";
    public static final String TEXTURE_UNIT_SIZE_UNIFORM = "u_textureUnitSize";
    public static final String TEXTURE_HAS_UNIFORM = "u_hasTexture";

    private static final String vertexShader =
            Strings.join("\n",
                    "attribute vec4 vertex;",
                    "",
                    "out vec2 "+ FontShader.PASS_TEXTURE_COORDS+";",
                    "",
                    "uniform mat4 "+Shader.COMBINED_MATRIX_UNIFORM+";",
                    "",
                    "void main(){",
                    "    gl_Position = "+Shader.COMBINED_MATRIX_UNIFORM+"*vec4(vertex.xy, 0, 1);",
                    "    "+ FontShader.PASS_TEXTURE_COORDS+" = vertex.zw;",
                    "}");

    private static final String fragmentShader =
            Strings.join("\n",
                    "in vec2 "+ FontShader.PASS_TEXTURE_COORDS+";",
                    "",
                    "uniform vec4 "+Shader.COLOR_UNIFORM+";",
                    "uniform sampler2D "+Shader.TEXTURE_SAMPLER+";",
                    "",
                    "void main(){",
                    "    vec4 sampled = vec4(1,1,1,texture("+Shader.TEXTURE_SAMPLER+", "+FontShader.PASS_TEXTURE_COORDS+"));",
                    "    gl_FragColor = sampled*"+ Shader.COLOR_UNIFORM+";",
                    "}");

    public static Shader create() {
        return new Shader(vertexShader, fragmentShader);
    }
}
