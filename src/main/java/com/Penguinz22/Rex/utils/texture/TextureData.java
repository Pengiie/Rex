package com.Penguinz22.Rex.utils.texture;

import com.Penguinz22.Rex.utils.Disposable;

import java.io.File;

public interface TextureData extends Disposable {

    boolean isPrepared();

    void prepare();

    int getWidth();

    int getHeight();

    Format getFormat();

    class Factory {
        public static TextureData loadFromFile(String filePath, Format format) {
            if(filePath.isEmpty())
                return null;
            return new FileTextureData(new File(filePath), format);
        }
    }

}
