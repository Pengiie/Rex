package com.Penguinz22.Rex.tilemap;

import com.Penguinz22.Rex.graphics.BatchShader;
import com.Penguinz22.Rex.graphics.Shader;
import com.Penguinz22.Rex.utils.Strings;

public class TilemapShader {

    private static final String PASS_TEXTURE_COORDS = "p_textureCoords";
    public static final String ORTHO_VIEW_TRANSFORMATION_MATRIX = "u_orthoViewTransformation";

    private static final String vertexShader =
            Strings.join("\n",
                    "attribute vec2 "+Shader.POSITION_ATTRIBUTE+";",
                    "attribute vec2 "+ Shader.TEXTURE_COORD_ATTRIBUTE+";",
                    "",
                    "out vec2 "+TilemapShader.PASS_TEXTURE_COORDS+";",
                    "",
                    "uniform mat4 "+TilemapShader.ORTHO_VIEW_TRANSFORMATION_MATRIX +";",
                    "",
                    "void main(){",
                    "    gl_Position = "+ BatchShader.ORTHO_VIEW_TRANSFORMATION_MATRIX +"*vec4("+ Shader.POSITION_ATTRIBUTE+", 0, 1);",
                    "    "+TilemapShader.PASS_TEXTURE_COORDS+" = "+Shader.TEXTURE_COORD_ATTRIBUTE+";",
                    "}");

    private static final String fragmentShader =
            Strings.join("\n",
                    "in vec2 "+TilemapShader.PASS_TEXTURE_COORDS+";",
                    "",
                    "uniform sampler2D "+ Shader.TEXTURE_SAMPLER+";",
                    "",
                    "void main(){",
                    "    gl_FragColor = texture("+Shader.TEXTURE_SAMPLER+", "+TilemapShader.PASS_TEXTURE_COORDS+");",
                    //"   gl_FragColor = vec4(1,0,0,1);",
                    "}");

    public static Shader create() {
        return new Shader(vertexShader, fragmentShader);
    }

}
