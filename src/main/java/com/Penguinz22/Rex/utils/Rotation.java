package com.Penguinz22.Rex.utils;

import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Rotation {

    public final Vector2f origin;
    public final Quaternionf quaternion;

    public Rotation(float angleOfRotation) {
        this(Origin.BOTTOM_LEFT, angleOfRotation);
    }

    public Rotation(Vector2f origin, float angleOfRotation) {
        this.origin = origin;
        this.quaternion = new Quaternionf();
        this.quaternion.fromAxisAngleDeg(new Vector3f(0, 0, 1), -angleOfRotation);
    }
    /**
     * Origin in terms of values from 0 to 1
     */
    public static class Origin {

        public static Vector2f BOTTOM_LEFT = new Vector2f(0, 0);
        public static Vector2f BOTTOM_RIGHT = new Vector2f(1, 0);
        public static Vector2f TOP_LEFT = new Vector2f(0, 1);
        public static Vector2f TOP_RIGHT = new Vector2f(1, 1);

        public static Vector2f MIDDLE_BOTTOM = new Vector2f(0.5f, 0);
        public static Vector2f MIDDLE_LEFT = new Vector2f(0, 0.5f);
        public static Vector2f MIDDLE_RIGHT = new Vector2f(1, 0.5f);
        public static Vector2f MIDDLE_TOP = new Vector2f(0.5f, 1);

        public static Vector2f CENTER = new Vector2f(0.5f, 0.5f);


    }

}
