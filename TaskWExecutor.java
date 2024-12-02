package cp2024.solution;

import java.util.concurrent.ExecutorService;

public abstract class TaskWExecutor implements Runnable {
    protected ExecutorService executor;

    public TaskWExecutor(ExecutorService executor) {
        this.executor = executor;
    }
}
