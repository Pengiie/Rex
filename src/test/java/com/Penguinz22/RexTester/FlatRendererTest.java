package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.graphics.Draw;
import com.Penguinz22.Rex.listeners.ApplicationListener;

public class FlatRendererTest implements ApplicationListener {

    private Draw draw;

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle("Quality Game!");
        Application app = new Application(new FlatRendererTest(), config);
    }

    @Override
    public void init() {
        this.draw = new Draw();
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
