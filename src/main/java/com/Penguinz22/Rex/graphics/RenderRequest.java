package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Color;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class RenderRequest {

    final Matrix4f transformation;
    final Vector2f textureOffset;
    final Vector2f textureSize;
    final Color color;

    public RenderRequest(Matrix4f transformation, Vector2f textureOffset, Vector2f textureSize, Color color) {
        this.transformation = transformation;
        this.textureOffset = textureOffset;
        this.textureSize = textureSize;
        this.color = color;
    }
}
