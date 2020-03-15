package com.Penguinz22.Rex.graphics.camera;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.utils.ProjectionUtils;
import com.Penguinz22.Rex.utils.TransformationUtils;
import com.Penguinz22.Rex.utils.VectorUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera {

    public Vector2f position;
    // Euler's rotation
    public float rotY;

    // Zoom from 0 - Max
    public float zoom;

    private ViewFrustum viewFrustum;

    private Matrix4f viewMatrix;
    private Matrix4f orthoMatrix;
    private Matrix4f orthoViewMatrix;

    public Camera() {
        this.position = VectorUtils.zero;
        this.rotY = 0;
        this.zoom = 1;

        createOrthoMatrix();
        update();
    }

    public void update() {
        this.viewMatrix = TransformationUtils.createViewMatrix(position.x, position.y, rotY);
        this.orthoViewMatrix = new Matrix4f(this.orthoMatrix).mul(viewMatrix);
        this.viewFrustum = new ViewFrustum(
                (int) (this.position.x - Core.window.getWidth()/2),
                (int) (this.position.x + Core.window.getWidth()/2),
                (int) (this.position.y + Core.window.getHeight()/2),
                (int) (this.position.y - Core.window.getHeight()/2)
        );
    }

    public ViewFrustum getViewFrustum() {
        return viewFrustum;
    }

    public void resized() {
        createOrthoMatrix();
    }

    private void createOrthoMatrix() {
        this.orthoMatrix = ProjectionUtils.createOrthoMatrix(Core.window.getWidth(), 0, Core.window.getHeight(), 0, -1, 1);
    }

    public Matrix4f getOrthoViewMatrix() {
        return orthoViewMatrix;
    }
}
