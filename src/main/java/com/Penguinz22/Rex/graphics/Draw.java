package com.Penguinz22.Rex.graphics;

import com.Penguinz22.Rex.utils.Color;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Draw {

    public static void clearWithColor(Color color) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(color.r, color.g, color.b, color.a);
    }

}
