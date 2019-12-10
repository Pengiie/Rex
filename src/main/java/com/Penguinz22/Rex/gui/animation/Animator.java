package com.Penguinz22.Rex.gui.animation;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.gui.components.GuiComponent;
import com.Penguinz22.Rex.gui.constraints.GuiConstraints;

import java.lang.annotation.ElementType;
import java.util.HashMap;

public class Animator {

    private GuiComponent component;

    private HashMap<Transition, DelayHolder> transitions = new HashMap<>();

    private boolean playingCurrent = false;

    private Transition transition;
    private boolean reversed;
    private long startTime;
    private long delay;
    private float beginX;
    private float beginY;
    private float beginWidth;
    private float beginHeight;
    private float beginAlpha;

    public Animator(GuiComponent component) {
        this.component = component;
    }

    public void addAnimation(Transition transition, float startDelay, float reverseStartDelay) {
        transitions.put(transition, new DelayHolder(startDelay, reverseStartDelay));
    }

    public void playTransition(Transition transition, boolean reverse) {
        if(!transitions.containsKey(transition))
            throw new RuntimeException("This transition is not yet indexed yet");
        DelayHolder delays = transitions.get(transition);
        if(!playingCurrent) {
            setNewTransition(transition, delays, reverse);
        } else {
            if(transition == this.transition) {
                if(reverse&&!this.reversed) {
                    long temp = this.startTime;
                    setNewTransition(transition, delays, true);
                    if(transition.getXDriver()!=null)
                        this.beginX = transition.getXDriver().getTargetValue();
                    if(transition.getYDriver()!=null)
                        this.beginY = transition.getYDriver().getTargetValue();
                    if(transition.getWidthDriver()!=null)
                        this.beginWidth = transition.getWidthDriver().getTargetValue();
                    if(transition.getHeightDriver()!=null)
                        this.beginHeight = transition.getHeightDriver().getTargetValue();
                    if(transition.getAlphaDriver()!=null)
                        this.beginAlpha = transition.getAlphaDriver().getTargetValue();
                    this.startTime = (long) (Core.timings.getTotalMilliseconds() -
                            ((transition.getTotalTime()*1000)-(Core.timings.getTotalMilliseconds()-temp)));
                } else if(!reverse&&this.reversed){
                    long temp = this.startTime;
                    setNewTransition(transition, delays, false);
                    this.startTime = (long) (Core.timings.getTotalMilliseconds() -
                            ((transition.getTotalTime()*1000)-(Core.timings.getTotalMilliseconds()-temp)));
                } else {
                    long temp = this.startTime;
                    setNewTransition(transition, delays, reverse);
                    if(reverse) {
                        if(transition.getXDriver()!=null)
                            this.beginX = transition.getXDriver().getTargetValue();
                        if(transition.getYDriver()!=null)
                            this.beginY = transition.getYDriver().getTargetValue();
                        if(transition.getWidthDriver()!=null)
                            this.beginWidth = transition.getWidthDriver().getTargetValue();
                        if(transition.getHeightDriver()!=null)
                            this.beginHeight = transition.getHeightDriver().getTargetValue();
                        if(transition.getAlphaDriver()!=null)
                            this.beginAlpha = transition.getAlphaDriver().getTargetValue();
                    }
                    this.startTime = temp;
                }
            }
        }
    }

    private void setNewTransition(Transition transition, DelayHolder delays, boolean reverse) {
        this.transition = transition;
        if(transition.getXDriver()!=null)
            this.beginX = transition.getXDriver().getStartValue();
        if(transition.getYDriver()!=null)
            this.beginY = transition.getYDriver().getStartValue();
        if(transition.getWidthDriver()!=null)
            this.beginWidth = transition.getWidthDriver().getStartValue();
        if(transition.getHeightDriver()!=null)
            this.beginHeight = transition.getHeightDriver().getStartValue();
        if(transition.getAlphaDriver()!=null)
            this.beginAlpha = transition.getAlphaDriver().getStartValue();
        this.delay = (long) (delays.startDelay*1000);
        this.startTime = Core.timings.getTotalMilliseconds();
        this.reversed = reverse;
        playingCurrent = true;
    }

    long elapsed;
    public void update() {
        if(playingCurrent) {
            long elapsedTime = Core.timings.getTotalMilliseconds() - this.startTime;
            this.elapsed = elapsedTime;
            if(elapsedTime > delay) {
                if(updateCurrentTransition(elapsedTime-delay)) {
                    playingCurrent = false;
                }
            }
        }
    }

    private boolean updateCurrentTransition(long elapsedTime) {
        boolean isFinished = true;
        if(transition.getXDriver() != null) {
            this.component.getConstraintsReference().getXConstraint().pixelOffset
                    = transition.getXDriver().interpolate(beginX, elapsedTime, reversed);
            if(elapsedTime<transition.getXDriver().getTimeNeeded()*1000)
                isFinished = false;
        } else if(transition.getYDriver() != null) {
            this.component.getConstraintsReference().getYConstraint().pixelOffset
                    = transition.getYDriver().interpolate(beginY, elapsedTime, reversed);
            if(elapsedTime<transition.getYDriver().getTimeNeeded()*1000)
                isFinished = false;
        } else if(transition.getWidthDriver() != null) {
            this.component.getConstraintsReference().getWidthConstraint().pixelOffset
                    = transition.getWidthDriver().interpolate(beginWidth, elapsedTime, reversed);
            if(elapsedTime<transition.getWidthDriver().getTimeNeeded()*1000)
                isFinished = false;
        } else if(transition.getHeightDriver() != null) {
            this.component.getConstraintsReference().getHeightConstraint().pixelOffset
                    = transition.getHeightDriver().interpolate(beginHeight, elapsedTime, reversed);
            if(elapsedTime<transition.getHeightDriver().getTimeNeeded()*1000)
                isFinished = false;
        } else if(transition.getAlphaDriver() != null) {
            this.component.setAlpha(transition.getAlphaDriver().interpolate(beginAlpha, elapsedTime, reversed));
            if(elapsedTime<transition.getAlphaDriver().getTimeNeeded()*1000)
                isFinished = false;
        }
        this.component.updateConstraints();
        return isFinished;
    }

    private class DelayHolder {
        public final float startDelay;
        public final float reverseStartDelay;

        public DelayHolder(float startDelay, float reverseStartDelay) {
            this.startDelay = startDelay;
            this.reverseStartDelay = reverseStartDelay;
        }
    }

}
