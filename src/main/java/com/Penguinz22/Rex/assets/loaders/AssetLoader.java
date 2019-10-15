package com.Penguinz22.Rex.assets.loaders;

import com.Penguinz22.Rex.assets.AssetManager;

public abstract class AssetLoader<T, P extends AssetLoaderParameter<T>> {


    /** Loads the part of the asset that doesn't require OpenGL context
     * @param filePath the path to the file to load
     */
    public abstract void loadAsync(AssetManager assetManager, String filePath, P assetParameters);

    /** Loads the OpenGL part of the asset
     * @param filePath the path to the file to load
     */
    public abstract T loadSync(AssetManager assetManager, String filePath, P assetParameters);

}
