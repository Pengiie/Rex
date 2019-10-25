package com.Penguinz22.Rex.graphics;

public class MeshData {

    static final MeshData rectData = new MeshData(new float[] {
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

    final float[] positions;
    final float[] textureCoords;

    public MeshData(float[] positions, float[] textureCoords) {
        this.positions = positions;
        this.textureCoords = textureCoords;
    }

}
