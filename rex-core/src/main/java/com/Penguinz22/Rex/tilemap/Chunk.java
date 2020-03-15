package com.Penguinz22.Rex.tilemap;

import com.Penguinz22.Rex.graphics.Mesh;
import com.Penguinz22.Rex.graphics.VertexAttribute;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.HashMap;

public class Chunk {

    private final int size;
    private final int totalSize;

    private Mesh mesh;
    private Tileset tileset;

    private HashMap<Vector2i, Tile> tiles;

    public Chunk(Tileset tileset, int size) {
        this.size = size;
        this.totalSize = size*size;
        this.tiles = new HashMap<>();
        this.tileset = tileset;
    }

    public void setTile(int x, int y, Tile tile) {
        tiles.put(new Vector2i(x, y), tile);
        updateMesh();
    }

    public Tile getTile(int x, int y) {
        return tiles.getOrDefault(new Vector2i(x, y), null);
    }

    public void updateMesh() {
        float[] positions = new float[tiles.size()*12];
        float[] textureCoords = new float[tiles.size()*12];

        int index = 0;
        for (Vector2i position : tiles.keySet()) {
            System.out.println(tileset == null);
            Vector2f texturePosition = tileset.getId(tiles.get(position).getTextureId());

            positions[index] = position.x;
            positions[index+1] = position.y;
            textureCoords[index] = texturePosition.x;
            textureCoords[index+1] = texturePosition.y;

            index += 2;

            positions[index] = position.x + 1;
            positions[index+1] = position.y;
            textureCoords[index] = texturePosition.x + tileset.getSize().x;
            textureCoords[index+1] = texturePosition.y;

            index += 2;

            positions[index] = position.x;
            positions[index+1] = position.y + 1;
            textureCoords[index] = texturePosition.x;
            textureCoords[index+1] = texturePosition.y + tileset.getSize().y;

            index += 2;

            positions[index] = position.x + 1;
            positions[index+1] = position.y;
            textureCoords[index] = texturePosition.x + tileset.getSize().x;
            textureCoords[index+1] = texturePosition.y;

            index += 2;

            positions[index] = position.x;
            positions[index+1] = position.y + 1;
            textureCoords[index] = texturePosition.x;
            textureCoords[index+1] = texturePosition.y + tileset.getSize().y;

            index += 2;

            positions[index] = position.x + 1;
            positions[index+1] = position.y + 1;
            textureCoords[index] = texturePosition.x + tileset.getSize().x;
            textureCoords[index+1] = texturePosition.y + tileset.getSize().y;

            index += 2;
        }
        this.mesh = new Mesh(
                new VertexAttribute(VertexAttribute.Usage.POSITION, 2, positions),
                new VertexAttribute(VertexAttribute.Usage.TEXTURE_COORDS, 2,textureCoords));
    }

    public Mesh getMesh() {
        return mesh;
    }

    /** Do not make changes to this unless you call updateMesh()
     *
     * @return Hashmap of tiles and their positions
     */
    public HashMap<Vector2i, Tile> getTiles() {
        return tiles;
    }

    public int getSize() {
        return size;
    }

    public int getTotalSize() {
        return totalSize;
    }
}
