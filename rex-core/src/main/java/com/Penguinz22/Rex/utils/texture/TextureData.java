package com.Penguinz22.Rex.utils.texture;

import com.Penguinz22.Rex.utils.IOUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureData {

    final String file;
    int width, height;
    float aspectRatio;
    ByteBuffer data;
    Format format;
    boolean isPrepared = false;
    private boolean disposed = false;

    public TextureData(String file, Format format) {
        this.file = file;
        this.format = format;
        if(format == null)
            this.format = Format.RGBA;
    }

    public TextureData(int width, int height, ByteBuffer data, Format format) {
        this.file = "";
        this.width = width;
        this.height = height;
        this.aspectRatio = (float) width / height;
        this.data = data;
        this.format = format;
        this.isPrepared = true;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void prepare() {
        if(isPrepared) throw new RuntimeException("File Texture Data Already Prepared!");

        ByteBuffer imageBuffer;
        try {
            imageBuffer = IOUtils.ioResourceToByteBuffer(file, 8 * 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);
            data = STBImage.stbi_load_from_memory(imageBuffer, w, h, comp, format.getStbChannel());

            this.width = w.get(0);
            this.height = h.get(0);
            this.aspectRatio = (float) width / height;
        }
        isPrepared = true;
    }

    public boolean isDisposed() {
        return disposed;
    }

    public void dispose() {
        MemoryUtil.memFree(data);
        this.disposed = true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Format getFormat() {
        return format;
    }

    public ByteBuffer getData() {
        return data;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public static class Factory {
        public static TextureData loadFromFile(String filePath, Format format) {
            return new TextureData(filePath, format);
        }
    }
}
