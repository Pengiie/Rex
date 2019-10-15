package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Disposable;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.CallbackI;

import java.io.*;

public class Shader implements Disposable {

    public static final String POSITION_ATTRIBUTE = "in_position";

    public static final String COLOR_ATTRIBUTE = "in_color";

    private int programHandle;
    private int vertexHandle;
    private int fragmentHandle;

    public Shader(String vertexShader, String fragmentShader) {
        this.programHandle = GL20.glCreateProgram();
        this.vertexHandle = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
        this.fragmentHandle = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);

        GL20.glAttachShader(programHandle, vertexHandle);
        GL20.glAttachShader(programHandle, fragmentHandle);
        GL20.glLinkProgram(programHandle);


    }

    private int loadShader(String source, int type) {
        int handle = GL20.glCreateShader(type);
        GL20.glShaderSource(handle, source);
        GL20.glCompileShader(handle);
        if(GL20.glGetShaderi(handle, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException(GL20.glGetShaderInfoLog(handle));
        }
        return handle;
    }

    @Override
    public void dispose() {

    }
}
