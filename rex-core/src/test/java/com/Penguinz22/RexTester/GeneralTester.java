package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.graphics.BatchRenderer;
import com.Penguinz22.Rex.graphics.Draw;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.physics.RigidBody;
import com.Penguinz22.Rex.utils.Color;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class GeneralTester implements ApplicationListener {

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle("Quality Game!");
        Application app = new Application(new GeneralTester(), config);
    }

    private BatchRenderer renderer;
    private RigidBody ref1;
    private RigidBody ref2;

    @Override
    public void init() {
        this.renderer = new BatchRenderer();
        /*Core.physics = new PhysicsManager();
        Core.assets = new AssetManager(true);
        Core.assets.load("ratioTest.png", Texture.class);
        Core.assets.finishLoading();
        Core.physics.addRigidBody(ref1 = new RigidBody(new Vector2f(50, 50)));
        ref1.addBoundingBox(new RectBoundingBox(100, 100), VectorUtils.zero);
        Core.physics.addRigidBody(ref2 = new RigidBody(new Vector2f(300, 300)));
        ref2.addBoundingBox(new RectBoundingBox(100, 100), VectorUtils.zero);
        RigidBody floor = new RigidBody( new Vector2f(0, 0));
        Core.physics.addRigidBody(floor);
        floor.addBoundingBox(new RectBoundingBox(700, 40), VectorUtils.zero);

        ref1.mass = 1;
        ref1.gravity = -0.1f;
        ref2.mass = 1;
        ref2.gravity = -0.1f;
        //ref2.friction = 0.05f;*/
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update() {
        //System.out.println(Core.input.getMousePosX()+" "+Core.input.getMousePosY());
        /*Core.physics.update();
        if(Core.input.isKeyDown(Key.KEY_LEFT))
            ref1.velocity.x+=-0.05f;
        else if(Core.input.isKeyDown(Key.KEY_RIGHT))
            ref1.velocity.x +=0.05f;
        else
            ref1.velocity.x = 0;

        if(Core.input.isKeyDown(Key.KEY_DOWN))
            ref1.velocity.y +=-0.05f;
        else if(Core.input.isKeyDown(Key.KEY_UP))
            ref1.velocity.y +=0.05f;
        else
            ref1.velocity.y = 0;*/
    }

    private List<Vector2i> places = new ArrayList<>();

    @Override
    public void render() {
        Draw.clear();

        renderer.draw(Color.red, (int)ref1.position.x, (int)ref1.position.y, (int)100, (int)100);
        renderer.draw(Color.blue, (int)ref2.position.x, (int)ref2.position.y, (int)100, (int)100);
        renderer.draw(Color.green, 0, 0, 700, 40);

        /*for(int i=0;i<=360;i++) {
            renderer.draw(new Color(1, 1, 0, 1), Core.input.getMousePosX(), Core.input.getMousePosY(), 50, 50, new Rotation(Rotation.Origin.BOTTOM_LEFT, i));
        }

        if(Core.input.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_1))
            places.add(new Vector2i(Core.input.getMousePosX(), Core.input.getMousePosY()));

        for(Vector2i spot: places) {
            for(int i=0;i<=360;i++) {
                renderer.draw(new Color(1, 1, 0, 1), spot.x, spot.y, 50, 50, new Rotation(Rotation.Origin.BOTTOM_LEFT, i));
            }
        }
*/
        renderer.finish();
    }
}
