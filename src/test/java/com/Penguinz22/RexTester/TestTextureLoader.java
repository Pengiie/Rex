package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.assets.TextureAtlas;
import com.Penguinz22.Rex.assets.loaders.TextureAtlasLoader;
import com.Penguinz22.Rex.graphics.BatchRenderer;
import com.Penguinz22.Rex.graphics.Draw;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.utils.Color;
import com.Penguinz22.Rex.utils.Rotation;
import com.Penguinz22.Rex.utils.texture.TextureAtlasData;
import junit.framework.TestListener;
import org.joml.Vector2f;

public class TestTextureLoader {
    public static void main(String[] args) {

        Application app = new Application(new TestListener(), new ApplicationConfig());

    }

    static class TestListener implements ApplicationListener {

        BatchRenderer renderer;
        TextureAtlas texture;

        @Override
        public void init() {
            this.renderer = new BatchRenderer();
            AssetManager manager = new AssetManager(true);
            TextureAtlasLoader.TextureAtlasParameter params = new TextureAtlasLoader.TextureAtlasParameter();
            params.unitSize = new Vector2f(32, 32);
            manager.load("src/test/resources/test.png", TextureAtlas.class, params);
            manager.finishLoading();
            if(manager.isFinished()) {
                this. texture = manager.get("src/test/resources/test.png");
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
            texture.switchToTexture(5);
            this.renderer.draw(texture, Color.white, 50, 50, 500, 500, new Rotation(0));
            //else this.renderer.draw(texture, Color.white, 50, 50, 100, 100, new Rotation(0));
            this.renderer.finish();
        }
    }

}
