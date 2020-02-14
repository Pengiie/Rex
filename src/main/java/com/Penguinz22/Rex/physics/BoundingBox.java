package com.Penguinz22.Rex.physics;

import org.joml.Vector2f;

public abstract class BoundingBox {

    public abstract void update(Vector2f position);

    public abstract  IntersectData intersects(BoundingBox boundingBox);

}
