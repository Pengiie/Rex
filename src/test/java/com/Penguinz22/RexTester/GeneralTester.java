package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.graphics.BatchRenderer;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.utils.Color;
import com.Penguinz22.Rex.utils.Rotation;

public class GeneralTester implements ApplicationListener {

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle("Quality Game!");
        Application app = new Application(new GeneralTester(), config);
    }

    private BatchRenderer renderer;

    @Override
    public void init() {
        this.renderer = new BatchRenderer();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        for(int i=0;i<=360;i++) {
            renderer.draw(new Color(1, 1, 0, 1), 150, 150, 50, 50, new Rotation(Rotation.Origin.CENTER, i));
        }
        renderer.finish();
    }
}
