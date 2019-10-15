package com.Penguinz22.Rex;

import com.Penguinz22.Rex.backend.Window;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.utils.Disposable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUtil;

public class Application implements Disposable {

    private final ApplicationListener listener;
    private final ApplicationConfig config;
    private final Window window;
    private volatile boolean running = true;

    public Application(ApplicationListener listener, ApplicationConfig config) {
        initGLFW();
        this.listener = listener;
        this.config = ApplicationConfig.copyConfig(config);
        if(this.config.title == null)
            this.config.title = listener.getClass().getSimpleName();
        Core.application = this;
        this.window = createWindow(listener, config);
        loop();
        cleanup();
    }

    private void loop() {
        while(running && window != null) {
            if(window.shouldClose())
                running = false;
            window.update();
            listener.update();
        }
    }

    private void cleanup() {
        dispose();
        GLFW.glfwTerminate();
    }

    public Window createWindow(ApplicationListener listener, ApplicationConfig config) {
        final Window window = new Window(listener, config);
        long windowHandle = createGlfwWindow(config);
        window.create(windowHandle);
        window.setVisible(true);
        return window;
    }

    private void initGLFW() {
        GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
        if(!GLFW.glfwInit())
            throw new RuntimeException("Unable to initialize GLFW!");
    }

    static long createGlfwWindow(ApplicationConfig config){
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, config.windowResizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);

        long windowHandle = GLFW.glfwCreateWindow(config.windowWidth, config.windowHeight, config.title, 0, 0);
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(windowHandle, vidMode.width() / 2 - config.windowWidth / 2, vidMode.height() / 2 - config.windowHeight / 2);

        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwSwapInterval(0);
        GL.createCapabilities();

        return windowHandle;
    }

    @Override
    public void dispose() {
        if(Core.assets != null)
            Core.assets.dispose();
    }

}
