package com.Penguinz22.Rex.tilemap;

import com.Penguinz22.Rex.assets.TilemapData;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.HashMap;

public class Tilemap {

    private HashMap<Vector2i, Chunk> chunks;
    public final int chunkSize;
    public final int tileSize;

    private Tileset tileset;

    public Vector2f position = new Vector2f();

    public Tilemap(TilemapData data, int chunkSize, int tileSize) {
        this.chunkSize = chunkSize;
        this.tileSize = tileSize;
        loadData(data, chunkSize);
    }

    public void loadData(TilemapData data, int chunkSize) {
        this.chunks = new HashMap<>();
        this.tileset = new Tileset(data.getTileset());
        for (Vector2i position: data.getTiles().keySet()) {
            int chunkX = position.x / chunkSize;
            int chunkY = position.y / chunkSize;
            int x = position.x % chunkSize;
            int y = position.y % chunkSize;
            Chunk chunk = chunks.get(new Vector2i(chunkX, chunkY));
            if(chunk == null) {
                chunk = new Chunk(tileset, chunkSize);
                chunks.put(new Vector2i(chunkX, chunkY), chunk);
            }
            chunk.setTile(x,y,data.getTiles().get(position));
        }
    }

    public HashMap<Vector2i, Chunk> getChunks() {
        return chunks;
    }


    public Tileset getTileset() {
        return tileset;
    }
}
