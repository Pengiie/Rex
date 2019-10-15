package com.Penguinz22.Rex.listeners;

public interface ApplicationListener {

    void init();

    void resize(int width, int height);

    void update();

    void render();

    default void dispose() {

    }

}
