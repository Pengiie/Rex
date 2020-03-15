package com.Penguinz22.Rex.gui.constraints;

/**
 * Position relative to parent constraints position by the factor plus original position
 * Width relative to parent constraints width times the factor
 */
public class RelativeConstraint extends Constraint {

    private float factor;

    public RelativeConstraint(float factor) {
        this.factor = factor;
    }

    @Override
    public void update(GuiConstraints constraints, GuiConstraints parentConstraints) {
        if(getConstraintType(constraints) == ConstraintType.POSITION) {
            if (constraints.getXConstraint() == this) {
                this.value = parentConstraints.getXConstraint().getValue() +
                        parentConstraints.getWidthConstraint().getValue() * factor;
            } else if (constraints.getYConstraint() == this) {
                this.value = parentConstraints.getYConstraint().getValue() +
                        parentConstraints.getHeightConstraint().getValue() * factor;
            }
        } else if(getConstraintType(constraints) == ConstraintType.SIZE) {
            this.value = getMatchingParentConstraint(constraints, parentConstraints).getValue() * factor;
        }
    }

    @Override
    public Constraint clone() {
        return new RelativeConstraint(factor);
    }
}
