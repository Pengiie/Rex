package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.TextureAtlas;
import com.Penguinz22.Rex.assets.TilemapData;
import com.Penguinz22.Rex.assets.loaders.TextureAtlasLoader;
import com.Penguinz22.Rex.assets.loaders.TilemapLoader;
import com.Penguinz22.Rex.graphics.BatchRenderer;
import com.Penguinz22.Rex.graphics.camera.Camera;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.tilemap.Tilemap;
import com.Penguinz22.Rex.tilemap.TilemapRenderer;
import org.joml.Vector2f;

public class TestTextureLoader {
    public static void main(String[] args) {

        Application app = new Application(new TestListener(), new ApplicationConfig());

    }

    static class TestListener implements ApplicationListener {

        BatchRenderer renderer;
        TilemapRenderer tilemapRenderer;
        TextureAtlas texture;
        Tilemap tilemap;
        Camera camera;

        @Override
        public void init() {
            this.renderer = new BatchRenderer();
            this.tilemapRenderer = new TilemapRenderer();
            AssetManager manager = new AssetManager(true);
            TilemapLoader.TilemapParameter param = new TilemapLoader.TilemapParameter();
            TextureAtlasLoader.TextureAtlasParameter params = new TextureAtlasLoader.TextureAtlasParameter();
            params.unitSize = new Vector2f(32, 32);
            param.tilesetParameters = params;
            param.tilesetPath = "src/test/resources/test.png";
            manager.load("src/test/resources/overworld.tilemap", TilemapData.class, param);
            manager.finishLoading();
            if(manager.isFinished()) {
                this.tilemap = new Tilemap(manager.get("src/test/resources/overworld.tilemap"), 32, 32);
                //System.out.println(texture.getTextureTarget() + " : " + texture.getTextureId());
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
            /*texture.switchToTexture(2, 1);
            System.out.println(texture.MAX_ID);
            this.renderer.draw(texture, Color.white, 50, 50, 500, 500, new Rotation(0));
            //else this.renderer.draw(texture, Color.white, 50, 50, 100, 100, new Rotation(0));
            this.renderer.finish();*/
            tilemapRenderer.render(tilemap);
            tilemapRenderer.finish();
        }
    }

}
