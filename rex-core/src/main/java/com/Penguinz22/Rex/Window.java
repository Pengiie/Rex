package com.Penguinz22.Rex;

import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.listeners.WindowListener;
import com.Penguinz22.Rex.utils.CursorType;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

public class Window {

    private int width, height;

    private long windowHandle;
    private ApplicationListener listener;
    private WindowListener windowListener;
    private ApplicationConfig config;
    private boolean initializedListener = false;
    private final GLFWWindowCloseCallback closeCallback = new GLFWWindowCloseCallback() {
        @Override
        public void invoke(long window) {

        }
    };

    private boolean resizedYet = false;
    private final GLFWWindowSizeCallback resizeCallback = new GLFWWindowSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            if(!resizedYet) {
                resizedYet = true;
                return;
            }
            updateSizeFields(width, height);
            GL11.glViewport(0, 0, width, height);
            listener.resize(width, height);
        }
    };

    private long normalCursor;
    private long handCursor;
    private long textInputCursor;

    private CursorType currentCursor = CursorType.NORMAL;

    public Window(ApplicationListener listener, ApplicationConfig config) {
        this.listener = listener;
        this.config = config;
        this.windowListener = config.getWindowListener();
        updateSizeFields(config.windowWidth, config.windowHeight);
    }

    private void updateSizeFields(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void create(long windowHandle) {
        this.windowHandle = windowHandle;
        Core.window = this;
        GLFW.glfwSetWindowCloseCallback(windowHandle, closeCallback);
        GLFW.glfwSetWindowSizeCallback(windowHandle, resizeCallback);

        Core.input = new InputManager(windowHandle);
        Core.input.setCallbacks(windowHandle);

        createCursors();

        if(windowListener != null){
            windowListener.created();
        }
    }

    private void createCursors() {
        this.normalCursor = GLFW.glfwCreateStandardCursor(CursorType.NORMAL.getGlfwEquivalent());
        this.handCursor = GLFW.glfwCreateStandardCursor(CursorType.SELECT_HAND.getGlfwEquivalent());
        this.textInputCursor = GLFW.glfwCreateStandardCursor(CursorType.TEXT_INPUT.getGlfwEquivalent());
    }

    // Update every frame
    public void updateCurrentCursorType(CursorType type) {
        this.currentCursor = type;
    }

    private void setCursor(CursorType type) {
        if(type == CursorType.NORMAL)
            GLFW.glfwSetCursor(windowHandle, normalCursor);
        else if(type == CursorType.SELECT_HAND)
            GLFW.glfwSetCursor(windowHandle, handCursor);
        else if(type == CursorType.TEXT_INPUT)
            GLFW.glfwSetCursor(windowHandle, textInputCursor);
    }

    public void setVisible(boolean visible) {
        if(visible){
            GLFW.glfwShowWindow(windowHandle);
        }else{
            GLFW.glfwHideWindow(windowHandle);
        }
    }

    public void update() {
        if(!initializedListener) {
            listener.init();
            initializedListener = true;
        }
        setCursor(currentCursor);
        currentCursor = CursorType.NORMAL;

        GLFW.glfwSwapBuffers(windowHandle);
        GLFW.glfwPollEvents();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void closeWindow(){
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
