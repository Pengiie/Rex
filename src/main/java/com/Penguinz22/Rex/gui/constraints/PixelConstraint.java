package com.Penguinz22.Rex.gui.constraints;

import com.Penguinz22.Rex.gui.GuiComponent;

/**
 * Applies value based off of pixels from the bottom left corner of the screen
 */
public class AbsoluteConstraint extends Constraint {

    private float pixelsX, pixelsY;

    public AbsoluteConstraint(float pixelsX, float pixelsY) {
        this.pixelsX = pixelsX;
        this.pixelsY = pixelsY;
    }

    @Override
    public void update(GuiComponent superComponent) {
        
    }
}
