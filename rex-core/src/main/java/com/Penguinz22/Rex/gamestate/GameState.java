package com.Penguinz22.Rex.gamestate;

import java.util.ArrayList;
import java.util.List;

public abstract class GameState {

    private List<GameState> children = new ArrayList<>();

    public abstract void init();

    public abstract void update();

    public abstract void render();

    public void addChildState(GameState state) {
        children.add(state);
    }

    public List<GameState> getChildren() {
        return children;
    }
}
