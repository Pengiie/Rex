package com.Penguinz22.Rex.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class TransformationUtils {

    private static Matrix4f tempMatrix = new Matrix4f();

    public static Matrix4f createTransformation(float x, float y, float width, float height, Rotation rotation) {
        tempMatrix.identity();
        tempMatrix.translate(x, y, 0);
        if(rotation!=null)
            tempMatrix.rotateAroundLocal(rotation.quaternion, x+width*rotation.origin.x ,y+height*rotation.origin.y ,0);
        tempMatrix.scale(width, height, 1);
        return new Matrix4f(tempMatrix);
    }

}
