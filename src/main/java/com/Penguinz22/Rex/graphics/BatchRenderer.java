package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.Window;
import com.Penguinz22.Rex.utils.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.Stack;

public class BatchRenderer implements Disposable {

    private Mesh mesh;
    private final Shader shader;

    private boolean blending;
    private Texture lastTexture;
    private Matrix4f projectionMatrix;
    private Matrix4f combinedMatrix = new Matrix4f();

    private int renderCalls;
    private int totalRenderCalls;

    Stack<RenderRequest> renderQueue = new Stack<>();

    public BatchRenderer() {
        this(null, true);
    }

    public BatchRenderer(Shader shader, boolean blending) {

        this.blending = blending;
        this.projectionMatrix = ProjectionUtils.createOrthoMatrix(Core.window.getWidth(), 0, Core.window.getHeight(), 0, -1, 1);
        this.mesh = new Mesh(
                new VertexAttribute(VertexAttribute.Usage.POSITION, 2, MeshData.rectData.positions),
                new VertexAttribute(VertexAttribute.Usage.TEXTURE_COORDS, 2, MeshData.rectData.textureCoords));


        if(shader == null) {
            this.shader = BatchShader.create();
        } else {
            this.shader = shader;
        }
    }

    public void draw(Color color, int x, int y, int width, int height) {
        draw(null, color, x, y, width, height, null);
    }

    public void draw(Color color, int x, int y, int width, int height, Rotation rotation) {
        draw(null, color, x, y, width, height, rotation);
    }

    public void draw(Texture texture, Color color, int x, int y, int width, int height, Rotation rotation) {
        if(texture!=null&&(lastTexture==null||!lastTexture.equals(texture)))
            switchTexture(texture);
        else if(texture == null && lastTexture != null) {
            switchTexture(null);
        }
        if(texture != null)
            renderQueue.push(new RenderRequest(TransformationUtils.createTransformation(x,y,width,height,rotation),
                VectorUtils.clone(texture.getOffset()), VectorUtils.clone(texture.getSize()),
                color.clone()));
        else
            renderQueue.push(new RenderRequest(TransformationUtils.createTransformation(x, y, width, height, rotation),
                    VectorUtils.zero, VectorUtils.identity,
                    color.clone()));
    }

    private void switchTexture(Texture texture) {
        flush();
        lastTexture = texture;
    }

    public void finish() {
        flush();
        renderCalls = 0;
    }

    public void flush() {
        renderCalls++;
        totalRenderCalls++;

        shader.start();

        boolean hasTexture = true;
        if(lastTexture!=null)
            lastTexture.bind();
        else
            hasTexture = false;

        if(blending) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        } else {
            GL11.glDisable(GL11.GL_BLEND);
        }

        mesh.bind();
        mesh.enableVertexArrays();

        while(!renderQueue.empty()) {
            RenderRequest renderRequest = renderQueue.pop();

            resolveMatrices(renderRequest.transformation);
            shader.setUniformMat4(Shader.COMBINED_MATRIX_UNIFORM, this.combinedMatrix);

            shader.setUniformVec2f(BatchShader.TEXTURE_OFFSET_UNIFORM, renderRequest.textureOffset);
            shader.setUniformVec2f(BatchShader.TEXTURE_UNIT_SIZE_UNIFORM, renderRequest.textureSize);
            shader.setUniformBool(BatchShader.TEXTURE_HAS_UNIFORM, hasTexture);

            shader.setUniformVec4f(Shader.COLOR_UNIFORM, renderRequest.color.toVector4f());

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());
        }

        mesh.disableVertexArrays();
        shader.stop();
    }

    public int getRenderCalls() {
        return renderCalls;
    }

    private void resolveMatrices(Matrix4f transformation) {
        this.combinedMatrix.identity();
        this.combinedMatrix.set(projectionMatrix).mul(transformation);
    }

    @Override
    public void dispose() {

    }
}
