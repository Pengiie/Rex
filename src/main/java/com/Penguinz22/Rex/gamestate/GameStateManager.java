package com.Penguinz22.Rex.gamestate;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    private List<GameState> states = new ArrayList<>();
    private List<GameState> activeStates = new ArrayList<>();

    private List<GameState> toAddStates = new ArrayList<>();
    private List<GameState> toRemoveStates = new ArrayList<>();

    public void addActiveState(Class<? extends GameState> state) {
        states.forEach(gameState -> {
            if(gameState.getClass().isAssignableFrom(state)) {
                if(!activeStates.contains(gameState)) {
                    toAddStates.add(gameState);
                    return;
                }
            }
        });
    }

    public void removeActiveState(Class<? extends GameState> state) {
        for (GameState activeState : activeStates) {
            if(activeState.getClass().isAssignableFrom(state)) {
                toRemoveStates.add(activeState);
                return;
            }
        }
    }

    public boolean containsState(Class<? extends GameState> state) {
        for (GameState gameState : states) {
            if(gameState.getClass().isAssignableFrom(state))
                return true;
        }
        return false;
    }

    public void update() {
        for (GameState activeState : activeStates) {
            updateState(activeState);
        }
        activeStates.removeAll(toRemoveStates);
        toRemoveStates.clear();
        activeStates.addAll(toAddStates);
        toAddStates.forEach(GameState::init);
        toAddStates.clear();
    }

    private void updateState(GameState state) {
        state.update();
        for (GameState child : state.getChildren()) {
            updateState(child);
        }
    }

    // Should not be adding or removing states here
    public void render() {
        for (GameState activeState : activeStates) {
            renderState(activeState);
        }
    }

    private void renderState(GameState state) {
        state.render();
        for (GameState child : state.getChildren()) {
            renderState(child);
        }
    }

    public <G extends GameState> void addState(G state) {
        states.add(state);
    }

}
