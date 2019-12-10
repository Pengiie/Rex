package com.Penguinz22.Rex.gui;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.assets.Font;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.graphics.*;
import com.Penguinz22.Rex.gui.components.GuiComponent;
import com.Penguinz22.Rex.gui.constraints.FinalConstraint;
import com.Penguinz22.Rex.gui.constraints.GuiConstraints;
import com.Penguinz22.Rex.utils.*;
import com.Penguinz22.Rex.utils.annotations.Order;
import com.Penguinz22.Rex.utils.annotations.RenderOrder;
import com.Penguinz22.Rex.utils.fonts.Character;
import com.Penguinz22.Rex.utils.fonts.FontShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTruetype;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

public class GuiRenderer implements Disposable{

    public GuiComponent screenComponent;

    public final Shader shader;
    public final Mesh rectMesh;

    public final Shader fontShader;
    public final Mesh fontMesh;

    private Matrix4f projectionMatrix;
    private Matrix4f combinedMatrix = new Matrix4f();

    public GuiRenderer() {
        this.screenComponent = generateScreenComponent();

        this.shader = GuiShader.create();
        this.rectMesh = new Mesh(
                new VertexAttribute(VertexAttribute.Usage.POSITION, 2, MeshData.rectData.positions),
                new VertexAttribute(VertexAttribute.Usage.TEXTURE_COORDS, 2, MeshData.rectData.textureCoords));

        this.fontShader = FontShader.create();
        this.fontMesh = new Mesh(
                new VertexAttribute(VertexAttribute.Usage.POSITION, 4, new float[24]));

        this.projectionMatrix = ProjectionUtils.createOrthoMatrix(Core.window.getWidth(), 0, Core.window.getHeight(), 0, -1, 1);
    }

    public GuiComponent generateScreenComponent() {
        GuiConstraints constraints = new GuiConstraints();
        constraints.setXConstraint(new FinalConstraint(0));
        constraints.setYConstraint(new FinalConstraint(0));
        constraints.setWidthConstraint(new FinalConstraint(Core.window.getWidth()));
        constraints.setHeightConstraint(new FinalConstraint(Core.window.getHeight()));
        GuiComponent screen = new GuiComponent();
        screen.setConstraintsReference(constraints);
        return screen;
    }

    public void resized(int width, int height) {
        GuiConstraints constraints = new GuiConstraints();
        constraints.setXConstraint(new FinalConstraint(0));
        constraints.setYConstraint(new FinalConstraint(0));
        constraints.setWidthConstraint(new FinalConstraint(width));
        constraints.setHeightConstraint(new FinalConstraint(height));
        screenComponent.setConstraintsReference(constraints);

        updateGuiConstraints();

        this.projectionMatrix = ProjectionUtils.createOrthoMatrix(width, 0, height, 0, -1, 1);
    }

    public void updateGuiConstraints() {
        screenComponent.updateConstraints();
    }

    // Remember this is not automatically called, must call in your update method in your application listener somewhere
    public void updateScreen() {
        update(screenComponent);
    }

    public void update(GuiComponent component) {
        component.updateSelf();
        for (GuiComponent guiComponent : component.getRenderList()) {
            update(guiComponent);
        }
    }

    // Must be called in your render method, same as updateScreen
    public void renderScreen() {
        startRendering();
        render(screenComponent);
        stopRendering();
    }

    public void render(GuiComponent component) {
        boolean renderAfter = false;
        try {
            Class<? extends GuiComponent> clazz = component.getClass();
            Method renderMethod = clazz.getMethod("render", GuiRenderer.class);
            Annotation[] annotations = renderMethod.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation instanceof RenderOrder)
                    if(((RenderOrder) annotation).value() == Order.AFTER) {
                        renderAfter = true;
                        break;
                    }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if(component.isVisible() && !renderAfter)
            component.render(this);
        for (GuiComponent guiComponent : component.getRenderList()) {
            render(guiComponent);
        }
        if(component.isVisible() && renderAfter)
            component.render(this);
    }

    public void startRendering() {
        shader.start();
        rectMesh.bind();
        rectMesh.enableVertexArrays();
    }

    public void stopRendering() {
        rectMesh.disableVertexArrays();
        rectMesh.unbind();
        shader.stop();
    }

    public void draw(Color color, float x, float y, float width, float height, boolean blend) {
        draw(null, color, x, y, width, height, blend, new Rotation(0));
    }

    public void draw(Color color, float x, float y, float width, float height, Rotation rotation) {
        draw(null, color, x, y, width, height, false, rotation);
    }

    public void draw(Color color, float x, float y, float width, float height, boolean blend, Rotation rotation) {
        draw(null, color, x, y, width, height, blend, rotation);
    }

    public void draw(Texture texture, Color color, float x, float y, float width, float height, boolean blend, Rotation rotation) {
        Vector2f textureOffset = VectorUtils.zero;
        Vector2f textureSize = VectorUtils.identity;
        if(texture != null) {
            texture.bind();
            textureOffset = VectorUtils.clone(texture.getOffset());
            textureSize = VectorUtils.clone(texture.getSize());
        }
        shader.setUniformBool(GuiShader.TEXTURE_HAS_UNIFORM, texture != null);

        if(blend) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        } else {
            GL11.glDisable(GL11.GL_BLEND);
        }

        Matrix4f transformation = TransformationUtils.createTransformation(x,y,width,height,rotation);
        resolveMatrices(transformation);

        shader.setUniformMat4(Shader.COMBINED_MATRIX_UNIFORM, this.combinedMatrix);

        shader.setUniformVec2f(BatchShader.TEXTURE_OFFSET_UNIFORM, textureOffset);
        shader.setUniformVec2f(BatchShader.TEXTURE_UNIT_SIZE_UNIFORM, textureSize);

        shader.setUniformVec4f(Shader.COLOR_UNIFORM, color.clone().toVector4f());

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, rectMesh.getVertexCount());
    }

    public void drawText(Font font, String text, int x, int y, Color color, int width, int height) {
        drawText(font, text, x, y, font.getScaleForWidth(text, width, height), color);
    }

    public void drawText(Font font, String text, int x, int y, int pixelHeight, Color color) {
        drawText(font, text, x, y, font.getScale(pixelHeight), color);
    }

    public void drawText(Font font, String text, int x, int y, float scale, Color color) {
        stopRendering();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        fontShader.start();
        fontShader.setUniformVec4f(Shader.COLOR_UNIFORM, color.clone().toVector4f());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureId());

        fontMesh.bind();
        fontMesh.enableVertexArrays();

        for (int i = 0; i < text.length(); i++) {
            int cp = text.charAt(i);
            Character character = font.getCharacters().get(cp);
            if(character == null)
                continue;
            fontMesh.editVboData(0, new float[] {
                    0, 0, character.getTexCoords().x, character.getTexCoords().w,
                    1, 0, character.getTexCoords().z, character.getTexCoords().w,
                    0, 1, character.getTexCoords().x, character.getTexCoords().y,
                    1, 0, character.getTexCoords().z, character.getTexCoords().w,
                    0, 1, character.getTexCoords().x, character.getTexCoords().y,
                    1, 1, character.getTexCoords().z, character.getTexCoords().y
            });
            float xPos = x;
            float yPos = y + character.getDescent() * scale;

            Matrix4f transformationMatrix = TransformationUtils.createTransformation(
                    xPos, yPos,
                    character.getSize().x * scale, character.getSize().y * scale,
                    new Rotation(0)
            );
            resolveMatrices(transformationMatrix);
            fontShader.setUniformMat4(Shader.COMBINED_MATRIX_UNIFORM, combinedMatrix);

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, fontMesh.getVertexCount());

            x += character.getAdvance() * scale;
        }
        fontMesh.disableVertexArrays();
        fontMesh.unbind();
        fontShader.stop();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glDisable(GL11.GL_BLEND);

        startRendering();
    }

    private void resolveMatrices(Matrix4f transformation) {
        this.combinedMatrix.identity();
        this.combinedMatrix.set(projectionMatrix).mul(transformation);
    }

    public GuiComponent getScreenComponent() {
        return screenComponent;
    }

    @Override
    public void dispose() {
        shader.dispose();
        rectMesh.dispose();
    }
}
