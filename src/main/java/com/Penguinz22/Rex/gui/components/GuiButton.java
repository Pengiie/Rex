package com.Penguinz22.Rex.gui.components;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.gui.constraints.GuiConstraints;
import com.Penguinz22.Rex.gui.constraints.RelativeConstraint;
import com.Penguinz22.Rex.utils.Color;

public abstract class GuiButton extends GuiComponent {

    private GuiComponent base;
    private GuiComponent hoverBase;
    private GuiText text;
    private GuiText hoverText;

    private Runnable mouseEnterCallback;
    private Runnable mouseLeaveCallback;

    public GuiButton(GuiComponent base, GuiText text) {
        this.base = base;
        this.text = text;
        Color baseColor = text.getColor();

        GuiConstraints constraints = new GuiConstraints();

        constraints.setXConstraint(new RelativeConstraint(0));
        constraints.setYConstraint(new RelativeConstraint(0));
        constraints.setWidthConstraint(new RelativeConstraint(1));
        constraints.setHeightConstraint(new RelativeConstraint(1));

        this.add(base, constraints);

        constraints.setXConstraint(new RelativeConstraint(0.05f));
        constraints.setYConstraint(new RelativeConstraint(0.05f));
        constraints.setWidthConstraint(new RelativeConstraint(0.9f));
        constraints.setHeightConstraint(new RelativeConstraint(0.9f));
        this.add(text, constraints);

        super.setMouseEnterCallback(() -> {
            if(mouseEnterCallback != null)
                mouseEnterCallback.run();
        });

        super.setMouseLeaveCallback(() -> {
            if(mouseLeaveCallback != null)
                mouseLeaveCallback.run();
        });

        this.setMouseClickCallback(() -> onClick(Core.input.getMousePosX(), Core.input.getMousePosY()));
    }

    public GuiButton(GuiComponent base, GuiText text, GuiComponent hoverBase, GuiText hoverText) {
        this.base = base;
        this.text = text;
        this.hoverBase = hoverBase;
        this.hoverText = hoverText;
        Color baseColor = text.getColor();

        GuiConstraints constraints = new GuiConstraints();

        constraints.setXConstraint(new RelativeConstraint(0));
        constraints.setYConstraint(new RelativeConstraint(0));
        constraints.setWidthConstraint(new RelativeConstraint(1));
        constraints.setHeightConstraint(new RelativeConstraint(1));

        this.add(base, constraints);
        this.add(hoverBase, constraints);
        hoverBase.setVisible(false);

        constraints.setXConstraint(new RelativeConstraint(0.05f));
        constraints.setYConstraint(new RelativeConstraint(0.05f));
        constraints.setWidthConstraint(new RelativeConstraint(0.9f));
        constraints.setHeightConstraint(new RelativeConstraint(0.9f));
        this.add(text, constraints);
        this.add(hoverText, constraints);
        hoverText.setVisible(false);

        this.setMouseClickCallback(() -> onClick(Core.input.getMousePosX(), Core.input.getMousePosY()));

        super.setMouseEnterCallback(() -> {
            text.setVisible(false);
            hoverText.setVisible(true);

            base.setVisible(false);
            hoverBase.setVisible(true);

            System.out.println("test");
            if(mouseEnterCallback != null) {
                mouseEnterCallback.run();

            }
        });

        super.setMouseLeaveCallback(() -> {
            text.setVisible(true);
            hoverText.setVisible(false);

            base.setVisible(true);
            hoverBase.setVisible(false);

            if(mouseLeaveCallback != null)
                mouseLeaveCallback.run();
        });
    }

    @Override
    public void setMouseEnterCallback(Runnable mouseEnterCallback) {
        this.mouseEnterCallback = mouseEnterCallback;
    }

    @Override
    public void setMouseLeaveCallback(Runnable mouseLeaveCallback) {
        this.mouseLeaveCallback = mouseLeaveCallback;
    }

    public abstract void onClick(int mouseX, int mouseY);

}
