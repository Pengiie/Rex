package com.Penguinz22.Rex.physics;

import java.util.ArrayList;
import java.util.List;

public class PhysicsManager {

    public List<RigidBody> rigidBodies = new ArrayList<>();

    public void update() {
        for (RigidBody rigidBody : rigidBodies) {
            rigidBody.update();
        }
        for (RigidBody rigidBody : rigidBodies) {
            rigidBody.checkForCollisions(rigidBodies);
        }
    }

    public void addRigidBody(RigidBody rigidBody) {
        rigidBodies.add(rigidBody);
    }

}
