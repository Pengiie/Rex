package com.Penguinz22.Rex.utils.fonts;

import com.Penguinz22.Rex.assets.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Character {

    private Vector4f texCoords;
    private Vector2f size;
    private int advance;
    private int descent;

    public Character(Vector4f texCoords, Vector2f size, int advance, int descent) {
        this.texCoords = texCoords;
        this.size = size;
        this.advance = advance;
        this.descent = descent;
    }

    public int getDescent() {
        return descent;
    }

    public Vector4f getTexCoords() {
        return texCoords;
    }

    public Vector2f getSize() {
        return size;
    }

    public int getAdvance() {
        return advance;
    }
}
