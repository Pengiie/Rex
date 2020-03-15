package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.utils.fonts.Character;
import com.Penguinz22.Rex.utils.fonts.FontData;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

public class Font {

    private HashMap<Integer, Character> characters = new HashMap<>();
    private FontData data;

    private int textureId;

    public Font(FontData data) {
        load(data);
    }

    public void load(FontData data) {
        this.data = data;
        if(!data.isPrepared())
            data.prepare();

        int id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RED, data.getWidth(), data.getHeight(), 0, GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, data.getData());

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        this.textureId = id;

        characters.clear();
        loadDefaultCharacters();
    }

    private void loadDefaultCharacters() {
        for (int i = 32; i < 128; i++) {
            characters.put(i, loadCharData(i));
        }
    }

    private float lastX;

    public Character loadCharData(int character) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer x = stack.mallocFloat(1);
            FloatBuffer y = stack.mallocFloat(1);
            STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);
            STBTruetype.stbtt_GetBakedQuad(data.getCData(), data.getWidth(), data.getHeight(), character - 32, x, y, q, true);

            float advance = x.get(0) - lastX;

            lastX = x.get(0);

            int descent = (int) -q.y1();
            int width = (int) (q.x1()-q.x0());
            int height = (int) (q.y1() - q.y0());

            return new Character(new Vector4f(q.s0(), q.t0(), q.s1(), q.t1()), new Vector2f(width, height), (int) advance, descent);
        }
    }

    public HashMap<Integer, Character> getCharacters() {
        return characters;
    }

    public int getTextureId() {
        return textureId;
    }

    public float getScale(int pixelHeight) {
        return (float) pixelHeight / data.getLineHeight();
    }

    public float getScaleForWidth(String text, int width, int height) {
        int charHeight;
        int x = 0;
        do {
            x = 0;
            for (int i = 0; i < text.length(); i++) {
                int cp = text.charAt(i);
                Character character = characters.get(cp);
                x += character.getAdvance();
            }
            charHeight = (int) (((float) width/x) * getData().getLineHeight());
            if(charHeight > height)
                width--;
        } while(charHeight > height);
        return (float) width/x;
    }

    public int getTextWidth(String text, float scale) {
        return getTextWidth(text, scale, true);
    }

    public int getTextWidth(String text, float scale, boolean withTrailingAdvance) {
        int x = 0;
        for (int i = 0; i < text.length(); i++) {
            int cp = text.charAt(i);
            Character character = characters.get(cp);
            if(character == null)
                continue;
            x += character.getAdvance() * scale;
            if(!withTrailingAdvance && i == text.length()-1)
                x -= (character.getAdvance() * scale) - (character.getSize().x * scale);
        }
        return x;
    }

    public FontData getData() {
        return data;
    }

    public float getKernAdvance(char char1, char char2) {
        return STBTruetype.stbtt_GetCodepointKernAdvance(data.getInfo(), char1, char2);
    }
}
