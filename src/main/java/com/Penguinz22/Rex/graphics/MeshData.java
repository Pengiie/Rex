package com.Penguinz22.Rex.graphics;

public class MeshData {

    public static final MeshData rectData = new MeshData(new float[] {
            0, 0,
            1, 0,
            0, 1,
            1, 0,
            0, 1,
            1, 1
    }, new float[] {
            0, 0,
            1, 0,
            0, 1,
            1, 0,
            0, 1,
            1, 1
    });

    public final float[] positions;
    public final float[] textureCoords;

    public MeshData(float[] positions, float[] textureCoords) {
        this.positions = positions;
        this.textureCoords = textureCoords;
    }

}
