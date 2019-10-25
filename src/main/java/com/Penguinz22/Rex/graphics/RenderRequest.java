package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Color;
import org.joml.Matrix4f;

public class RenderRequest {

    final Matrix4f transformation;
    final Color color;

    public RenderRequest(Matrix4f transformation, Color color) {
        this.transformation = transformation;
        this.color = color;
    }
}
