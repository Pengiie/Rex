package com.Penguinz22.Rex.gui.components;

import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.gui.GuiRenderer;
import com.Penguinz22.Rex.utils.Color;
import com.Penguinz22.Rex.utils.Rotation;

/**
 * A solid block of color
 */
public class GuiImage extends GuiComponent {

    public Texture texture;

    public GuiImage(Texture texture) {
        this.texture = texture;
    }

    public GuiImage(Texture texture, Color color) {
        this.texture = texture;
        setColor(color);
    }

    @Override
    public void render(GuiRenderer renderer) {
        renderer.draw(texture,
                getColor().clone().setAlpha(getAlpha()),
                getConstraintsReference().getXConstraint().getValue(), getConstraintsReference().getYConstraint().getValue(),
                getConstraintsReference().getWidthConstraint().getValue(), getConstraintsReference().getHeightConstraint().getValue(),
                true,
                new Rotation(0));
    }
}
