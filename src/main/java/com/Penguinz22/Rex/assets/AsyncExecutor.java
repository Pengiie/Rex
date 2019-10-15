package com.Penguinz22.Rex.assets;

import com.Penguinz22.Rex.utils.AsyncTask;
import com.Penguinz22.Rex.utils.Disposable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutor implements Disposable {

    private final ExecutorService executor;

    public AsyncExecutor(int maxConcurrent) {
        executor = Executors.newFixedThreadPool(maxConcurrent, r -> {
            Thread thread = new Thread(r, "AsynchExecutor-Thread");
            thread.setDaemon(true);
            thread.setUncaughtExceptionHandler((t, e) -> {
                e.printStackTrace();
            });
            return thread;
        });
    }

    public AsyncResult<Void> submit(Runnable run) {
        return submit(() -> {
            run.run();
            return null;
        });
    }

    public <T> AsyncResult<T> submit(final AsyncTask<T> task){
        if(executor.isShutdown()){
            throw new RuntimeException("Cannot run tasks on an executor that has been shutdown (disposed)");
        }
        return new AsyncResult<>(executor.submit(task::call));
    }

    @Override
    public void dispose() {
        executor.shutdown();
    }
}
