package com.Penguinz22.Rex.gui.components;

import com.Penguinz22.Rex.gui.GuiRenderer;
import com.Penguinz22.Rex.utils.Color;

/**
 * A solid block of color
 */
public class GuiBlock extends GuiComponent {

    private Color color;

    public GuiBlock(Color color) {
        this.color = color;
    }

    @Override
    public void render(GuiRenderer renderer) {
        renderer.draw(color,
                getConstraintsReference().getXConstraint().getValue(), getConstraintsReference().getYConstraint().getValue(),
                getConstraintsReference().getWidthConstraint().getValue(), getConstraintsReference().getHeightConstraint().getValue(), false);
    }
}
