package com.Penguinz22.Rex.assets.loaders;

import com.Penguinz22.Rex.assets.AssetDescriptor;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.utils.texture.Format;
import com.Penguinz22.Rex.utils.texture.TextureData;

import java.util.List;

public class TextureLoader extends AssetLoader<Texture, TextureLoader.TextureParameter> {

    TextureLoaderInfo info = new TextureLoaderInfo();


    @Override
    public List<AssetDescriptor> getDependencies(String filePath, TextureParameter assetParameters) {
        return null;
    }

    @Override
    public void loadAsync(AssetManager assetManager, String filePath, TextureParameter parameter) {
        info.fileName = filePath.split("/")[filePath.split("/").length-1];
        if(parameter == null || parameter.textureData == null) {
            Format format = null;
            if(parameter != null) {
                format = parameter.format;
                info.texture = parameter.texture;
            }

            info.data = TextureData.Factory.loadFromFile(filePath, format);
        } else {
            info.data = parameter.textureData;
            info.texture = parameter.texture;
        }
        if(!info.data.isPrepared())
            info.data.prepare();
    }

    @Override
    public Texture loadSync(AssetManager assetManager, String filePath, TextureParameter parameter) {
        if(info == null)
            return null;
        Texture texture = info.texture;
        if(texture != null) {
            texture.load(info.data);
        } else {
            texture = new Texture(info.data);
        }
        return texture;
    }

    public static class TextureLoaderInfo {
        String fileName;
        TextureData data;
        Texture texture;
    }

    public static class TextureParameter extends AssetLoaderParameter<Texture> {
        public Format format = null;
        public Texture texture = null;
        public TextureData textureData = null;
    }

}
