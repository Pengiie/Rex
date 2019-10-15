package com.Penguinz22.Rex.utils.texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class FileTextureData implements TextureData {

    final File file;
    int width, height;
    ByteBuffer data;
    Format format;
    boolean isPrepared = false;
    private boolean disposed = false;

    public FileTextureData(File file, Format format) {
        this.file = file;
        this.format = format;
        if(format == null)
            this.format = Format.RGBA;
    }

    @Override
    public boolean isPrepared() {
        return isPrepared;
    }

    @Override
    public void prepare() {
        if(isPrepared) throw new RuntimeException("File Texture Data Already Prepared!");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            data = STBImage.stbi_load(file.getAbsolutePath(), w, h, comp, format.getStbChannel());

            this.width = w.get(0);
            this.height = h.get(0);
        }
        isPrepared = true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        MemoryUtil.memFree(data);
        this.disposed = true;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Format getFormat() {
        return format;
    }
}
