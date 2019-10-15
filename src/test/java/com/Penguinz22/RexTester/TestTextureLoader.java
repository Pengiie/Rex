package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import junit.framework.TestListener;

public class TestTextureLoader {

    public static void main(String[] args) {
//        System.out.println("hola");
//        AssetManager manager = new AssetManager(true);
//        manager.load("src/test/resources/test.png", Texture.class);
//        manager.finishLoading();
//        if(manager.isFinished()) {
//            Texture texture = manager.get("src/test/resources/test.png");
//            System.out.println(texture.getTextureTarget() + " : " + texture.getTextureId());
//            System.out.println("hi");
//        }

        Application app = new Application(new TestListener(), new ApplicationConfig());

    }

    static class TestListener implements ApplicationListener {

        @Override
        public void init() {
            AssetManager manager = new AssetManager(true);
            manager.load("src/test/resources/test.png", Texture.class);
            manager.finishLoading();
            if(manager.isFinished()) {
                Texture texture = manager.get("src/test/resources/test.png");
                System.out.println(texture.getTextureTarget() + " : " + texture.getTextureId());
            }
        }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void update() {

        }

        @Override
        public void render() {

        }
    }

}
