package com.Penguinz22.ZSurvival.assets;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.assets.Font;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.assets.TextureAtlas;
import com.Penguinz22.Rex.assets.TilemapData;
import com.Penguinz22.Rex.assets.loaders.TextureAtlasLoader;
import com.Penguinz22.Rex.assets.loaders.TextureLoader;
import com.Penguinz22.Rex.assets.loaders.TrueTypeFontLoader;
import org.joml.Vector2f;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssetLibrary {

    // File path, Field name
    private HashMap<String, String> assets = new HashMap<>();

    private boolean isLoaded = false;

    public void load() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if(field.get(this) == null) {
                    if(!field.isAnnotationPresent(AssetPath.class))
                        continue;
                    AssetPath path = field.getAnnotation(AssetPath.class);
                    Class<?> assetType = field.getType();
                    if(assetType.isAssignableFrom(Texture.class)) {
                        Core.assets.load(path.value(), Texture.class, null);
                    } else if(assetType.isAssignableFrom(TextureAtlas.class)) {
                        TextureAtlasLoader.TextureAtlasParameter parameter = null;
                        if(field.isAnnotationPresent(TextureAtlasParameter.class)) {
                            TextureAtlasParameter param = field.getAnnotation(TextureAtlasParameter.class);

                            parameter = new TextureAtlasLoader.TextureAtlasParameter();
                            parameter.unitSize = new Vector2f(param.unitSizeX(), param.unitSizeY());
                        }
                        Core.assets.load(path.value(), TextureAtlas.class, parameter);
                    } else if(assetType.isAssignableFrom(Font.class)) {
                        TrueTypeFontLoader.FontParameter parameter = null;
                        if(field.isAnnotationPresent(FontParameter.class)) {
                            FontParameter param = field.getAnnotation(FontParameter.class);

                            parameter = new TrueTypeFontLoader.FontParameter();
                            parameter.lineHeight = param.lineHeight();
                            parameter.bitmapWidth = param.bitmapWidth();
                            parameter.bitmapHeight = param.bitmapHeight();
                        }
                        Core.assets.load(path.value(), Font.class, parameter);
                    } else {
                        continue;
                    }
                    assets.put(path.value(), field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Core.assets.addAssetLoadCallback(this::onAssetLoaded);
        isLoaded = true;
    }

    private <T> void onAssetLoaded(String path, T asset) {
        for (String filePath : assets.keySet()) {
            if(path.equals(filePath)) {
                try {
                    Field field = this.getClass().getDeclaredField(assets.get(filePath));
                    field.setAccessible(true);
                    field.set(this, Core.assets.get(filePath));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
