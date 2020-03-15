package com.Penguinz22.Rex.gui.constraints;

/**
 * Only for use of x and y values
 */
public class CenterConstraint extends Constraint {

    @Override
    public void update(GuiConstraints constraints, GuiConstraints parentConstraints) {
        ConstraintType type = getConstraintType(constraints);
        if(type == ConstraintType.POSITION) {
            if(constraints.getXConstraint() == this) {
                if(constraints.getWidthConstraint().value == 0)
                    constraints.getWidthConstraint().update(constraints, parentConstraints);
                this.value = parentConstraints.getXConstraint().getValue() +
                        parentConstraints.getWidthConstraint().getValue()/2 - constraints.getWidthConstraint().getValue()/2;
            } else if(constraints.getYConstraint() == this) {
                if(constraints.getHeightConstraint().value == 0)
                    constraints.getHeightConstraint().update(constraints, parentConstraints);
                this.value = parentConstraints.getYConstraint().getValue() +
                        parentConstraints.getHeightConstraint().getValue()/2 - constraints.getHeightConstraint().getValue()/2;
            }
        }
    }

    @Override
    public Constraint clone() {
        return new CenterConstraint();
    }
}
