package com.Penguinz22.Rex.utils.fonts;

import com.Penguinz22.Rex.utils.IOUtils;
import org.lwjgl.stb.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class FontData {

    String filePath;
    int width;
    int height;
    float scale;
    int ascent;
    int descent;
    int lineGap;
    int lineHeight;
    boolean useKerning;
    ByteBuffer data;
    STBTTBakedChar.Buffer cData;
    STBTTFontinfo info;
    boolean isPrepared = false;
    private boolean disposed = false;

    public FontData(String filePath, int width, int height, int lineHeight, boolean useKerning) {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.lineHeight = lineHeight;
        this.useKerning = useKerning;
    }

    public void prepare() {
        if(isPrepared) throw new RuntimeException("Font Data Already Prepared!");

        ByteBuffer fontBuffer;
        try {
            fontBuffer = IOUtils.ioResourceToByteBuffer(filePath, 512 * 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        STBTTFontinfo info = STBTTFontinfo.create();
        if(!STBTruetype.stbtt_InitFont(info, fontBuffer)) {
            throw new RuntimeException("Could not init font "+filePath);
        }
        this.info = info;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pAscent  = stack.mallocInt(1);
            IntBuffer pDescent = stack.mallocInt(1);
            IntBuffer pLineGap = stack.mallocInt(1);

            STBTruetype.stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap);

            ascent = pAscent.get(0);
            descent = pDescent.get(0);
            lineGap = pLineGap.get(0);
        }

        STBTTBakedChar.Buffer cData = STBTTBakedChar.malloc(96);
        ByteBuffer bitmap = MemoryUtil.memAlloc(width * height);
        STBTruetype.stbtt_BakeFontBitmap(fontBuffer, lineHeight, bitmap, width, height, 32, cData);
        STBImageWrite.stbi_write_png("gamer.png", width, height, 1, bitmap, 0);
        this.cData = cData;
        this.data = bitmap;
        this.isPrepared = true;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public ByteBuffer getData() {
        return data;
    }

    public STBTTBakedChar.Buffer getCData() {
        return cData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLineGap() {
        return lineGap;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public boolean isUseKerning() {
        return useKerning;
    }

    public STBTTFontinfo getInfo() {
        return info;
    }

    public int getAscent() {
        return ascent;
    }

    public int getDescent() {
        return descent;
    }

    public boolean isDisposed() {
        return disposed;
    }

    public void dispose() {
        MemoryUtil.memFree(data);
        MemoryUtil.memFree(cData);
        this.disposed = true;
    }

}
