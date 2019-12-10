package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.assets.Font;
import com.Penguinz22.Rex.graphics.BatchRenderer;
import com.Penguinz22.Rex.graphics.Draw;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.utils.Color;
import com.Penguinz22.Rex.utils.Rotation;
import com.Penguinz22.Rex.utils.fonts.FontData;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class GeneralTester implements ApplicationListener {

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle("Quality Game!");
        Application app = new Application(new GeneralTester(), config);
    }

    private BatchRenderer renderer;

    @Override
    public void init() {
        this.renderer = new BatchRenderer();
        Font font = new Font(new FontData("comic.ttf", 512, 512, 64, false));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update() {
        //System.out.println(Core.input.getMousePosX()+" "+Core.input.getMousePosY());
    }

    private List<Vector2i> places = new ArrayList<>();

    @Override
    public void render() {
        Draw.clear();

        for(int i=0;i<=360;i++) {
            renderer.draw(new Color(1, 1, 0, 1), Core.input.getMousePosX(), Core.input.getMousePosY(), 50, 50, new Rotation(Rotation.Origin.BOTTOM_LEFT, i));
        }

        if(Core.input.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_1))
            places.add(new Vector2i(Core.input.getMousePosX(), Core.input.getMousePosY()));

        for(Vector2i spot: places) {
            for(int i=0;i<=360;i++) {
                renderer.draw(new Color(1, 1, 0, 1), spot.x, spot.y, 50, 50, new Rotation(Rotation.Origin.BOTTOM_LEFT, i));
            }
        }

        renderer.finish();
    }
}
