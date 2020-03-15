package com.Penguinz22.Rex.assets.loaders;

import com.Penguinz22.Rex.assets.AssetDescriptor;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.Font;
import com.Penguinz22.Rex.assets.TextureAtlas;
import com.Penguinz22.Rex.utils.fonts.FontData;
import com.Penguinz22.Rex.utils.texture.Format;
import com.Penguinz22.Rex.utils.texture.TextureAtlasData;
import org.joml.Vector2f;

import java.util.List;

public class TrueTypeFontLoader extends AssetLoader<Font, TrueTypeFontLoader.FontParameter> {

    TrueTypeFontLoaderInfo info = new TrueTypeFontLoaderInfo();

    @Override
    public List<AssetDescriptor> getDependencies(String filePath, FontParameter assetParameters) {
        return null;
    }

    @Override
    public void loadAsync(AssetManager assetManager, String filePath, FontParameter assetParameters) {
        if(assetParameters != null)
            info.data = new FontData(filePath, assetParameters.bitmapWidth, assetParameters.bitmapHeight, assetParameters.lineHeight, false);
        else
            info.data = new FontData(filePath, 512, 512, 64, false);
        if(!info.data.isPrepared())
            info.data.prepare();
    }

    @Override
    public Font loadSync(AssetManager assetManager, String filePath, FontParameter assetParameters) {
        if(info.data == null)
            return null;
        Font font = info.font;
        if(font == null) {
            font = new Font(info.data);
        } else {
            font.load(info.data);
        }
        return font;
    }

    public static class TrueTypeFontLoaderInfo {
        String filePath;
        FontData data;
        Font font;
    }

    public static class FontParameter extends AssetLoaderParameter<Font> {
        public int lineHeight = 64;
        public int bitmapWidth = 512;
        public int bitmapHeight = 512;
        public boolean useKerning = false;
    }
}
