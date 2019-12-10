package com.Penguinz22.Rex.utils.texture;

import org.joml.Vector2f;

import java.io.File;

public class TextureAtlasData extends TextureData {

    private Vector2f unitSize;

    public TextureAtlasData(String file, Format format, Vector2f unitSize) {
        super(file, format);
        this.unitSize = unitSize;
    }

    public Vector2f getUnitSize() {
        return unitSize;
    }

    public static class Factory {
        public static TextureAtlasData loadFromFile(String filePath, Format format, Vector2f unitSize) {
            return new TextureAtlasData(filePath, format, unitSize);
        }
    }
}
