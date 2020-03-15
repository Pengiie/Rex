package com.Penguinz22.Rex.gui.animation;

public class SlideTransition extends TransitionDriver {

    public SlideTransition(float startValue, float targetValue, float timeNeeded) {
        super(startValue, targetValue, timeNeeded);
        // TODO: Allow for gui constraints such as relative constraint for start value and target value, will be relative to component that is being transitioned
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
