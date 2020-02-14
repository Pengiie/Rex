package com.Penguinz22.Rex.physics;

import com.Penguinz22.Rex.utils.VectorUtils;
import org.joml.Vector2f;

public class RectBoundingBox extends BoundingBox {

    protected Vector2f pointA, pointB;
    protected Vector2f origin;

    private float width, height;

    public RectBoundingBox(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(Vector2f position) {
        this.pointA = new Vector2f(position.x, position.y);
        this.pointB = new Vector2f(position.x+this.width, position.y+this.height);
        this.origin = new Vector2f(position.x+this.width/2, position.y+this.height/2);
    }

    @Override
    public IntersectData intersects(BoundingBox boundingBox) {
        if(boundingBox instanceof RectBoundingBox) {
            // Using Separating Axis theorem to determine if there is not a gap on a side
            RectBoundingBox other = (RectBoundingBox) boundingBox;
            Vector2f distances1 = VectorUtils.clone(other.pointA).sub(this.pointB);
            Vector2f distances2 = VectorUtils.clone(this.pointA).sub(other.pointB);

            Vector2f maxDistance = VectorUtils.clone(distances1).max(distances2);
            float maxValue = VectorUtils.maxValue(maxDistance);

            // Fixes the penetration value to work per side
            Vector2f direction = VectorUtils.clone(this.origin).sub(other.origin);
            if(direction.x < 0)
                maxDistance.x *= -1;
            if(direction.y < 0)
                maxDistance.y *= -1;

            return new IntersectData(maxValue < 0, maxDistance);
        }
        return new IntersectData(false, new Vector2f());
    }

    public Vector2f getPointA() {
        return pointA;
    }

    public Vector2f getPointB() {
        return pointB;
    }

    public Vector2f getOrigin() {
        return origin;
    }

}
