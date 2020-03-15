package com.Penguinz22.Rex.gui.animation;

import com.Penguinz22.Rex.gui.constraints.Constraint;

public abstract class TransitionDriver {

    private float timeNeeded;

    private float startValue;
    private float targetValue;

    public TransitionDriver(float startValue, float targetValue, float timeNeeded) {
        this.startValue = startValue;
        this.targetValue = targetValue;
        this.timeNeeded = timeNeeded;
    }

    public abstract float interpolate(float startValue, float time, boolean reversed);

    public float getTimeNeeded() {
        return timeNeeded;
    }

    public float getStartValue() {
        return startValue;
    }

    public float getTargetValue() {
        return targetValue;
    }
}
