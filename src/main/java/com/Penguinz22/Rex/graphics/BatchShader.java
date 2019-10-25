package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Strings;

public class BatchShader {

    private static final String vertexShader =
            Strings.join("\n",
                    "attribute vec2 "+Shader.POSITION_ATTRIBUTE+";",
                    "",
                    "uniform mat4 "+Shader.COMBINED_MATRIX_UNIFORM+";",
                    "",
                    "void main(){",
                    "    gl_Position = "+Shader.COMBINED_MATRIX_UNIFORM+"*vec4("+Shader.POSITION_ATTRIBUTE+", 0, 1);",
                    "}");

    private static final String fragmentShader =
            Strings.join("\n",
                    "uniform vec4 "+Shader.COLOR_UNIFORM+";",
                    "",
                    "void main(){",
                    "    gl_FragColor = "+Shader.COLOR_UNIFORM+";",
                    "}");

    public static Shader create() {
        return new Shader(vertexShader, fragmentShader);
    }

}
