package com.Penguinz22.Rex.assets.loaders;

import com.Penguinz22.Rex.assets.AssetDescriptor;
import com.Penguinz22.Rex.assets.AssetManager;

import java.util.List;

public abstract class AssetLoader<T, P extends AssetLoaderParameter<T>> {


    /** Gets the dependencies needed to load this file
     * @param filePath the path to the file to load
     * @return the asset descriptors of the dependencies needed to be injected
     */
    public abstract List<AssetDescriptor> getDependencies(String filePath, P assetParameters);

    /** Loads the part of the asset that doesn't require OpenGL context
     * @param filePath the path to the file to load
     */
    public abstract void loadAsync(AssetManager assetManager, String filePath, P assetParameters);

    /** Loads the OpenGL part of the asset
     * @param filePath the path to the file to load
     */
    public abstract T loadSync(AssetManager assetManager, String filePath, P assetParameters);

}
