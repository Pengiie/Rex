package com.Penguinz22.Rex.tilemap;

import com.Penguinz22.Rex.assets.TextureAtlas;
import com.Penguinz22.Rex.utils.VectorUtils;
import org.joml.Vector2f;

import java.util.HashMap;

public class Tileset {

    private TextureAtlas atlas;
    private HashMap<Integer, Vector2f> positionCache;

    public Tileset(TextureAtlas atlas) {
        this.atlas = atlas;
        this.positionCache = new HashMap<>();
    }

    public Vector2f getId(int id) {
        if(!positionCache.containsKey(id)) {
            atlas.switchToTexture(id);
            positionCache.put(id, VectorUtils.clone(atlas.getOffset()));
        }
        return positionCache.get(id);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public Vector2f getSize() {
        return atlas.getSize();
    }
}
