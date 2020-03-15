package com.Penguinz22.Rex.gui.constraints;

/**
 * Applies value based off of pixels from the bottom left corner of the parent component
 * Should use for x and y values
 */
public class PixelConstraint extends Constraint {

    private float pixels;

    public PixelConstraint(float pixels) {
        this.pixels = pixels;
    }

    @Override
    public void update(GuiConstraints constraints, GuiConstraints parentConstraints) {
        this.value = getMatchingParentConstraint(constraints, parentConstraints).getValue() + pixels;
    }

    @Override
    public Constraint clone() {
        return new PixelConstraint(pixels);
    }
}
