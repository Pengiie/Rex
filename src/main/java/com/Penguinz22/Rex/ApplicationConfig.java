package com.Penguinz22.Rex;

import com.Penguinz22.Rex.listeners.WindowListener;

public class ApplicationConfig {

    int windowX = -1;
    int windowY = -1;
    int windowWidth = 1280;
    int windowHeight = 720;
    boolean windowResizable = false;
    WindowListener windowListener;
    String title = "";

    public static ApplicationConfig copyConfig(ApplicationConfig config) {
        ApplicationConfig copy = new ApplicationConfig();
        copy.set(config);
        return copy;
    }

    public void set(ApplicationConfig config) {
        this.windowX = config.windowX;
        this.windowY = config.windowY;
        this.windowWidth = config.windowWidth;
        this.windowHeight = config.windowHeight;
        this.windowResizable = config.windowResizable;
        this.windowListener = config.windowListener;
        this.title = config.title;
    }

    public void setWindowPosition(int windowX, int windowY) {
        this.windowX = windowX;
        this.windowY = windowY;
    }

    public void setWindowSize(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }

    public void setWindowResizable(boolean windowResizable) {
        this.windowResizable = windowResizable;
    }

    public void setWindowListener(WindowListener windowListener) {
        this.windowListener = windowListener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WindowListener getWindowListener() {
        return windowListener;
    }
}
