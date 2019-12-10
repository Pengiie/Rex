package com.Penguinz22.Rex.gui.constraints;

public abstract class Constraint {

    protected float value;
    public float pixelOffset = 0;

    public abstract void update(GuiConstraints constraints, GuiConstraints parentConstraints);

    public abstract Constraint clone();

    protected Constraint getMatchingParentConstraint(GuiConstraints constraints, GuiConstraints parentConstraints) {
        if(constraints.getXConstraint() == this)
            return parentConstraints.getXConstraint();
        if(constraints.getYConstraint() == this)
            return parentConstraints.getYConstraint();
        if(constraints.getWidthConstraint() == this)
            return parentConstraints.getWidthConstraint();
        if(constraints.getHeightConstraint() == this)
            return parentConstraints.getHeightConstraint();
        throw new RuntimeException("Could not find corresponding parent constraint");
    }

    protected ConstraintType getConstraintType(GuiConstraints constraints) {
        if(constraints.getXConstraint() == this)
            return ConstraintType.POSITION;
        if(constraints.getYConstraint() == this)
            return ConstraintType.POSITION;
        if(constraints.getWidthConstraint() == this)
            return ConstraintType.SIZE;
        if(constraints.getHeightConstraint() == this)
            return ConstraintType.SIZE;
        throw new RuntimeException("Could not find corresponding parent constraint");
    }

    public float getValue() {
        return value + pixelOffset;
    }

    enum ConstraintType {
        POSITION, SIZE;
    }
}
