package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Disposable;

public class BatchRenderer implements Disposable {

    private Mesh mesh;
    private final Shader shader;

    public BatchRenderer() {
        this(null);
    }

    public BatchRenderer(Shader shader) {

        if(shader == null) {
            this.shader = FlatShader.create();
        } else {
            this.shader = shader;
        }
    }

    public void flush() {

    }


    @Override
    public void dispose() {

    }
}
