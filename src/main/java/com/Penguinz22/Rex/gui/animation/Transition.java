package com.Penguinz22.Rex.gui.animation;

import com.Penguinz22.Rex.gui.components.GuiComponent;

import java.lang.reflect.GenericArrayType;

public class Transition {

    private TransitionDriver xDriver;
    private TransitionDriver yDriver;
    private TransitionDriver widthDriver;
    private TransitionDriver heightDriver;

    private TransitionDriver alphaDriver;

    private float totalTime;

    public Transition() {
        updateTotalTime();
    }

    public void updateTotalTime() {
        totalTime = 0;
        if(xDriver!=null)
            totalTime += xDriver.getTimeNeeded();
        if(yDriver!=null)
            totalTime += yDriver.getTimeNeeded();
        if(widthDriver!=null)
            totalTime += widthDriver.getTimeNeeded();
        if(heightDriver!=null)
            totalTime += heightDriver.getTimeNeeded();
        if(alphaDriver!=null)
            totalTime += alphaDriver.getTimeNeeded();
    }

    public Transition setXDriver(TransitionDriver xDriver) {
        this.xDriver = xDriver;
        updateTotalTime();
        return this;
    }

    public Transition setYDriver(TransitionDriver yDriver) {
        this.yDriver = yDriver;
        updateTotalTime();
        return this;
    }

    public Transition setWidthDriver(TransitionDriver widthDriver) {
        this.widthDriver = widthDriver;
        updateTotalTime();
        return this;
    }

    public Transition setHeightDriver(TransitionDriver heightDriver) {
        this.heightDriver = heightDriver;
        updateTotalTime();
        return this;
    }

    public Transition setAlphaDriver(TransitionDriver alphaDriver) {
        this.alphaDriver = alphaDriver;
        updateTotalTime();
        return this;
    }

    public TransitionDriver getXDriver() {
        return xDriver;
    }

    public TransitionDriver getYDriver() {
        return yDriver;
    }

    public TransitionDriver getWidthDriver() {
        return widthDriver;
    }

    public TransitionDriver getHeightDriver() {
        return heightDriver;
    }

    public TransitionDriver getAlphaDriver() {
        return alphaDriver;
    }

    public float getTotalTime() {
        return totalTime;
    }
}
