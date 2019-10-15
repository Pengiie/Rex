package com.Penguinz22.Rex.utils.texture;

import org.lwjgl.stb.STBImage;

public enum Format {

    RGB, RGBA;

    public int getStbChannel() {
        return this == Format.RGB ? STBImage.STBI_rgb : this == Format.RGBA ? STBImage.STBI_rgb_alpha : 0;
    }

}
