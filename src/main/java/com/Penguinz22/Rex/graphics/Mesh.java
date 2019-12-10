package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Disposable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mesh implements Disposable {

    static final List<Mesh> meshes = new ArrayList<>();

    private int vaoHandle;
    private int vertexCount;
    private int attribCount;

    private List<Integer> bufferObjects = new ArrayList<>();

    public Mesh(VertexAttribute... attributes) {
        createVao();
        bind();

        this.attribCount = attributes.length;
        for(VertexAttribute attribute: attributes) {
            bindAttribute(attribute);
        }
        unbind();

        addGlobalMesh(this);
    }

    public void editVboData(int vbo, float[] data) {
        int id = bufferObjects.get(vbo);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void createVao() {
        this.vaoHandle = GL30.glGenVertexArrays();
    }

    public void bind() {
        GL30.glBindVertexArray(this.vaoHandle);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void enableVertexArrays() {
        for(int i=0;i<attribCount;i++) {
            GL30.glEnableVertexAttribArray(i);
        }
    }

    public void disableVertexArrays() {
        for(int i=0;i<attribCount;i++) {
            GL30.glDisableVertexAttribArray(i);
        }
    }

    public void bindAttribute(VertexAttribute attribute) {
        int vboHandle = GL15.glGenBuffers();
        bufferObjects.add(vboHandle);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, attribute.data, GL15.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(attribute.getUsage(), attribute.getSize(), GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboHandle);

        if(attribute.getUsage() == VertexAttribute.Usage.POSITION)
            vertexCount = attribute.data.length/attribute.getSize();
    }

    public int getAttribCount() {
        return attribCount;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public static void disposeMeshes() {
        for(Mesh mesh: meshes)
            mesh.dispose();
    }

    @Override
    public void dispose() {
        GL30.glDeleteVertexArrays(vaoHandle);
        for(Integer vbo: bufferObjects)
            GL15.glDeleteBuffers(vbo);
    }

    static void addGlobalMesh(Mesh mesh) {
        meshes.add(mesh);
    }
}
