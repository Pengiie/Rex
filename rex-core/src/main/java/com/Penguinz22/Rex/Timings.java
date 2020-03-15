package com.Penguinz22.Rex;

public class Timings {

    // Gets the amount of processed frames per second
    private long startCounter;
    private int totalFrames;
    private int fps;

    // In nanoseconds
    private long lastTime;
    private float deltaTime;

    // In milliseconds
    private long startTime;

    public Timings() {
        startTime = System.currentTimeMillis();

        lastTime = System.nanoTime();
        startCounter = System.currentTimeMillis();
    }

    public void update() {
        // Calculate FPS
        totalFrames++;
        if(System.currentTimeMillis() - startCounter >= 1000) {
            fps = totalFrames;
            totalFrames = 0;
            startCounter = System.currentTimeMillis();
        }

        // Calculate delta time
        long delta = System.nanoTime() - lastTime;
        this.deltaTime = delta * (float) Math.pow(10, -9);
        this.lastTime = System.nanoTime();
    }

    public int getFps() {
        return fps;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public long getTotalMilliseconds() {
        return System.currentTimeMillis() - startTime;
    }
}
