package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.assets.loaders.AssetLoader;
import com.Penguinz22.Rex.utils.AsyncTask;

public class AssetLoadingTask implements AsyncTask<Void> {
    final AssetDescriptor assetDescriptor;
    final AssetLoader loader;
    final AsyncExecutor executor;
    final long startTime;
    AssetManager assetManager;
    volatile Object asset = null;
    volatile AsyncResult<Void> loadFuture = null;
    volatile boolean asyncDone = false;
    int ticks = 0;
    volatile boolean cancel = false;


    public AssetLoadingTask(AssetManager assetManager, AssetDescriptor assetDescriptor, AssetLoader loader, AsyncExecutor executor) {
        this.assetManager = assetManager;
        this.assetDescriptor = assetDescriptor;
        this.loader = loader;
        this.executor = executor;
        startTime = System.nanoTime();
    }

    @Override
    public Void call() throws Exception {
        loader.loadAsync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
        asyncDone = true;
        return null;
    }

    public boolean update() {
        ticks++;
        handleLoader();
        return asset != null;
    }

    public Object getAsset() {
        return asset;
    }

    private void handleLoader() {
        if(loadFuture == null && !asyncDone) {
            loadFuture = executor.submit(this);
        } else {
            if(asyncDone) {
                asset = loader.loadSync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
            } else if(loadFuture.isDone()) {
                try {
                    loadFuture.get();
                } catch (Exception e) {
                    throw new RuntimeException("Couldn't load asset: "+assetDescriptor.filePath);
                }
                asset = loader.loadSync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
            }
        }
    }
}
