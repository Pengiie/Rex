package com.Penguinz22.Rex.utils;

public interface Disposable {

    void dispose();

    default boolean isDisposed() {
        return false;
    }

}
