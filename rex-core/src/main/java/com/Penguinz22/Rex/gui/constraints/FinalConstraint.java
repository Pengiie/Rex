package com.Penguinz22.Rex.gui.constraints;

/**
 * Used for absolute positioning on the screen or for width
 * Use for absolute x and y values on the screen or for width and height
 */
public class FinalConstraint extends Constraint {

    public FinalConstraint(float value) {
        this.value = value;
    }

    // This method does nothing, no reason to invoke
    @Override
    public void update(GuiConstraints constraints, GuiConstraints parentConstraint) {
        return;
    }

    @Override
    public Constraint clone() {
        return new FinalConstraint(value);
    }
}
