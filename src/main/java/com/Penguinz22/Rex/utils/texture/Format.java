package com.Penguinz22.Rex.utils.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

public enum Format {

    RGB, RGBA;

    public int getSize() {
        return this == Format.RGB ? 3 : this == Format.RGBA ? 4 : 0;
    }

    public int getStbChannel() {
        return this == Format.RGB ? STBImage.STBI_rgb : this == Format.RGBA ? STBImage.STBI_rgb_alpha : 0;
    }

    public int getOpenGLChannel() {
        return this == Format.RGB ? GL11.GL_RGB : this == Format.RGBA ? GL11.GL_RGBA : 0;
    }

}
