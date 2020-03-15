package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.assets.loaders.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class AssetManager {

    final HashMap<Class, HashMap<String, RefCountedContainer>> assets = new HashMap<>();
    final HashMap<String, List<String>> assetDependencies = new HashMap<>();
    final HashMap<Class, AssetLoader> loaders = new HashMap<>();
    final List<AssetDescriptor> loadQueue = new ArrayList<>();
    final HashMap<String, Class> assetTypes = new HashMap<>();
    final Stack<AssetLoadingTask> tasks = new Stack<>();
    final AsyncExecutor executor;
    int loaded = 0;
    int toLoad = 0;
    int peakTasks = 0;

    private List<AssetLoadedCallback> loadedCallbacks = new ArrayList<>();

    public AssetManager(boolean defaultLoaders) {
        if(defaultLoaders) {
            setLoader(Texture.class, new TextureLoader());
            setLoader(TextureAtlas.class, new TextureAtlasLoader());
            setLoader(Font.class, new TrueTypeFontLoader());
            setLoader(TilemapData.class, new TilemapLoader());
        }
        this.executor = new AsyncExecutor(1);
    }

    public synchronized float getProgress() {
        if(toLoad == 0)
            return 1;
        float fractionalLoaded = (float)loaded;
        if(peakTasks > 0){
            fractionalLoaded += ((peakTasks - tasks.size()) / (float)peakTasks);
        }
        return Math.min(1, fractionalLoaded / (float)toLoad);
    }

    public synchronized <T, P extends AssetLoaderParameter<T>> void setLoader(Class<T> type, AssetLoader<T, P> loader) {
        if(type == null) throw new RuntimeException("Type is null");
        if(loader == null) throw new RuntimeException("Loader is null");
        loaders.put(type, loader);
    }

    public <T> T get(String filePath) {
        Class<T> type = assetTypes.get(filePath);
        if(type == null)
            throw new RuntimeException("Asset not loaded: " + filePath);
        HashMap<String, RefCountedContainer> assetsByType = assets.get(type);
        if(assetsByType == null)
            throw new RuntimeException("Asset not loaded: " + filePath);
        RefCountedContainer assetContainer = assetsByType.get(filePath);
        if(assetContainer == null)
            throw new RuntimeException("Asset not loaded: " + filePath);
        T asset = assetContainer.getObject(type);
        if(asset == null)
            throw new RuntimeException("Asset not loaded: " + filePath);
        return asset;
    }

    public synchronized  <T> AssetDescriptor load(String filePath, Class<T> type) {
        return load(filePath, type, null);
    }

    public synchronized <T> AssetDescriptor load(String filePath, Class<T> type, AssetLoaderParameter<T> parameter) {
        AssetLoader loader = getLoader(type);
        if(loader == null) throw new RuntimeException("No loader for type: "+type.getSimpleName());

        if(loadQueue.size() == 0) {
            loaded = 0;
            toLoad = 0;
            peakTasks = 0;
        }

        for(int i = 0; i < loadQueue.size(); i++){
            AssetDescriptor desc = loadQueue.get(i);
            if(desc.filePath.equals(filePath) && !desc.type.equals(type)) throw new RuntimeException(
                    "Asset with name '" + filePath + "' already in preload queue, but has different type (expected: "
                            + type.getSimpleName() + ", found: " + desc.type.getSimpleName() + ")");
        }

        for(int i = 0; i < tasks.size(); i++){
            AssetDescriptor desc = tasks.get(i).assetDescriptor;
            if(desc.filePath.equals(filePath) && !desc.type.equals(type)) throw new RuntimeException(
                    "Asset with name '" + filePath + "' already in task list, but has different type (expected: "
                            + type.getSimpleName() + ", found: " + desc.type.getSimpleName() + ")");
        }

        Class otherType = assetTypes.get(filePath);
        if(otherType != null && !otherType.equals(type))
            throw new RuntimeException("Asset with name '" + filePath + "' already loaded, but has different type (expected: "
                    + type.getSimpleName() + ", found: " + otherType.getSimpleName() + ")");

        toLoad++;
        AssetDescriptor assetDescriptor = new AssetDescriptor(filePath, type, parameter);
        loadQueue.add(assetDescriptor);
        return assetDescriptor;
    }

    public void finishLoading() {
        while(!update())
            Thread.yield();
    }

    // Remember to call the update method somewhere
    public synchronized boolean update() {
        try {
            if (tasks.size() == 0) {
                while (loadQueue.size() != 0 && tasks.size() == 0) {
                    nextTask();
                }

                if (tasks.size() == 0)
                    return true;
            }
            return updateTask() && loadQueue.size() == 0 && tasks.size() == 0;
        } catch(Throwable t) {
            return loadQueue.size() == 0;
        }
    }

    private void nextTask() {
        AssetDescriptor assetDescriptor = loadQueue.remove(0);
        if(isLoaded(assetDescriptor.filePath)){
            Class type = assetTypes.get(assetDescriptor.filePath);
            RefCountedContainer assetRef = assets.get(type).get(assetDescriptor.filePath);
            assetRef.incRefCount();
            loaded++;
        }else{
            addTask(assetDescriptor);
        }
    }

    private boolean updateTask() {
        AssetLoadingTask task = tasks.peek();

        boolean complete = true;
        try {
            complete = task.cancel || task.update();
        } catch (RuntimeException ex) {
            task.cancel = true;
            ex.printStackTrace();
        }
        if(complete) {
            if(tasks.size() == 1) {
                loaded++;
                peakTasks = 0;
            }
            tasks.pop();
            if(task.cancel)
                return true;
            addAsset(task.assetDescriptor.filePath, task.assetDescriptor.type, task.getAsset());

            long endTime = System.nanoTime();

            return true;
        }
        return false;
    }

    public synchronized boolean isFinished() {
        return loadQueue.size() == 0 && tasks.size() == 0;
    }

    public synchronized boolean isLoaded(String filePath) {
        if(filePath == null)
            return false;
        return assetTypes.containsKey(filePath);
    }

    protected <T> void addAsset(final String filePath, Class<T> type, T asset) {
        assetTypes.put(filePath, type);

        HashMap<String, RefCountedContainer> typeToAssets = assets.get(type);
        if(typeToAssets == null){
            typeToAssets = new HashMap<>();
            assets.put(type, typeToAssets);
        }
        typeToAssets.put(filePath, new RefCountedContainer(asset));

        for (AssetLoadedCallback loadedCallback : loadedCallbacks) {
            loadedCallback.onLoad(filePath, asset);
        }
    }

    public void injectDependencies(String parentFileName, AssetDescriptor... dependenciesToInject) {
        for (AssetDescriptor dependency : dependenciesToInject) {
            List<String> dependencies = assetDependencies.get(parentFileName);
            if(dependencies == null) {
                dependencies = new ArrayList<>();
                assetDependencies.put(parentFileName,dependencies);
            }
            dependencies.add(dependency.filePath);

            if(isLoaded(dependency.filePath)) {
                Class type = assetTypes.get(dependency.filePath);
                RefCountedContainer assetReference = assets.get(type).get(dependency.filePath);
                assetReference.incRefCount();
            } else {
                addTask(dependency);
            }
        }
    }

    private void addTask(AssetDescriptor assetDescriptor) {
        AssetLoader loader = getLoader(assetDescriptor.type);
        if(loader == null)
            throw new RuntimeException("No loader found for asset type: "+assetDescriptor.type.getSimpleName());
        tasks.push(new AssetLoadingTask(this, assetDescriptor, loader, executor));
        peakTasks++;
    }

    public synchronized void dispose() {

    }

    public <T> AssetLoader getLoader(final Class<T> type) {
        return loaders.get(type);
    }

    public void addAssetLoadCallback(AssetLoadedCallback callback) {
        this.loadedCallbacks.add(callback);
    }

}
