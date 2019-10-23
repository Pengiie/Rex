package com.Penguinz22.Rex.graphics;

public class VertexAttribute {

    private int usage;
    private int size;

    float[] data;

    public VertexAttribute(int usage, int size, float[] data) {
        this.usage = usage;
        this.size = size;
        this.data = data;
    }

    public int getUsage() {
        return usage;
    }

    public int getSize() {
        return size;
    }

    public static final class Usage {
        public static final int POSITION = 0;
        public static final int TEXTURE_COORDS = 1;
    }

}
