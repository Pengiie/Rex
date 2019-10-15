package com.Penguinz22.Rex.backend;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.listeners.WindowListener;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallback;

public class Window {

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

    public Window(ApplicationListener listener, ApplicationConfig config) {
        this.listener = listener;
        this.config = config;
        this.windowListener = config.getWindowListener();
    }

    public void create(long windowHandle) {
        this.windowHandle = windowHandle;
        GLFW.glfwSetWindowCloseCallback(windowHandle, closeCallback);

        if(windowListener != null){
            windowListener.created();
        }
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
        GLFW.glfwSwapBuffers(windowHandle);
        GLFW.glfwPollEvents();
    }

    public void closeWindow(){
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

}
