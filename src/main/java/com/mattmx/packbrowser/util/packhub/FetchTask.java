package com.mattmx.packbrowser.util.packhub;

import java.util.concurrent.*;
import java.util.function.Consumer;

public class FetchTask<T> extends Thread {
    private Callable<T> task;
    private Consumer<T> whenDone;

    public FetchTask(Callable<T> task) {
        this.task = task;
    }

    public FetchTask<T> get() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<T> result = executor.submit(task);
        while (!result.isDone()) {
        }
        try {
            T actualResult = result.get();
            if (whenDone != null) whenDone.accept(actualResult);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FetchTask<T> then(Consumer<T> task) {
        whenDone = task;
        return this;
    }
}
