package com.Penguinz22.Rex.physics;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.utils.VectorUtils;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.List;

public class RigidBody {

    // Pixels per second
    public float gravity = 0;
    // 0 = Static, not affect by gravity, should not move
    public float mass = 0;
    public float friction = 0f;

    private Vector2f lastPosition;
    public Vector2f position;
    public Vector2f velocity = new Vector2f(0, 0);
    private Vector2f forcedVelocity = new Vector2f(0, 0);
    private Vector2f totalVelocity = new Vector2f(0, 0);

    private HashMap<BoundingBox, Vector2f> boundingBoxes = new HashMap<>();

    public RigidBody(Vector2f position) {
        this.lastPosition = position;
        this.position = position;
    }

    public void update() {
        velocity.add(0, gravity*mass* Core.timings.getDeltaTime());
        if(!lastPosition.equals(position))
            forcedVelocity = VectorUtils.clone(position).sub(lastPosition);

        this.position.add(VectorUtils.clone(velocity).mul(Core.timings.getDeltaTime()));

        for (BoundingBox boundingBox : boundingBoxes.keySet()) {
            boundingBox.update(VectorUtils.clone(boundingBoxes.get(boundingBox)).add(this.position));
        }
        this.lastPosition = VectorUtils.clone(position);
        this.totalVelocity = VectorUtils.clone(velocity).add(forcedVelocity);
    }

    public void checkForCollisions(List<RigidBody> rigidBodies) {
        for (RigidBody rigidBody : rigidBodies) {
            if(rigidBody != this && mass > 0) {
                for (BoundingBox boundingBox : boundingBoxes.keySet()) {
                    for (BoundingBox otherBoundingBox : rigidBody.boundingBoxes.keySet()) {
                        IntersectData intersectData = boundingBox.intersects(otherBoundingBox);
                        if(intersectData.intersects) {
                            if(this.mass > 0)
                                collide(rigidBody, intersectData);
                            //if(rigidBody.mass > 0)
                                //rigidBody.collide(this, intersectData);
                        }
                    }
                }
            }
        }
    }

    public void collide(RigidBody other, IntersectData intersectData) {
        //System.out.println(intersectData.penetration.x+" "+intersectData.penetration.y);

        float penetrationDepth = Math.min(Math.abs(intersectData.penetration.x - 0), Math.abs(intersectData.penetration.y - 0));
        Vector2f relativeVelocity = VectorUtils.clone(velocity).sub(other.velocity);

        if(other.mass <= 0) {
            Vector2f relativePosition = VectorUtils.clone(other.position).sub(this.position);
            Vector2f relativity = VectorUtils.clone(relativePosition).normalize(Math.abs(relativeVelocity.x)+Math.abs(relativeVelocity.y)).add(relativeVelocity);
            System.out.println(relativePosition.y);
            System.out.println(" f " +relativeVelocity.y);
            // Resolve on the lowest penetration value, probably will break with higher speeds
            if (Math.abs(intersectData.penetration.x - 0) < Math.abs(intersectData.penetration.y - 0)) {
                this.lastPosition.x = this.position.x -= intersectData.penetration.x;
            } else {
                this.lastPosition.y = this.position.y -= intersectData.penetration.y;
            }

            //this.velocity.x = 0;
            //this.velocity.y = 0;
        } else {
            //Vector2f relativeVelocity = VectorUtils.clone(other.totalVelocity).sub(totalVelocity);
            //relativeVelocity.mul(mass);
            //this.velocity.add(relativeVelocity);
            System.out.println(relativeVelocity);
        }
    }

    public void addBoundingBox(BoundingBox boundingBox, Vector2f positionalOffset) {
        this.boundingBoxes.put(boundingBox, positionalOffset);
    }

}
