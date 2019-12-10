package com.Penguinz22.Rex;

import com.Penguinz22.Rex.utils.Key;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InputManager {

    private HashMap<Key, List<Modifier>> keyPresses = new HashMap();
    private HashMap<Key, List<Modifier>> keyHolds = new HashMap<>();
    private HashMap<Key, List<Modifier>> keyReleases = new HashMap<>();

    private Vector2f mousePosition = new Vector2f(0, 0);
    private List<Integer> mousePresses = new ArrayList<>();
    private List<Integer> mouseHolds = new ArrayList<>();
    private List<Integer> mouseReleases = new ArrayList<>();

    GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int keyCode, int scancode, int action, int mods) {
            Key key = Key.getKeyCode(keyCode);
            if(key == null)
                return;
            List<Modifier> modifiers = new ArrayList<>();
            switch(mods) {
                case 1: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.SHIFT})); break;
                case 2: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CONTROL})); break;
                case 3: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.SHIFT, Modifier.CONTROL})); break;
                case 4: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.ALT})); break;
                case 5: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.SHIFT, Modifier.ALT})); break;
                case 6: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CONTROL, Modifier.ALT})); break;
                case 7: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.SHIFT, Modifier.CONTROL, Modifier.ALT})); break;
                case 16: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK})); break;
                case 17: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK, Modifier.SHIFT})); break;
                case 18: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK, Modifier.CONTROL})); break;
                case 19: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK, Modifier.SHIFT, Modifier.CONTROL})); break;
                case 20: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK, Modifier.ALT})); break;
                case 21: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK, Modifier.SHIFT, Modifier.ALT})); break;
                case 22: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK, Modifier.CONTROL, Modifier.ALT})); break;
                case 23: modifiers.addAll(Arrays.asList(new Modifier[]{Modifier.CAPS_LOCK, Modifier.SHIFT, Modifier.CONTROL, Modifier.ALT})); break;
            }
            if(action == GLFW.GLFW_PRESS) {
                keyPresses.put(key, modifiers);
                keyHolds.put(key, modifiers);
            } else if(action == GLFW.GLFW_REPEAT)
                keyHolds.put(key, modifiers);
            else if(action == GLFW.GLFW_RELEASE)
                keyReleases.put(key, modifiers);
        }
    };

    GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if(action == GLFW.GLFW_PRESS) {
                mousePresses.add(button);
                mouseHolds.add(button);
            } else if(action == GLFW.GLFW_REPEAT)
                mouseHolds.add(button);
            else if(action == GLFW.GLFW_RELEASE)
                mouseReleases.add(button);
        }
    };

    GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            mousePosition.x = (float) xpos;
            mousePosition.y = (float) ypos * -1 + Core.window.getHeight();
        }
    };

    public InputManager(long windowHandle) {
        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_LOCK_KEY_MODS, GLFW.GLFW_TRUE);
    }

    void setCallbacks(long windowHandle) {
        GLFW.glfwSetKeyCallback(windowHandle, keyCallback);
        GLFW.glfwSetCursorPosCallback(windowHandle, cursorPosCallback);
        GLFW.glfwSetMouseButtonCallback(windowHandle, mouseButtonCallback);
    }

    void clearInputs() {
        mousePresses.clear();
        mouseReleases.clear();
        keyPresses.clear();
        keyReleases.clear();
    }

    public boolean isKeyPressed(Key key, Modifier... modifiers) {
        if(keyPresses.containsKey(key)) {
            for (Modifier modifier : modifiers) {
                if(!keyPresses.get(key).contains(modifier))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean isKeyDown(Key key, Modifier... modifiers) {
        if(keyHolds.containsKey(key)) {
            for (Modifier modifier : modifiers) {
                if(!keyPresses.get(key).contains(modifier))
                    return false;
                return true;
            }
        }
        return false;
    }

    public boolean isKeyReleased(Key key, Modifier... modifiers) {
        if(keyReleases.containsKey(key)) {
            for (Modifier modifier : modifiers) {
                if(!keyPresses.get(key).contains(modifier))
                    return false;
                return true;
            }
        }
        return false;
    }

    public boolean isMouseButtonPressed(int button) {
        return mousePresses.contains(button);
    }

    public boolean isMouseButtonDown(int button) {
        return mouseHolds.contains(button);
    }

    public boolean isMouseButtonReleased(int button) {
        return mouseReleases.contains(button);
    }

    public int getMousePosX() {
        return (int) mousePosition.x;
    }

    public int getMousePosY() {
        return (int) mousePosition.y;
    }

    public HashMap<Key, List<Modifier>> getKeyPresses() {
        return keyPresses;
    }

    public enum Modifier {

        SHIFT(GLFW.GLFW_MOD_SHIFT),
        CONTROL(GLFW.GLFW_MOD_CONTROL),
        ALT(GLFW.GLFW_MOD_ALT),
        CAPS_LOCK(GLFW.GLFW_MOD_CAPS_LOCK),
        NUM_LOCK(GLFW.GLFW_MOD_NUM_LOCK);

        private final int modCode;

        Modifier(int modCode) {
            this.modCode = modCode;
        }

    }

}
