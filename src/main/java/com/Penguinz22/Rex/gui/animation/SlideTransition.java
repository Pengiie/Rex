package com.Penguinz22.Rex.gui.animation;

import com.Penguinz22.Rex.gui.constraints.Constraint;

public class SlideTransition extends TransitionDriver {

    public SlideTransition(float startValue, float targetValue, float timeNeeded) {
        super(startValue, targetValue, timeNeeded);
    }

    @Override
    public float interpolate(float startValue, float time, boolean reversed) {
        if(!reversed) {
            float slope = (startValue-getTargetValue())/(-getTimeNeeded());
            return slope * (time/1000) + startValue;
        } else {
            float slope = (getStartValue()-getTargetValue())/(getTimeNeeded());
            return slope * (time/1000) + startValue;
        }

    }
}
