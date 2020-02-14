package com.Penguinz22.Rex.entity;

import com.Penguinz22.Rex.graphics.BatchRenderer;

public interface Entity {

    void render(BatchRenderer renderer);

    boolean update();

}
