package com.Penguinz22.Rex.gui.components;

import com.Penguinz22.Rex.gui.GuiRenderer;
import com.Penguinz22.Rex.gui.constraints.GuiConstraints;
import com.Penguinz22.Rex.utils.Color;

/**
 * A solid block of color
 */
public class GuiBlock extends GuiComponent {

    public GuiBlock(Color color) {
        setColor(color);
    }

    @Override
    public void render(GuiRenderer renderer) {
        renderer.draw(getColor().clone().setAlpha(getAlpha()),
                getConstraintsReference().getXConstraint().getValue(), getConstraintsReference().getYConstraint().getValue(),
                getConstraintsReference().getWidthConstraint().getValue(), getConstraintsReference().getHeightConstraint().getValue(), true);
    }
}
