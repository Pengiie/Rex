package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.utils.Disposable;
import com.Penguinz22.Rex.utils.texture.TextureData;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Texture implements Disposable {

    private int textureTarget;
    private int textureId;
    private TextureData data;

    public Texture(int textureTarget, int textureId, TextureData data) {
        this.textureTarget = textureTarget;
        this.textureId = textureId;
        load(data);
    }

    public Texture(TextureData data) {
        this(GL11.GL_TEXTURE_2D, GL11.glGenTextures(), data);
    }

    public void load(TextureData data) {
        this.data = data;

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
