package com.Penguinz22.Rex.gui;

import com.Penguinz22.Rex.graphics.BatchShader;
import com.Penguinz22.Rex.graphics.Shader;
import com.Penguinz22.Rex.utils.Strings;

public class GuiShader {

    private static final String PASS_TEXTURE_COORDS = "p_textureCoords";

    public static final String TEXTURE_OFFSET_UNIFORM = "u_textureOffset";
    public static final String TEXTURE_UNIT_SIZE_UNIFORM = "u_textureUnitSize";
    public static final String TEXTURE_HAS_UNIFORM = "u_hasTexture";

    private static final String vertexShader =
            Strings.join("\n",
                    "attribute vec2 "+Shader.POSITION_ATTRIBUTE+";",
                    "attribute vec2 "+ Shader.TEXTURE_COORD_ATTRIBUTE+";",
                    "",
                    "out vec2 "+ GuiShader.PASS_TEXTURE_COORDS+";",
                    "",
                    "uniform mat4 "+Shader.COMBINED_MATRIX_UNIFORM+";",
                    "uniform vec2 "+BatchShader.TEXTURE_OFFSET_UNIFORM+";",
                    "uniform vec2 "+BatchShader.TEXTURE_UNIT_SIZE_UNIFORM+";",
                    "",
                    "void main(){",
                    "    gl_Position = "+Shader.COMBINED_MATRIX_UNIFORM+"*vec4("+Shader.POSITION_ATTRIBUTE+", 0, 1);",
                    "    "+ GuiShader.PASS_TEXTURE_COORDS+" = "+BatchShader.TEXTURE_OFFSET_UNIFORM+"+("+BatchShader.TEXTURE_UNIT_SIZE_UNIFORM+"*"+Shader.TEXTURE_COORD_ATTRIBUTE+");",
                    "}");

    private static final String fragmentShader =
            Strings.join("\n",
                    "in vec2 "+ GuiShader.PASS_TEXTURE_COORDS+";",
                    "",
                    "uniform vec4 "+Shader.COLOR_UNIFORM+";",
                    "uniform bool "+BatchShader.TEXTURE_HAS_UNIFORM+";",
                    "uniform sampler2D "+Shader.TEXTURE_SAMPLER+";",
                    "",
                    "void main(){",
                    "    vec4 textureColor = texture("+Shader.TEXTURE_SAMPLER+", "+GuiShader.PASS_TEXTURE_COORDS+");",
                    "    vec4 color = "+ Shader.COLOR_UNIFORM+";",
                    "    if("+BatchShader.TEXTURE_HAS_UNIFORM+") {",
                    "         color = color * textureColor;",
                    "    }",
                    "    gl_FragColor = color;",
                    "}");

    public static Shader create() {
        return new Shader(vertexShader, fragmentShader);
    }

}
