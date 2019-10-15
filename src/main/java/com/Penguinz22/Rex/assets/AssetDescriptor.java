package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.assets.loaders.AssetLoaderParameter;

public class AssetDescriptor<T> {
    public final String filePath;
    public final Class<T> type;
    public final AssetLoaderParameter params;

    public AssetDescriptor(String filePath, Class<T> assetType) {
        this(filePath, assetType, null);
    }

    public AssetDescriptor(String filePath, Class<T> assetType, AssetLoaderParameter<T> params) {
        this.filePath = filePath;
        this.type = assetType;
        this.params = params;
    }
}
