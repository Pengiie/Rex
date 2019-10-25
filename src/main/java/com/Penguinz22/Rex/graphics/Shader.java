package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Disposable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;

public class Shader implements Disposable {

    public static final String POSITION_ATTRIBUTE = "in_position";
    public static final String TEXTURE_COORD_ATTRIBUTE = "in_texCoord";

    public static final String COLOR_UNIFORM = "u_color";
    public static final String COMBINED_MATRIX_UNIFORM = "u_combinedMatrix";

    private int programHandle;
    private int vertexHandle;
    private int fragmentHandle;

    private String[] uniformNames;
    private HashMap<String, Integer> uniforms = new HashMap<>();

    private float[] tempArray = new float[16];

    public Shader(String vertexShader, String fragmentShader) {
        this.programHandle = GL20.glCreateProgram();
        this.vertexHandle = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
        this.fragmentHandle = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);

        GL20.glAttachShader(programHandle, vertexHandle);
        GL20.glAttachShader(programHandle, fragmentHandle);
        GL20.glLinkProgram(programHandle);

        getUniforms();
    }

    public void start() {
        GL20.glUseProgram(programHandle);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    private int loadShader(String source, int type) {
        int handle = GL20.glCreateShader(type);
        GL20.glShaderSource(handle, "#version 400 core\n"+source);
        GL20.glCompileShader(handle);
        if(GL20.glGetShaderi(handle, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw new RuntimeException(GL20.glGetShaderInfoLog(handle));
        }
        return handle;
    }

    public void setUniformf(String uniformName, float value) {
        if(!hasUniformName(uniformName))
            return;
        GL20.glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniformVec4f(String uniformName, Vector4f value) {
        if(!hasUniformName(uniformName))
            return;
        GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    public void setUniformMat4(String uniformName, Matrix4f value) {
        if(!hasUniformName(uniformName))
            return;
        value.get(tempArray);
        GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, tempArray);
    }

    public boolean hasUniformName(String uniformName) {
        return uniforms.containsKey(uniformName);
    }

    private void getUniforms() {
        int uniformCount = GL20.glGetProgrami(programHandle, GL20.GL_ACTIVE_UNIFORMS);

        this.uniformNames = new String[uniformCount];

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer size = stack.mallocInt(1);
            IntBuffer type = stack.mallocInt(1);
            for (int i = 0; i < uniformCount; i++) {
                size.put(0, 1);
                type.clear();
                String name = GL20.glGetActiveUniform(programHandle, i, size, type);
                int location = GL20.glGetUniformLocation(programHandle, name);
                uniforms.put(name, location);
                uniformNames[i] = name;
            }
        }
    }

    @Override
    public void dispose() {

    }
}
