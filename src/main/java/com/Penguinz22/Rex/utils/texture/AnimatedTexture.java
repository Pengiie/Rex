package com.Penguinz22.Rex.utils.texture;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.assets.TextureAtlas;

public class AnimatedTexture {

    private TextureAtlas textureAtlas;

    private int startId;
    private int totalFrames;
    private int fps;
    private float millisecondsBetweenFrame;

    private long lastTime;
    private int currentFrame;

    public AnimatedTexture(TextureAtlas textureAtlas, int startX, int startY, int totalFrames, int fps) {
        this.textureAtlas = textureAtlas.clone();
        this.startId = textureAtlas.getId(startX, startY);
        this.totalFrames = totalFrames;
        this.fps = fps;
        this.millisecondsBetweenFrame = ((float) 1/fps) * 1000;

        this.lastTime = Core.timings.getTotalMilliseconds();
        this.currentFrame = 0;
        this.textureAtlas.switchToTexture(startId);
        System.out.println(startId);
    }

    public void update() {
        long elapsedTime = Core.timings.getTotalMilliseconds() - this.lastTime;
        System.out.println(millisecondsBetweenFrame);
        int elapsedFrames = (int) (elapsedTime / millisecondsBetweenFrame);
        if(elapsedFrames <= 0)
            return;

        if((currentFrame += elapsedFrames) >= totalFrames)
            currentFrame = 0;

        this.lastTime = Core.timings.getTotalMilliseconds();
        this.textureAtlas.switchToTexture(startId+currentFrame);
    }

    public void setFps(int fps) {
        this.fps = fps;
        this.millisecondsBetweenFrame = ((float) 1/fps) * 1000;
    }

    public TextureAtlas getCurrentTexture() {
        return this.textureAtlas;
    }

}
