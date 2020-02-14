package com.Penguinz22.Rex.entity;

import com.Penguinz22.Rex.graphics.BatchRenderer;
import com.Penguinz22.Rex.graphics.camera.Camera;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<Entity> entities = new ArrayList<>();

    private BatchRenderer renderer;
    private Camera camera;

    public EntityManager() {
        this.renderer = new BatchRenderer();
    }

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i).update()) {
                entities.remove(i);
                i--;
            }
        }
    }

    public void render(Camera camera) {
        this.camera = camera;
        renderer.attachCamera(camera);
        entities.forEach(entity->entity.render(renderer));
        renderer.finish();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

}
