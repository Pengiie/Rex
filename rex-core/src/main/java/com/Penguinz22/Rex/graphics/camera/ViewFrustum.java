package com.Penguinz22.Rex.graphics.camera;

import org.joml.Vector2f;

public class ViewFrustum {

    final int left, right, top, bottom;

    public ViewFrustum(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public boolean inFrustum(Vector2f pointA, Vector2f pointB) {
        if(pointA.x >= left && pointA.x <= right &&
                pointA.y <= top && pointA.y >= bottom)
            return true;
        if(pointB.x >= left && pointB.x <= right &&
                pointB.y <= top && pointB.y >= bottom)
            return true;
        return false;
    }

}
