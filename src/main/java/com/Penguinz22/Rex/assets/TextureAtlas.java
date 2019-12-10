package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.utils.VectorUtils;
import com.Penguinz22.Rex.utils.texture.TextureAtlasData;
import com.Penguinz22.Rex.utils.texture.TextureData;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class TextureAtlas extends Texture {

    public final int MAX_ID;

    private int rows, cols;

    public TextureAtlas(int textureTarget, int textureId, TextureAtlasData data) {
        super(textureTarget, textureId, data, VectorUtils.zero, VectorUtils.identity);
        this.cols = (int) Math.floor(data.getWidth()/data.getUnitSize().x);
        this.rows = (int) Math.floor(data.getHeight()/data.getUnitSize().y);
        setSize((float) 1/cols, (float) 1/rows);
        this.MAX_ID = this.cols * this.rows;
    }

    public TextureAtlas(TextureAtlasData data) {
        this(GL11.GL_TEXTURE_2D, GL11.glGenTextures(), data);
    }

    /** Instead of x and y coords enter id and max id is equals to
     * @param id starts from 1 and ends at MAX_ID
     */
    public void switchToTexture(int id) {
        int x = (id-1)%cols;
        int y = (id-1)/cols;
        switchToTexture(x+1, y+1);
    }

    /** Must be called when switching a texture in the atlas to reposition coordinates
     * @param x the texture x coordinate starting from 1
     * @param y the texture y coordinate starting from 1
     */
    public void switchToTexture(int x, int y) {
        if(x <= 0 || x > cols || y <= 0 || y > rows)
            throw new RuntimeException("Trying to access texture out of bounds of atlas, x: "+x+", y: "+y);
        setOffset((x-1)*getSize().x, (y-1)*getSize().y);
    }

}
