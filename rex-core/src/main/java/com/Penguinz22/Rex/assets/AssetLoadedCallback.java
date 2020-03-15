package com.Penguinz22.Rex.assets;

public interface AssetLoadedCallback {

    public <T> void onLoad(String filePath, T asset);

}
