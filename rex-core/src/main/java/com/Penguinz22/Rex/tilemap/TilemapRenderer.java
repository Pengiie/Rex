package com.Penguinz22.Rex.tilemap;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.graphics.BatchShader;
import com.Penguinz22.Rex.graphics.Mesh;
import com.Penguinz22.Rex.graphics.Shader;
import com.Penguinz22.Rex.graphics.camera.Camera;
import com.Penguinz22.Rex.utils.ProjectionUtils;
import com.Penguinz22.Rex.utils.TransformationUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

import java.util.Stack;

public class TilemapRenderer {

    public boolean frustumCulling = true;

    private final Shader shader;

    private Matrix4f projectionMatrix;
    private Matrix4f combinedMatrix = new Matrix4f();
    private Camera camera;

    private int renderCalls;
    private int totalRenderCalls;

    private Tilemap lastTilemap = null;
    private Stack<Tilemap> tilemapQueue = new Stack<>();

    public TilemapRenderer() {
        this.projectionMatrix = ProjectionUtils.createOrthoMatrix(Core.window.getWidth(), 0, Core.window.getHeight(), 0, -1, 1);
        this.shader = TilemapShader.create();
    }

    public void render(Tilemap tilemap) {
        if(lastTilemap != null &&
                !tilemap.getTileset().equals(lastTilemap.getTileset()))
            flush();
        tilemapQueue.add(tilemap);
    }

    public void flush() {
        if(tilemapQueue.size() == 0)
            return;
        renderCalls++;
        totalRenderCalls++;

        shader.start();

        Tileset tileset = tilemapQueue.peek().getTileset();
        tileset.getAtlas().bind();

        while(!tilemapQueue.isEmpty()) {
            Tilemap tilemap = tilemapQueue.pop();
            for (Vector2i chunkPosition : tilemap.getChunks().keySet()) {
                // Checks if frustum culling and if the camera exists then checks the chunk in the view
                if(frustumCulling && camera != null && !camera.getViewFrustum().inFrustum(
                        new Vector2f(
                                tilemap.position.x + chunkPosition.x * tilemap.chunkSize * tilemap.tileSize,
                                tilemap.position.y + chunkPosition.y * tilemap.chunkSize * tilemap.tileSize),
                        new Vector2f(
                                tilemap.position.x + (chunkPosition.x + 1) * tilemap.chunkSize * tilemap.tileSize,
                                tilemap.position.y + (chunkPosition.y + 1) * tilemap.chunkSize * tilemap.tileSize)))
                    continue;
                Chunk chunk = tilemap.getChunks().get(chunkPosition);

                Mesh mesh = chunk.getMesh();

                mesh.bind();
                mesh.enableVertexArrays();

                resolveMatrices(TransformationUtils.createTransformation(
                        tilemap.position.x + chunkPosition.x * tilemap.chunkSize * tilemap.tileSize,
                        tilemap.position.y + chunkPosition.y * tilemap.chunkSize * tilemap.tileSize,
                        tilemap.tileSize,
                        tilemap.tileSize,
                        null));
                shader.setUniformMat4(BatchShader.ORTHO_VIEW_TRANSFORMATION_MATRIX, this.combinedMatrix);

                GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());

                mesh.disableVertexArrays();
            }
        }
        shader.stop();
    }

    private void resolveMatrices(Matrix4f transformation) {
        this.combinedMatrix.identity();
        if(camera == null) {
            this.combinedMatrix.set(projectionMatrix).mul(transformation);
        } else {
            this.combinedMatrix.set(camera.getOrthoViewMatrix()).mul(transformation);
        }
    }

    public void finish() {
        totalRenderCalls = 0;
        flush();
    }

    public void attachCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

}
