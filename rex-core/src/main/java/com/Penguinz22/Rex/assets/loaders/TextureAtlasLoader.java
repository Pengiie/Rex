package com.Penguinz22.Rex.assets.loaders;

import com.Penguinz22.Rex.assets.AssetDescriptor;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.assets.TextureAtlas;
import com.Penguinz22.Rex.utils.texture.Format;
import com.Penguinz22.Rex.utils.texture.TextureAtlasData;
import com.Penguinz22.Rex.utils.texture.TextureData;
import org.joml.Vector2f;

import java.util.List;

public class TextureAtlasLoader extends AssetLoader<TextureAtlas, TextureAtlasLoader.TextureAtlasParameter> {

    TextureAtlasLoaderInfo info = new TextureAtlasLoaderInfo();

    @Override
    public List<AssetDescriptor> getDependencies(String filePath, TextureAtlasParameter assetParameters) {
        return null;
    }

    @Override
    public void loadAsync(AssetManager assetManager, String filePath, TextureAtlasParameter parameter) {
        info.fileName = filePath.split("/")[filePath.split("/").length-1];
        if(parameter == null || parameter.textureData == null) {
            Format format = null;
            Vector2f unitSize = new Vector2f(64, 64);
            if(parameter != null) {
                if(parameter.format != null)
                    format = parameter.format;
                if(parameter.texture != null)
                    info.texture = parameter.texture;
                if(parameter.unitSize != null)
                    unitSize = parameter.unitSize;
            }

            info.data = TextureAtlasData.Factory.loadFromFile(filePath, format, unitSize);
        } else {
            info.data = parameter.textureData;
            info.texture = parameter.texture;
        }
        if(!info.data.isPrepared())
            info.data.prepare();
    }

    @Override
    public TextureAtlas loadSync(AssetManager assetManager, String filePath, TextureAtlasParameter assetParameters) {
        if(info == null)
            return null;
        TextureAtlas texture = info.texture;
        if(texture != null) {
            texture.load(info.data);
        } else {
            texture = new TextureAtlas(info.data);
        }
        return texture;
    }

    public static class TextureAtlasLoaderInfo {
        String fileName;
        TextureAtlasData data;
        TextureAtlas texture;
    }

    public static class TextureAtlasParameter extends AssetLoaderParameter<TextureAtlas> {
        public Format format = null;
        public TextureAtlas texture = null;
        public TextureAtlasData textureData = null;
        public Vector2f unitSize;
    }

}
