package com.Penguinz22.Rex.utils;

import com.Penguinz22.Rex.backend.Window;
import org.joml.Matrix4f;

public class ProjectionUtils {

    public static Matrix4f createOrthoMatrix(float right, float left, float top, float bottom, float zNear, float zFar) {
        Matrix4f matrix = new Matrix4f();
        matrix.m00((float)2/(right-left));
        matrix.m11((float)2/(top-bottom));
        matrix.m22((float)-2/(zFar-zNear));
        matrix.m03(-(right+left)/(right-left));
        matrix.m13(-(top+bottom)/(top-bottom));
        matrix.m23(-(zFar+zNear)/(zFar-zNear));
        matrix.m33(1);
        return matrix.transpose();
    }

}
