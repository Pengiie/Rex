package com.Penguinz22.Rex.utils;

import org.joml.Vector2f;

public class VectorUtils {

    public static final Vector2f zero = new Vector2f(0, 0);
    public static final Vector2f identity = new Vector2f(1, 1);

    public static Vector2f clone(Vector2f vector2f) {
        return new Vector2f(vector2f.x, vector2f.y);
    }

}
