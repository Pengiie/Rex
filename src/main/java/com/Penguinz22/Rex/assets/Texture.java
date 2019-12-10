package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.utils.Disposable;
import com.Penguinz22.Rex.utils.VectorUtils;
import com.Penguinz22.Rex.utils.texture.TextureData;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Texture implements Disposable {

    private int textureTarget;
    private int textureId;
    private TextureData data;

    private Vector2f offset;
    private Vector2f size;

    public Texture(int textureTarget, int textureId, TextureData data, Vector2f offset, Vector2f size) {
        this.textureTarget = textureTarget;
        this.textureId = textureId;
        this.offset = offset;
        this.size = size;
        load(data);
    }

    public Texture(TextureData data) {
        this(GL11.GL_TEXTURE_2D, GL11.glGenTextures(), data, VectorUtils.zero, VectorUtils.identity);
    }

    public void load(TextureData data) {
        this.data = data;

        if(!data.isPrepared())
            data.prepare();

        bind();
        GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(textureTarget, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        GL11.glTexImage2D(textureTarget, 0, data.getFormat().getOpenGLChannel(),
                data.getWidth(), data.getHeight(), 0,
                data.getFormat().getOpenGLChannel(),
                GL11.GL_UNSIGNED_BYTE, data.getData());
        GL11.glBindTexture(textureTarget, 0);

    }

    public int getTextureTarget() {
        return textureTarget;
    }

    public int getTextureId() {
        return textureId;
    }

    public void bind() {
        GL11.glBindTexture(this.textureTarget, this.textureId);
    }

    public void bind(int unit) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0 + unit);
        bind();
    }

    protected void setSize(float x, float y) {
        this.size.x = x;
        this.size.y = y;
    }

    protected void setOffset(float x, float y) {
        this.offset.x = x;
        this.offset.y = y;
    }

    public Vector2f getSize() {
        return size;
    }

    public Vector2f getOffset() {
        return offset;
    }

    public float getAspectRatio() {
        return this.data.getAspectRatio();
    }

    @Override
    public void dispose() {
        if(textureId == 0)
            return;
        GL11.glDeleteTextures(textureId);
        textureId = 0;
        if(data != null)
            if(!data.isDisposed())
                data.dispose();
    }

    @Override
    public boolean isDisposed() {
        return textureId == 0;
    }
}
