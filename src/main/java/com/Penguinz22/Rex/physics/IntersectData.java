package com.Penguinz22.Rex.physics;

import org.joml.Vector2f;

public class IntersectData {

    public final boolean intersects;
    public final Vector2f penetration;

    public IntersectData(boolean intersects, Vector2f penetration) {
        this.intersects = intersects;
        this.penetration = penetration;
    }

}
