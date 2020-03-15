package com.Penguinz22.Rex.gui.constraints;

/**
 * Only works for one side of an object, not recommended for x or y
 */
public class RatioConstraint extends Constraint {

    private float ratio;

    public RatioConstraint(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public void update(GuiConstraints constraints, GuiConstraints parentConstraints) {
        ConstraintType type = getConstraintType(constraints);
        if(type == ConstraintType.POSITION) {
            if (constraints.getXConstraint() == this) {
                this.value = parentConstraints.getXConstraint().getValue() +
                        parentConstraints.getYConstraint().getValue() * ratio;
            } else if (constraints.getYConstraint() == this) {
                this.value = parentConstraints.getYConstraint().getValue() +
                        parentConstraints.getXConstraint().getValue() * ratio;
            }
        } else if(type == ConstraintType.SIZE) {
            if (constraints.getWidthConstraint() == this) {
                if(constraints.getHeightConstraint().value == 0)
                    constraints.getHeightConstraint().update(constraints, parentConstraints);
                this.value = constraints.getHeightConstraint().getValue() * ratio;
            } else if (constraints.getHeightConstraint() == this) {
                if(constraints.getWidthConstraint().value == 0)
                    constraints.getWidthConstraint().update(constraints, parentConstraints);
                this.value = constraints.getWidthConstraint().getValue() * ratio;
            }
        }
    }

    @Override
    public Constraint clone() {
        return new RatioConstraint(ratio);
    }
}
