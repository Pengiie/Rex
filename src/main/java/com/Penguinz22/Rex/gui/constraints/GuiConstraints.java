package com.Penguinz22.Rex.gui.constraints;

import com.Penguinz22.Rex.gui.components.GuiComponent;

public class GuiConstraints {

    public static final GuiConstraints fullConstraint =
            new GuiConstraints()
            .setXConstraint(new RelativeConstraint(0))
            .setYConstraint(new RelativeConstraint(0))
            .setWidthConstraint(new RelativeConstraint(1))
            .setHeightConstraint(new RelativeConstraint(1));


    private Constraint xConstraint;
    private Constraint yConstraint;
    private Constraint widthConstraint;
    private Constraint heightConstraint;

    public GuiConstraints() {
        setDefaultConstraints();
    }

    private void setDefaultConstraints() {
        this.xConstraint = new PixelConstraint(0);
        this.yConstraint = new PixelConstraint(0);
        this.widthConstraint = new RelativeConstraint(0.5f);
        this.heightConstraint = new RelativeConstraint(0.5f);
    }

    public void update(GuiConstraints parentConstraints) {
        if(xConstraint != null)
            xConstraint.update(this, parentConstraints);
        if(yConstraint != null)
            yConstraint.update(this, parentConstraints);
        if(widthConstraint != null)
            widthConstraint.update(this, parentConstraints);
        if(heightConstraint != null)
            heightConstraint.update(this, parentConstraints);
    }

    public GuiConstraints setXConstraint(Constraint xConstraint) {
        this.xConstraint = xConstraint;
        return this;
    }

    public GuiConstraints setYConstraint(Constraint yConstraint) {
        this.yConstraint = yConstraint;
        return this;
    }

    public GuiConstraints setWidthConstraint(Constraint widthConstraint) {
        this.widthConstraint = widthConstraint;
        return this;
    }

    public GuiConstraints setHeightConstraint(Constraint heightConstraint) {
        this.heightConstraint = heightConstraint;
        return this;
    }

    public Constraint getXConstraint() {
        return xConstraint;
    }

    public Constraint getYConstraint() {
        return yConstraint;
    }

    public Constraint getWidthConstraint() {
        return widthConstraint;
    }

    public Constraint getHeightConstraint() {
        return heightConstraint;
    }

    public GuiConstraints clone() {
        GuiConstraints constraints = new GuiConstraints();
        constraints.setXConstraint(xConstraint.clone());
        constraints.setYConstraint(yConstraint.clone());
        constraints.setWidthConstraint(widthConstraint.clone());
        constraints.setHeightConstraint(heightConstraint.clone());
        return constraints;
    }
}
