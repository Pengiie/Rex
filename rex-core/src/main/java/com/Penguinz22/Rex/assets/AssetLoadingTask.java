package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.assets.loaders.AssetLoader;
import com.Penguinz22.Rex.utils.AsyncTask;

import java.util.List;

public class AssetLoadingTask implements AsyncTask<Void> {
    final AssetDescriptor assetDescriptor;
    final AssetLoader loader;
    final AsyncExecutor executor;
    final long startTime;
    AssetManager assetManager;
    volatile Object asset = null;
    volatile AsyncResult<Void> loadFuture = null;
    volatile AsyncResult<Void> depsFuture = null;
    volatile boolean asyncDone = false;
    int ticks = 0;
    volatile boolean cancel = false;
    volatile boolean loadedDependencies = false;
    volatile List<AssetDescriptor> dependencies;


    public AssetLoadingTask(AssetManager assetManager, AssetDescriptor assetDescriptor, AssetLoader loader, AsyncExecutor executor) {
        this.assetManager = assetManager;
        this.assetDescriptor = assetDescriptor;
        this.loader = loader;
        this.executor = executor;
        startTime = System.nanoTime();
    }

    @Override
    public Void call() throws Exception {
        if(!loadedDependencies){
            dependencies = loader.getDependencies(assetDescriptor.filePath, assetDescriptor.params);
            if(dependencies != null){
                assetManager.injectDependencies(assetDescriptor.filePath, dependencies.toArray(new AssetDescriptor[dependencies.size()]));
            }else{
                // if we have no dependencies, we load the async part of the task immediately.
                loader.loadAsync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
                asyncDone = true;
            }
        }else{
            loader.loadAsync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
            asyncDone = true;
        }
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
        if(!loadedDependencies){
            if(depsFuture == null){
                depsFuture = executor.submit(this);
            }else{
                if(depsFuture.isDone()){
                    try{
                        depsFuture.get();
                    }catch(Exception e){
                        throw new RuntimeException("Couldn't load dependencies of asset: " + assetDescriptor.filePath+" because of "+e.getMessage(), e);
                    }
                    loadedDependencies = true;
                    if(asyncDone){
                        asset = loader.loadSync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
                    }
                }
            }
        } else {
            if (loadFuture == null && !asyncDone) {
                loadFuture = executor.submit(this);
            } else {
                if (asyncDone) {
                    asset = loader.loadSync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
                } else if (loadFuture.isDone()) {
                    try {
                        loadFuture.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Couldn't load asset: " + assetDescriptor.filePath);
                    }
                    asset = loader.loadSync(assetManager, assetDescriptor.filePath, assetDescriptor.params);
                }
            }
        }
    }
}
