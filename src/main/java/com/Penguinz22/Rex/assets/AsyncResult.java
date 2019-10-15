package com.Penguinz22.Rex.assets;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncResult<T> {
    private final Future<T> future;

    AsyncResult(Future<T> future) {
        this.future = future;
    }

    public boolean isDone() {
        return future.isDone();
    }

    public T get() {
        try {
            return future.get();
        } catch (InterruptedException e) {
            return null;
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
