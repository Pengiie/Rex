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

    public Rotation(Origin origin, float angleOfRotation) {
        this.origin = origin.origin;
        this.quaternion = new Quaternionf();
        this.quaternion.fromAxisAngleDeg(new Vector3f(0, 0, 1), -angleOfRotation);
    }

    /**
     * Origin in terms of values from 0 to 1
     */
    public static class Origin {

        public static Origin BOTTOM_LEFT = new Origin(0, 0);
        public static Origin BOTTOM_RIGHT = new Origin(1, 0);
        public static Origin TOP_LEFT = new Origin(0, 1);
        public static Origin TOP_RIGHT = new Origin(1, 1);

        public static Origin MIDDLE_BOTTOM = new Origin(0.5f, 0);
        public static Origin MIDDLE_LEFT = new Origin(0, 0.5f);
        public static Origin MIDDLE_RIGHT = new Origin(1, 0.5f);
        public static Origin MIDDLE_TOP = new Origin(0.5f, 1);

        public static Origin CENTER = new Origin(0.5f, 0.5f);


        final Vector2f origin;

        public Origin(float x, float y) {
            this.origin = new Vector2f(x, y);
        }

    }

}
