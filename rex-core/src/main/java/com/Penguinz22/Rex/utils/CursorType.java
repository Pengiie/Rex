package com.Penguinz22.Rex.utils;

import org.lwjgl.glfw.GLFW;

public enum CursorType {
    NORMAL(GLFW.GLFW_ARROW_CURSOR),
    TEXT_INPUT(GLFW.GLFW_IBEAM_CURSOR),
    SELECT_HAND(GLFW.GLFW_HAND_CURSOR);

    private int glfwEquivalent;

    CursorType(int glfwEquivalent) {
        this.glfwEquivalent = glfwEquivalent;
    }

    public int getGlfwEquivalent() {
        return glfwEquivalent;
    }
}
