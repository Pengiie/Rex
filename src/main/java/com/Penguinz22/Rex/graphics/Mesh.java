package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Disposable;
import org.lwjgl.opengl.GL30;

public class Mesh implements Disposable {

    private int vaoHandle;

    public Mesh() {
        createVao();
    }

    private void createVao() {
        this.vaoHandle = GL30.glGenVertexArrays();
    }

    @Override
    public void dispose() {
        GL30.glDeleteVertexArrays(vaoHandle);
    }
}
