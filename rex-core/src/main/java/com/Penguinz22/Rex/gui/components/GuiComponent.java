package com.Penguinz22.Rex.gui.components;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.gui.GuiRenderer;
import com.Penguinz22.Rex.gui.animation.Animator;
import com.Penguinz22.Rex.gui.constraints.GuiConstraints;
import com.Penguinz22.Rex.gui.constraints.RelativeConstraint;
import com.Penguinz22.Rex.utils.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiComponent {

    private GuiComponent parent;
    private GuiConstraints constraintsReference;

    private HashMap<GuiComponent, GuiConstraints> children = new HashMap<>();
    private List<GuiComponent> renderList = new ArrayList<>();

    private boolean isMouseHovering = false;
    private Runnable mouseHoverCallback;
    private Runnable mouseEnterCallback;
    private Runnable mouseLeaveCallback;
    private Runnable mouseClickCallback;

    private Animator animator;

    private float alpha = 1;
    private boolean visible = true;
    private Color color = Color.white;

    private List<GuiComponent> toUpdateConstraints = new ArrayList<>();

    public GuiComponent() {
        this.animator = new Animator(this);
    }

    public GuiComponent(GuiComponent parent) {
        this.animator = new Animator(this);
        this.parent = parent;
    }

    // Should override this method
    public void render(GuiRenderer renderer) { }

    // Can override, be sure to check for mouse updates
    public void update() {}

    public void updateSelf() {
        animator.update();
        updateMouse();
        update();
    }

    protected void updateMouse() {
        float x = getConstraintsReference().getXConstraint().getValue();
        float y = getConstraintsReference().getYConstraint().getValue();
        float width = getConstraintsReference().getWidthConstraint().getValue();
        float height = getConstraintsReference().getHeightConstraint().getValue();

        int mouseX = Core.input.getMousePosX();
        int mouseY = Core.input.getMousePosY();

        boolean lastHovering = isMouseHovering;
        if(mouseX >= x && mouseX <= x+width &&
            mouseY >= y && mouseY <= y+height)
            isMouseHovering = true;
        else
            isMouseHovering = false;
        if(!lastHovering && isMouseHovering)
            if(mouseEnterCallback != null)
                mouseEnterCallback.run();

        if(lastHovering && !isMouseHovering)
            if(mouseLeaveCallback != null)
                mouseLeaveCallback.run();

        if(isMouseHovering && mouseHoverCallback != null)
            mouseHoverCallback.run();

        if(isMouseHovering && Core.input.isMouseButtonPressed(0) && mouseClickCallback != null)
            mouseClickCallback.run();
    }

    public void add(GuiComponent component) {
        add(component, new GuiConstraints());
    }

    public void add(GuiComponent component, GuiConstraints constraints) {
        component.setParent(this);
        component.setConstraintsReference(constraints.clone());
        component.updateSelfConstraints();
        children.put(component, constraints.clone());
        renderList.add(component);
        for (GuiComponent toUpdateConstraint : component.toUpdateConstraints) {
            toUpdateConstraint.updateConstraints();
        }
    }

    public void updateConstraints() {
        updateSelfConstraints();
        for (GuiComponent guiComponent : children.keySet()) {
            guiComponent.updateConstraints();
        }
    }

    public void updateSelfConstraints() {
        if(parent == null)
            return;
        if(parent.constraintsReference == null) {
            parent.addToUpdate(this);
            return;
        }
        constraintsReference.update(parent.constraintsReference);
    }

    public void addToUpdate(GuiComponent component) {
        toUpdateConstraints.add(component);
    }

    public void setParent(GuiComponent parent) {
        this.parent = parent;
    }

    public void setConstraintsReference(GuiConstraints constraintsReference) {
        this.constraintsReference = constraintsReference;
    }

    public GuiConstraints getConstraintsReference() {
        return constraintsReference;
    }

    public HashMap<GuiComponent, GuiConstraints> getChildren() {
        return children;
    }

    public void setMouseClickCallback(Runnable mouseClickCallback) {
        this.mouseClickCallback = mouseClickCallback;
    }

    public void setMouseEnterCallback(Runnable mouseEnterCallback) {
        this.mouseEnterCallback = mouseEnterCallback;
    }

    public void setMouseHoverCallback(Runnable mouseHoverCallback) {
        this.mouseHoverCallback = mouseHoverCallback;
    }

    public void setMouseLeaveCallback(Runnable mouseLeaveCallback) {
        this.mouseLeaveCallback = mouseLeaveCallback;
    }

    public boolean isMouseHovering() {
        return isMouseHovering;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Animator getAnimator() {
        return animator;
    }

    public List<GuiComponent> getRenderList() {
        return renderList;
    }
}
