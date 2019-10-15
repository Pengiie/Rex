package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Strings;

public class FlatShader {

    private static final String vertexShader =
            Strings.join("\n",
                    "attribute vec2 "+Shader.POSITION_ATTRIBUTE+";",
                    "attribute vec4 "+Shader.COLOR_ATTRIBUTE+";",
                    "varying vec4 pass_color;",
                    "",
                    "void main(){",
                    "    pass_color = "+Shader.COLOR_ATTRIBUTE+";",
                    "    gl_Position = "+Shader.POSITION_ATTRIBUTE+";",
                    "}");

    private static final String fragmentShader =
            Strings.join("\n",
                    "varying vec4 pass_color;",
                    "",
                    "void main(){",
                    "    gl_FragColor = pass_color;",
                    "}");

    public static Shader create() {
        return new Shader(vertexShader, fragmentShader);
    }

}
