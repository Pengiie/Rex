package com.Penguinz22.Rex.assets.loaders;

import com.Penguinz22.Rex.assets.AssetDescriptor;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.TextureAtlas;
import com.Penguinz22.Rex.assets.TilemapData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TilemapLoader extends AssetLoader<TilemapData, TilemapLoader.TilemapParameter>{

    TilemapInfo info = new TilemapInfo();

    @Override
    public List<AssetDescriptor> getDependencies(String filePath, TilemapParameter assetParameters) {
        if(assetParameters == null) {
            throw new RuntimeException("Tilemap must be supplied with parameters");
        }
        return new ArrayList<>(
                Collections.singletonList(new AssetDescriptor<>(assetParameters.tilesetPath, TextureAtlas.class, assetParameters.tilesetParameters)));
    }

    @Override
    public void loadAsync(AssetManager assetManager, String filePath, TilemapParameter assetParameters) {
        info.data = TilemapData.loadTilemapData(assetManager.get(assetParameters.tilesetPath) ,filePath);
    }

    @Override
    public TilemapData loadSync(AssetManager assetManager, String filePath, TilemapParameter assetParameters) {
        return info.data;
    }

    public static class TilemapParameter extends AssetLoaderParameter<TilemapData> {
        public String tilesetPath = null;
        public TextureAtlasLoader.TextureAtlasParameter tilesetParameters = null;
        public String textureAtlas = null;
    }

    public class TilemapInfo {
        TilemapData data;
    }


}
